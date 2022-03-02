package com.tdt4240.game.ecs.component

/**
 * Component that has a damage given in int.
 */
class Bullet : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    var range: Float =  1f
    // This could in a more further version be more dynamic
    var damage : Int = 20
}