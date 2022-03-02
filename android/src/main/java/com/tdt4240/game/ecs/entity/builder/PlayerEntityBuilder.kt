package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.tdt4240.game.WorldSettings

// TODO: documentation
class PlayerEntityBuilder(engine: PooledEngine) : EntityBuilder(engine) {
    override fun createTexture(box_size: Int, color: Color): TextureRegion {

        val textureFile = com.badlogic.gdx.graphics.Texture(Gdx.files.internal("badlogic.jpg"))
        textureFile.setFilter(com.badlogic.gdx.graphics.Texture.TextureFilter.Linear, com.badlogic.gdx.graphics.Texture.TextureFilter.Linear)

        return TextureRegion(textureFile)
    }


    override fun createBodyDef(x: Float, y: Float): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x * WorldSettings.PIXELS_TO_METERS, y * WorldSettings.PIXELS_TO_METERS)
        return bodyDef
    }

    override fun createFixtureDef(box_size: Int): FixtureDef {
        val box = PolygonShape()
        box.setAsBox(box_size * WorldSettings.PIXELS_TO_METERS / 2, box_size * WorldSettings.PIXELS_TO_METERS / 2)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = box
        fixtureDef.density = 0.9f
        fixtureDef.restitution = 0.5f

        //box.dispose()

        return fixtureDef
    }


}