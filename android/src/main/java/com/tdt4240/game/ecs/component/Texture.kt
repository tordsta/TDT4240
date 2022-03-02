package com.tdt4240.game.ecs.component

import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Component storing the texture as a TextureRegion, zIndex and scale as float.
 */
class Texture : BaseComponent() {
    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    lateinit var texture: TextureRegion

    // TODO: zIndex is int
    var zIndex = 0

    var scale : Float = 1.0f
}
