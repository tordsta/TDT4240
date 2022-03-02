package com.tdt4240.game.ecs.component

/**
 * ComponentVisitor for several types of components that need to be both
 * - sent to the server
 * - updated locally based on the server updates (snapshots)
 *
 */
interface ComponentVisitor {
    fun visit(position: Position)
    fun visit(healthComponent: HealthComponent)
    fun visit(userControllable: UserControllable)
    fun visit(typeComponent: TypeComponent)
    fun visit(weaponType: WeaponType)
    fun visit(playerID: PlayerID)
    // TODO add other visit methods here
    fun visit(component: BaseComponent) {

    }

}