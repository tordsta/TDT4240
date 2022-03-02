package com.tdt4240.game.ecs.component

/**
 * Component storing the players ID as a string.
 */
class PlayerID : BaseComponent() {
    var id: String = ""

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}
