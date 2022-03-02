package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.tdt4240.game.WorldSettings


// TODO: documentation
class ObstacleEntityBuilder(engine: PooledEngine) : EntityBuilder(engine) {
    override fun createTexture(box_size: Int, color: Color): TextureRegion {

        val pixmap = Pixmap(box_size, box_size, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillRectangle(0, 0, box_size, box_size)

        val texture = com.badlogic.gdx.graphics.Texture(pixmap)
        val textureRegion = TextureRegion()
        textureRegion.setRegion(texture)
        //pixmap.dispose()

        return textureRegion
    }

    override fun createBodyDef(x: Float, y: Float): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.position.set(x.toFloat() * WorldSettings.PIXELS_TO_METERS, y.toFloat() * WorldSettings.PIXELS_TO_METERS)
        bodyDef.type = BodyDef.BodyType.DynamicBody
        return bodyDef
    }

    override fun createFixtureDef(box_size: Int): FixtureDef {
        var fixtureDef = FixtureDef()
        fixtureDef.shape = PolygonShape()

        var shape = PolygonShape()
        shape.setAsBox(box_size / 2 * WorldSettings.PIXELS_TO_METERS, box_size / 2 * WorldSettings.PIXELS_TO_METERS)
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        return fixtureDef
    }

}

