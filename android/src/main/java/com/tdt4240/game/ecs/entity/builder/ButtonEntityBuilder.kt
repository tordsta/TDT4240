package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef

// TODO: documentation
class ButtonEntityBuilder(engine: PooledEngine) : EntityBuilder(engine) {
    override fun createTexture(box_size: Int, color: Color): TextureRegion {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createBodyDef(x: Float, y: Float): BodyDef {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFixtureDef(box_size: Int): FixtureDef {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}