package com.tdt4240.game.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

data class Room(
        var players: MutableList<String>? = mutableListOf(),
        var spawns: Map<String, Spawn>? = mapOf(),
        var roomID: String? = ""
) {
    constructor(raw: JSONObject) : this() {
        this.players = Gson().fromJson(raw.getString("players"), Array<String>::class.java).toMutableList()
        this.spawns = Gson().fromJson(raw.getString("spawns"), object : TypeToken<Map<String, Spawn>>() {}.type)
        this.roomID = raw.getString("roomID")
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "players" to players,
            "spawns" to spawns!!.toMap(),
            "roomID" to roomID
        )
    }
}