package com.tdt4240.game.ecs.system.util.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.tdt4240.game.ecs.component.Explosive
import com.tdt4240.game.ecs.component.TypeComponent
import java.util.*

class ContactHandlerPlayerMine : ContactHandler {

    private val playerMapper = ContactEntityMapper(TypeComponent.Type.PLAYER)
    private val mineMapper = ContactEntityMapper(TypeComponent.Type.MINE)

    override val contactTypes: ArrayList<TypeComponent.Type>
        get() = CONTACT_TYPES

    override fun handleContact(contact: Contact) {
        //Entity player = playerMapper.get(contact);
        val mine = mineMapper[contact]

        val explosive = Explosive()
        explosive.strength = MINE_STRENGTH

        mine!!.add(explosive)
    }

    companion object {

        val CONTACT_TYPES = ArrayList(Arrays.asList(TypeComponent.Type.PLAYER, TypeComponent.Type.MINE))

        const val MINE_STRENGTH = 10f
    }
}