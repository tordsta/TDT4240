package com.tdt4240.game.ecs.component


/**
 * Component storing if it is the players turn.
 */
// TODO: should be refactored to PlayersTurn
class UserControllable : BaseComponent() {
    var enable: Boolean = false

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}

