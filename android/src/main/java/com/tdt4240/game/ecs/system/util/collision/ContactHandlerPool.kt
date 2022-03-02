package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.gdx.physics.box2d.Contact
import java.util.*

class ContactHandlerPool {

    private var contactHandlers = ArrayList<ContactHandler>()

    fun register(contactHandler: ContactHandler) {
        contactHandlers.add(contactHandler)
    }

    private fun hasTwoTypes(contactHandler: ContactHandler): Boolean {
        return contactHandler.contactTypes.size == 2
    }

    private fun hasOneType(contactHandler: ContactHandler): Boolean {
        return contactHandler.contactTypes.size == 1
    }

    fun getContactHandler(contact: Contact): ContactHandler? {
        if(contact.fixtureA == null || contact.fixtureB == null)
            return null

        // if a contact handler has only on type that it is interested in, then the other is irrelevant
        for (contactHandler in contactHandlers.stream().filter { c -> hasOneType(c) }){
            for (type in contactHandler.contactTypes){
                val mp = ContactEntityMapper(type)
                if(mp.has(contact)){
                    return contactHandler
                }
            }
        }

        // if a contact handler has two types, than both have to match
        for (contactHandler in contactHandlers.stream().filter { c -> hasTwoTypes(c)}) {
            // the contact handler should provide two different types of contact types that he can
            // handle

            var isMatchingHandler = true

            for (type in contactHandler.contactTypes) {
                val mp = ContactEntityMapper(type)

                if (!mp.has(contact)) {
                    isMatchingHandler = false
                    break
                }
            }

            if (isMatchingHandler) {
                return contactHandler
            }
        }

//        System.out.println("No contact handler found for " +
//                (contact.fixtureA.userData as Entity).getComponent(TypeComponent::class.java).type +
//                " / " +
//                (contact.fixtureB.userData as Entity).getComponent(TypeComponent::class.java).type)
        return null
    }

}