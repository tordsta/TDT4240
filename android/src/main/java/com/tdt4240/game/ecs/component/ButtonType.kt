package com.tdt4240.game.ecs.component

/**
 * Component stating the type of a button.
 * List of enums (navigating the player, between the weapons, selecting a weapon,
 * showing the weapons and start shooting).
 */
class ButtonType : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    enum class Type { PLAYER_NAVIGATION, WEAPON_NAVIGATION, WEAPON_SELECT, SHOW_WEAPONS, START_SHOOT }

    lateinit var type : Type
}