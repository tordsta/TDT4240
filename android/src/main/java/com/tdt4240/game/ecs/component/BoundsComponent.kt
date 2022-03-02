package com.tdt4240.game.ecs.component

import com.badlogic.gdx.math.Rectangle

class BoundsComponent : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    lateinit var bounds : Rectangle
}