package com.tdt4240.game.networking

import com.badlogic.ashley.core.Entity
import com.tdt4240.game.GameState
import com.tdt4240.game.ecs.component.BaseComponent
import com.tdt4240.game.ecs.component.NetworkID
import com.tdt4240.game.model.GameModel
import com.tdt4240.game.utils.SceneBuilder
import org.json.JSONObject

class NetworkAdapter(
        private val gameSocketClient: GameSocketClientInterface,
        private val gameModel: GameModel,
        private val sceneBuilder: SceneBuilder
) : GameSocketCallbackListenerInterface, NetworkAdapterInterface {

    override fun leaveRoom() {
        gameSocketClient.leaveRoom()
    }

    override fun processPendingCallbacks() {
        gameSocketClient.update()
    }

    init {
        gameSocketClient.registerEventListener(this)
    }


    override fun beginUpdate() {
        if (networkMode == NetworkMode.SENDER) {
            networkDataWriter.reset()
        }

        if (networkMode == NetworkMode.RECEIVER) {
            networkDataReader.update()
        }
    }

    override fun endUpdate() {
        if (networkMode == NetworkMode.SENDER) {
            val snapshot = networkDataWriter.getEntitySnapshot()
            //Log.v("NetworkAdapter", "broadcasting snapshot $snapshot")
            gameSocketClient.broadcastEntitySnapshot(snapshot)
        }
    }

    override fun allNetworkIDs():  MutableIterator<String>?{
        return networkDataReader.allNetworkIDs()
    }


    override fun onChangeTurnRequested(gameState: GameState) {
        gameModel.stm.changeState(gameState)
    }

    fun isInitialized(): Boolean {
        return networkMode != null
    }


    var networkMode: NetworkMode? = null


    private val db = DatabaseQueue()


    override fun onEntityUpdateReceived(data: JSONObject) {
        synchronized(db) {
            if (networkMode == NetworkMode.RECEIVER) {
                db.push(data)
            } else {
                db.clear()
            }
        }
    }


    private var isRoomCreator = false

    override fun onPlayerAdded(playerID: String) {
        // case 1: room is uninitialized so we are the first in the room so we will create all entities
        if (!gameSocketClient.isRoomInitialized()) {
            isRoomCreator = true

            gameModel.stm.changeState(GameState.WAIT_FOR_PLAYER_TO_JOIN)
            sceneBuilder.createEntitiesForMap()
        }

        // case 2: we are the one that created the room, we create and add the player to the game
        if (isRoomCreator) {
            gameSocketClient.addPlayerToRoom(playerID)
            sceneBuilder.addNewPlayer(playerID)
        }
    }


    private val networkDataReader = NetworkDataReader(db)
    private val networkDataWriter = NetworkDataWriter()


    override fun updateLocalEntity(entity: Entity) {
        val networkID = entity.getComponent(NetworkID::class.java).id
        if (networkDataReader.setEntity(networkID)) {
            entity.components.forEach { (it as BaseComponent).accept(networkDataReader) }
        }
    }

    override fun updateRemoteEntity(entity: Entity) {
        val networkID = entity.getComponent(NetworkID::class.java).id
        networkDataWriter.networkID = networkID
        entity.components.forEach { (it as BaseComponent).accept(networkDataWriter) }
    }

    fun localPlayerID() : String? {
        return gameSocketClient.localPlayerID()
    }

    fun getEntityType(networkId: String): Any? {
        return networkDataReader.getEntityType(networkId)
    }


}