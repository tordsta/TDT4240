package com.tdt4240.game.ecs.component

import com.badlogic.gdx.physics.box2d.Body

/**
 * Component storing the body as LibGdx Box2D Body
 */
class PhysicsBody : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    lateinit var body: Body}
