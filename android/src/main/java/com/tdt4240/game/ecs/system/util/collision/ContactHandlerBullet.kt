package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.tdt4240.game.ecs.component.Bullet
import com.tdt4240.game.ecs.component.Explosive
import com.tdt4240.game.ecs.component.TypeComponent

import java.util.*

/**
 * Contact handler for missiles.
 *
 *
 */
class ContactHandlerBullet : ContactHandler {

    private val bulletContactMapper = ContactEntityMapper(TypeComponent.Type.BULLET)

    override val contactTypes: ArrayList<TypeComponent.Type>
        get() = CONTACT_TYPES

    override fun handleContact(contact: Contact) {
        println("Exploded!!!!!")

        val explosive = Explosive()

        val bullet = bulletContactMapper[contact]

            if (bullet!!.getComponent(Bullet::class.java) != null){
                explosive.strength = bullet!!.getComponent(Bullet::class.java).damage.toFloat()
                explosive.range = bullet.getComponent(Bullet::class.java).range

                bullet.add(explosive)
            }
    }

    companion object {

        val CONTACT_TYPES = ArrayList(Arrays.asList(TypeComponent.Type.BULLET))
    }
}