package com.tdt4240.game.ecs.component

/**
 * Component for the different navigation buttons for the player.
 * The different actions (going left, going right and jumping) is given as a list of enums.
 *
 */
class ButtonActionComponent : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    enum class Action { LEFT, RIGHT, JUMP }

    lateinit var action : Action


}