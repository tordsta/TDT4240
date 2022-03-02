package com.tdt4240.game.networking

import com.tdt4240.game.ecs.component.*
import org.json.JSONObject

class NetworkDataWriter : ComponentVisitor {

    private var entities = JSONObject()

    var networkID = ""

    fun getEntitySnapshot(): JSONObject{
        return entities
    }

    fun reset(){
        entities = JSONObject()
    }

    private fun update(key: String, value: Any) {
        if (entities.isNull(networkID)) {
            entities.put(networkID, JSONObject())
        }
        entities.getJSONObject(networkID).put(key, value)
    }

    override fun visit(position: Position) {
        update(DatabaseKeys.X, position.position.x)
        update(DatabaseKeys.Y, position.position.y)
        update(DatabaseKeys.ORIENTATION, position.orientation)
    }

    override fun visit(healthComponent: HealthComponent) {
        update(DatabaseKeys.HEALTH_POINTS, healthComponent.healthPoints)
    }

    override fun visit(userControllable: UserControllable) {
        update(DatabaseKeys.ENABLE, userControllable.enable)
    }

    override fun visit(typeComponent: TypeComponent) {
        update(DatabaseKeys.TYPE, typeComponent.type.toString())
    }

    override fun visit(weaponType: WeaponType) {
        update(DatabaseKeys.WEAPON_TYPE, weaponType.type.toString())
    }

    override fun visit(playerID: PlayerID) {
        update(DatabaseKeys.PLAYER_ID, playerID.id)
    }


}