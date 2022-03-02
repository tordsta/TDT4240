package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.EntitySystem
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerBullet
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPlayerHealth
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPlayerMine
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPool
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.World

/**
 * The collision system handles collision between several types of entities
 * e.g. a player colliding with a mine.
 */
class Collision(private var world: World) : EntitySystem() {

    private var contactHandlerPool: ContactHandlerPool = ContactHandlerPool()

    init {
        contactHandlerPool.register(ContactHandlerPlayerMine())
        contactHandlerPool.register(ContactHandlerPlayerHealth())
        contactHandlerPool.register(ContactHandlerBullet())
    }

    override fun update(deltaTime: Float) {
        this.world.contactList.forEach { c -> handleContact(c) }
    }

    fun handleContact(contact: Contact) {
        val contactHandler = contactHandlerPool.getContactHandler(contact)
        contactHandler?.handleContact(contact)
    }

}
