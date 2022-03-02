package com.tdt4240.game.networking

import com.tdt4240.game.GameState
import org.json.JSONObject


interface GameSocketCallbackListenerInterface{
    fun onChangeTurnRequested(gameState: GameState)
    fun onPlayerAdded(playerID: String)

    fun onEntityUpdateReceived(data: JSONObject)

    fun processPendingCallbacks()
    fun leaveRoom()
}