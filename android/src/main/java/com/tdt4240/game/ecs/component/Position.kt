package com.tdt4240.game.ecs.component

import com.badlogic.gdx.math.Vector2

/**
 * Component storing the position as a Vector2 and the orientation as float.
 */
class Position : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    var position: Vector2 = Vector2(0.0f, 0.0f)
    var orientation: Float = 0.0f // radians


}
