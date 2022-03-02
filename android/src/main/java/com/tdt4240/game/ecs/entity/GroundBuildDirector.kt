package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.component.Position
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.component.Texture as TextureComponent

object GroundBuildDirector {
    // val color: Color = Color(221f / 255, 81f / 255,  54f / 255, 1f)
    val color: Color = Color(255f / 255, 255f / 255, 255f / 255, 1f)

    fun construct(engine: PooledEngine, groundObject: MapObject, world: World, mapScalingWidth: Float, mapScalingHeight: Float) {
        val entity = engine.createEntity()


        val rectangleObject = groundObject as RectangleMapObject
        val rectangle = rectangleObject.rectangle


        val textureComponent = engine.createComponent(TextureComponent::class.java)
        // construct
        val pixmap = Pixmap((mapScalingWidth * rectangle.width).toInt(), (mapScalingHeight * rectangle.height).toInt(), Pixmap.Format.RGBA8888)

        pixmap.setColor(color)
        pixmap.fillRectangle(0, 0, rectangle.width.toInt(), rectangle.height.toInt())
        val texture = Texture(pixmap)
        textureComponent.texture = TextureRegion()
        textureComponent.texture.setRegion(texture)
        textureComponent.zIndex = ZIndex.GROUND
        pixmap.dispose()

        entity.add(textureComponent)


        val typeComponent = engine.createComponent(TypeComponent::class.java)
        typeComponent.type = TypeComponent.Type.GROUND
        entity.add(typeComponent)

        val physicsComponent = engine.createComponent(PhysicsBody::class.java)
        val bodyDef = BodyDef()


        val center = Vector2(
                mapScalingWidth * (rectangle.x + rectangle.width * 0.5f) * WorldSettings.PIXELS_TO_METERS,
                mapScalingHeight * (rectangle.y + rectangle.height * 0.5f) * WorldSettings.PIXELS_TO_METERS
        )

        bodyDef.position.x = center.x
        bodyDef.position.y = center.y
        bodyDef.type = BodyDef.BodyType.StaticBody

        physicsComponent.body = world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = PolygonShape()


        fun getRectangle(rectangle: Rectangle): PolygonShape {
            val polygon = PolygonShape()

            polygon.setAsBox(
                    mapScalingWidth * rectangle.width * 0.5f * WorldSettings.PIXELS_TO_METERS,
                    mapScalingHeight * rectangle.height * 0.5f * WorldSettings.PIXELS_TO_METERS,
                    Vector2(0f, 0f),
                    0.0f
            )

            return polygon
        }

        val shape = getRectangle(groundObject.rectangle)

        fixtureDef.shape = shape
        fixtureDef.density = 1f

        physicsComponent.body.createFixture(fixtureDef)
        physicsComponent.body.fixtureList.first().userData = entity

        entity.add(physicsComponent)

        val position = Position()
        position.position.x = center.x
        position.position.y = center.y
        entity.add(position)

        shape.dispose()

        engine.addEntity(entity)
    }
}