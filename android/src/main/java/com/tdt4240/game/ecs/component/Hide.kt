package com.tdt4240.game.ecs.component

import com.badlogic.ashley.core.Entity

/**
 * Component informing that an entity should be hidden (not rendered)
 */
class Hide : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}

// TODO: is this in use?
fun Entity.hide(){
    this.add(Hide())
}

fun Entity.show(){
    this.remove(Hide::class.java)
}