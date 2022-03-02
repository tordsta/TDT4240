package com.tdt4240.game.ecs.component


class Delete : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }
}