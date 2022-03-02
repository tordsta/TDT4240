package com.tdt4240.game.networking

import org.json.JSONObject
import java.util.*


/**
 * A local Database, for holding and accumulating
 * snapshots of entities received through the Network.
 */
class DatabaseQueue {

    private val dbQueue = LinkedList<JSONObject>()

    fun push(data: JSONObject?) {
        if(data == null) return
        // Log.i("Network Queue", "pushing in new data")
        synchronized(dbQueue){
            dbQueue.add(data)
        }
    }

    fun getNextSnapshot(): JSONObject? {
        // remove old snapshots
        synchronized(dbQueue){
            return dbQueue.poll()
        }
    }

    fun clear(){
        synchronized(dbQueue){
            dbQueue.clear()
        }
    }

}