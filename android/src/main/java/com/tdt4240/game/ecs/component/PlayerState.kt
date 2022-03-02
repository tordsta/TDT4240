package com.tdt4240.game.ecs.component


/**
 * Component storing the players state.
 * Has a list of enums with possible states (shoot, move, wait or selecting weapon).
 */
class PlayerState : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    enum class STATE { SHOOT, MOVE, WAIT, SELECT_WEAPON }

    var type : STATE = STATE.WAIT
}