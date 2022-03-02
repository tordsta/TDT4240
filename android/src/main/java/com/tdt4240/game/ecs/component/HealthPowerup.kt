package com.tdt4240.game.ecs.component

/**
 * Component storing the health points that will be given when getting the powerUp.
 * Stored in int.
 */
class HealthPowerup : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)

    }

    // TODO: why is this 0? Is it set when creating the component? Should it rather be late init?
    var powerUpPoints: Int = 0

}