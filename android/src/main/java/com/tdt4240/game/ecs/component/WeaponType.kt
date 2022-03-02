package com.tdt4240.game.ecs.component

/**
 * Component storing the type of weapon.
 * List of enums (bomb, rifle, slignshot, cannon, gun, grenade).
 *
 */
class WeaponType : BaseComponent() {
    enum class Type { BOMB, RIFLE, SLINGSHOT, CANNON, GUN, GRENADE }

    lateinit var type : Type

    // Static functions are placed inside companion object
    companion object {
        fun values() : Array<Type>  {
            return Type.values()
        }

        fun bulletDamage(type : Type) : Int {
            return when (type) {
                Type.BOMB -> 30
                Type.RIFLE -> 20
                Type.SLINGSHOT -> 5
                Type.CANNON -> 25
                Type.GUN -> 15
                Type.GRENADE -> 20
            }
        }

        fun bulletRange(type: WeaponType.Type): Float {
            return when (type) {
                Type.BOMB -> 1.0f
                Type.RIFLE -> 1.0f
                Type.SLINGSHOT -> 1.0f
                Type.CANNON -> 1.0f
                Type.GUN -> 1.0f
                Type.GRENADE -> 1.0f
            }
        }
    }

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}