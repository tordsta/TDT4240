package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.tdt4240.game.ecs.component.TypeComponent
import java.util.*

/**
 * The purpose of this class is to get access to an entity that represents one of the contact pair elements
 *
 */
class ContactEntityMapper(private val type: TypeComponent.Type) {
    private val typeMapper = ComponentMapper.getFor(TypeComponent::class.java)


    fun has(contact: Contact): Boolean {
        for (o in Arrays.asList(contact.fixtureA.userData, contact.fixtureB.userData)) {
            if (typeMapper.has(o as Entity)) {
                if (typeMapper.get(o).type === type) {
                    return true
                }
            }else{
               throw Exception("invalid object")
            }
        }
        return false
    }

    operator fun get(contact: Contact): Entity? {
        for (o in Arrays.asList(contact.fixtureA.userData, contact.fixtureB.userData)) {
            if (typeMapper.has(o as Entity)) {
                if (typeMapper.get(o).type === type) {
                    return o
                }
            }
        }
        return null
    }
}