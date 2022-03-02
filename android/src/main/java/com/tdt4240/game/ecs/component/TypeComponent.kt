package com.tdt4240.game.ecs.component

/**
 * Component storing the type of an entity.
 * Has a list of possible types (obstacle, player, powerup, bullet etc)
 */
class TypeComponent : BaseComponent() {

    enum class Type { OBSTACLE, PLAYER, POWERUP, BULLET, MISSILE, BUTTON, WEAPON, MINE, GROUND, HEALTH }

    lateinit var type : Type

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}