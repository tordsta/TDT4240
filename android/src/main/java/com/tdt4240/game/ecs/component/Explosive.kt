package com.tdt4240.game.ecs.component

/**
 * Component storing the strength of an explosion in float.
 */
class Explosive : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    var strength: Float = 0.0f

    /**
     * Distance in meters to limit the explosion to.
     * All entities more far away from the explosion center are not damaged.
     */
    var range: Float = 1.0f
}
