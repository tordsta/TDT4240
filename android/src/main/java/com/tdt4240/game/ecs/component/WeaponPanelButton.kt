package com.tdt4240.game.ecs.component

/**
 * Component storing the type of a button in the WeaponPanel.
 * List of enums (select, right, left).
 */
class WeaponPanelButton : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    enum class Type { SELECT, RIGHT, LEFT}
    lateinit var type : Type
}