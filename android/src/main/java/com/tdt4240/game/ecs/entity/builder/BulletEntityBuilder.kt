package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.tdt4240.game.WorldSettings

/**
 * Builder for bullet entity.
 */
class BulletEntityBuilder(engine: PooledEngine) : EntityBuilder(engine) {

    override fun createTexture(box_size: Int, color: Color): TextureRegion {
        val pixmap = Pixmap(box_size, box_size, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillCircle(0, 0, box_size)

        val texture = com.badlogic.gdx.graphics.Texture(pixmap)
        val textureRegion = TextureRegion()
        textureRegion.setRegion(texture)
        pixmap.dispose()

        return textureRegion
    }

    override fun createBodyDef(x: Float, y: Float): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.position.set(x * WorldSettings.PIXELS_TO_METERS, y * WorldSettings.PIXELS_TO_METERS)
        bodyDef.type = BodyDef.BodyType.KinematicBody
        bodyDef.bullet = true

        return bodyDef
    }

    override fun createFixtureDef(box_size: Int): FixtureDef {
        val shape = CircleShape()
        shape.radius = box_size / 2 * WorldSettings.PIXELS_TO_METERS


        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 0.4f
        fixtureDef.restitution = 0f

        // TODO: keep or remove?
        // shape.dispose()

        return fixtureDef
    }

}