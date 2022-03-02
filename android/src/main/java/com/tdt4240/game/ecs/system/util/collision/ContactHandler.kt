package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.tdt4240.game.ecs.component.TypeComponent
import java.util.*

/**
 * Interface for contact handlers for a specific types of contacts.
 */
interface ContactHandler {
    val contactTypes: ArrayList<TypeComponent.Type>

    fun handleContact(contact: Contact)
}