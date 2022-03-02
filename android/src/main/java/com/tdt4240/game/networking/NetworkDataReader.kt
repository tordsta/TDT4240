package com.tdt4240.game.networking

import android.util.Log
import com.badlogic.ashley.core.Entity
import com.tdt4240.game.ecs.component.*
import org.json.JSONException
import org.json.JSONObject


/**
 * Responsible for receiving and parsing entity snapshots,
 * and updating the local entities.
 */
class NetworkDataReader(private val databaseQueue: DatabaseQueue) : ComponentVisitor {

    private var currentSnapshot: JSONObject? = null
    private var entitySnapshot: JSONObject? = null

    fun update() : Boolean {
        synchronized(databaseQueue) {
            var nextSnapshot : JSONObject? = null

            // update currentSnapshot to the latest snapshot or null if no new one is available
            do {
                currentSnapshot = nextSnapshot
                nextSnapshot = databaseQueue.getNextSnapshot()
            }while (nextSnapshot != null)
        }

        return currentSnapshot != null
    }

    fun allNetworkIDs(): MutableIterator<String>? {
        return currentSnapshot?.keys()
    }

    fun setEntity(networkID: String): Boolean {
        try {
            //Log.i("Network data reader", "processing entity $networkID")
            if (currentSnapshot == null) {
                //Log.i("Network data reader", "current snapshot is null")
                return false // no data available
            }

            entitySnapshot = currentSnapshot?.get(networkID) as JSONObject

            //println(networkID + entitySnapshot)

            if (entitySnapshot == null) {
                Log.w("NetworkDataReader", "Entity does not exist!")
                return false
            }

            return true
        } catch (e: JSONException) {
            //println(e.message)
            return false
        }

    }

    private fun getFloatValue(key: String): Float? {
        return getValue(key)?.toFloat()
    }

    private fun getIntValue(key: String): Int? {

        return getValue(key)?.toInt()
    }

    private fun getBoolValue(key: String): Boolean? {
        return getValue(key)?.toBoolean()
    }

    private fun getValue(key: String): String? {
        return entitySnapshot?.get(key).toString()
    }


    override fun visit(position: Position) {
        position.position.x = getFloatValue(DatabaseKeys.X)!!
        position.position.y = getFloatValue(DatabaseKeys.Y)!!
        position.orientation = getFloatValue(DatabaseKeys.ORIENTATION)!!
    }

    override fun visit(healthComponent: HealthComponent) {
        healthComponent.healthPoints = getIntValue(DatabaseKeys.HEALTH_POINTS)!!
    }

    override fun visit(userControllable: UserControllable) {
        userControllable.enable = getBoolValue(DatabaseKeys.ENABLE)!!
    }

    override fun visit(typeComponent: TypeComponent) {
        typeComponent.type = TypeComponent.Type.valueOf(getValue(DatabaseKeys.TYPE)!!)
    }

    override fun visit(weaponType: WeaponType) {
        weaponType.type = WeaponType.Type.valueOf(getValue(DatabaseKeys.WEAPON_TYPE)!!)
    }

    override fun visit(playerID: PlayerID) {
        playerID.id = getValue(DatabaseKeys.PLAYER_ID)!!
    }

    override fun visit(component: BaseComponent) {}

    fun getEntityType(networkId: String): Any? {
        val entityMap = currentSnapshot?.get(networkId) as JSONObject?

        val type = entityMap?.get(DatabaseKeys.TYPE)
        if(type == null){
            Log.w("Network data reader", "Found entity without type!")
        }

        return type
    }

}