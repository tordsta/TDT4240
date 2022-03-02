package com.tdt4240.game.networking

import org.json.JSONObject

abstract class GameSocketClientInterface {

    abstract fun login()
    abstract fun leaveRoom()


    abstract fun isRoomInitialized() : Boolean
    abstract fun addPlayerToRoom(newPlayerID: String)

    abstract fun broadcastEntitySnapshot(snapshot: JSONObject)

    abstract fun registerEventListener(listener: GameSocketCallbackListenerInterface)

    abstract fun update()
    abstract fun localPlayerID(): String?
}