package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.physics.box2d.Contact
import com.tdt4240.game.ecs.component.Delete
import com.tdt4240.game.ecs.component.HealthComponent
import com.tdt4240.game.ecs.component.HealthPowerup
import com.tdt4240.game.ecs.component.TypeComponent
import java.util.*

class ContactHandlerPlayerHealth : ContactHandler {
    private val playerMapper = ContactEntityMapper(TypeComponent.Type.PLAYER)
    private val healthBoxMapper = ContactEntityMapper(TypeComponent.Type.POWERUP)

    private val healthComponentMapper = ComponentMapper.getFor(HealthComponent::class.java)
    private val healthPowerupMapper = ComponentMapper.getFor(HealthPowerup::class.java)

    override val contactTypes: ArrayList<TypeComponent.Type>
        get() = CONTACT_TYPES

    override fun handleContact(contact: Contact) {
        val player = playerMapper[contact]
        val healthBox = healthBoxMapper[contact]

        val healthComponent = healthComponentMapper.get(player!!)

        val hb = healthPowerupMapper.get(healthBox)
        if(hb != null) {
            healthComponent.increaseHealth(hb.powerUpPoints)
        }

        healthBox?.add(Delete())
    }

    companion object {
        val CONTACT_TYPES = ArrayList(Arrays.asList(TypeComponent.Type.PLAYER, TypeComponent.Type.POWERUP))
    }
}