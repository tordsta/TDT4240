package com.tdt4240.game.networking

import android.util.Log
import com.tdt4240.game.GameState
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.util.concurrent.ConcurrentLinkedQueue


class GameSocketClient : GameSocketClientInterface() {

    override fun broadcastEntitySnapshot(snapshot: JSONObject) {
        if (snapshot.length() != 0) {
            socket.emit("update-entities", snapshot)
        }
    }

    override fun update() {
        processPendingLoginNetworkIds()
        checkIfAndChangeTurn()
    }

    override fun isRoomInitialized(): Boolean {
        return room.players!!.size > 0
    }

    override fun addPlayerToRoom(newPlayerID: String) {
        Log.i("GameSocketClient", "addPlayerToRoom")
        room.players!!.add(newPlayerID)
        socket.emit("room-update", JSONObject(room.toMap()))
    }


    // Change this to your local IP (not localhost!) if developing the server.
    private val SERVER_IP = "stadsbygd.ddns.net"
    private val uri: String = "http://$SERVER_IP:3000/"

    private var socket: Socket = IO.socket(uri)


    private var room = Room()


    init {
        registerSocketEventListeners()

        socket.connect()
    }

    private fun registerSocketEventListeners() {
        socket.on("logged-in", this::onSocketPlayerLoggedIn)
        socket.on("entities", this::onSocketEntityUpdateReceived)
        socket.on("player-change", this::onSocketPlayerChangeRequested)
        socket.on(Socket.EVENT_CONNECT, this::onSocketConnected)
    }


    private val newPlayerIDs = ArrayList<String>()

    private fun processPendingLoginNetworkIds() {
        synchronized(newPlayerIDs) {
            newPlayerIDs.forEach { eventListener?.onPlayerAdded(it) }
            newPlayerIDs.clear()
        }
    }

    private var playerID = ""

    private fun onSocketConnected(args: Array<Any>) {
        System.out.println("SOCKET:CONNECT: Socket connected $args")
        synchronized(playerID) {
            playerID = socket.id()
        }
    }


    private fun onSocketPlayerLoggedIn(args: Array<Any>) {
        val data = args[0] as JSONObject
        val newRoom = Room(data.getJSONObject("room"))
        val newPlayerID = data.getString("newPlayerID")
        Log.i("SOCKET:LOGIN", "logged in, room is $room")

        synchronized(newPlayerIDs) {
            newPlayerIDs.add(newPlayerID)
        }

        synchronized(room) {
            room = newRoom
        }
    }


    private val requestedGameStates = ConcurrentLinkedQueue<GameState>()

    private fun onSocketPlayerChangeRequested(args: Array<Any>) {
        val data = args[0] as JSONObject
        val newPlayerID = data.getString("playerID")

        val requestedGameState = synchronized(playerID) {
            if (playerID == newPlayerID) GameState.YOUR_TURN else GameState.WAITING_TURN
        }

        println("requestedGameState: $requestedGameState")

        synchronized(requestedGameStates) {
            requestedGameStates.add(requestedGameState)
        }

    }


    private fun onSocketEntityUpdateReceived(args: Array<Any>) {
        //println("received data")
        val data = args[0] as JSONObject
        eventListener?.onEntityUpdateReceived(data)
    }


    override fun login() {
        Log.i("SOCKET:LOGIN", "playerID $playerID")
        socket.emit("login")
    }


    override fun leaveRoom() {
        synchronized(playerID) {
            socket.emit("leave-room", playerID)
        }
        socket.disconnect()
        //TODO Go back to main menu
    }


    private fun checkIfAndChangeTurn() {
        synchronized(requestedGameStates) {
            requestedGameStates.forEach { eventListener?.onChangeTurnRequested(it) }
            requestedGameStates.clear()
        }

    }

    override fun localPlayerID(): String? {
        synchronized(playerID) {
            return playerID
        }
    }

    var eventListener: GameSocketCallbackListenerInterface? = null
    override fun registerEventListener(listener: GameSocketCallbackListenerInterface) {
        eventListener = listener
    }

}