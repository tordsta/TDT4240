package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.component.Position
import com.tdt4240.game.ecs.component.Texture
import com.tdt4240.game.ecs.component.TypeComponent

/**
 * Entity for health display.
 * Displaying the health of the player in either left or right top corner.
 */
object HealthDisplayBuildDirector {

    fun construct(engine: PooledEngine, x: Int, y: Int): Entity {
        val entity = engine.createEntity()

        // Position
        val position = engine.createComponent(Position::class.java)
        position.position = Vector2(x * WorldSettings.PIXELS_TO_METERS, y * WorldSettings.PIXELS_TO_METERS)
        entity.add(position)

        // Texture
        entity.add(createTexture(engine))

        // Type
        val typeComponent = engine.createComponent(TypeComponent::class.java)
        typeComponent.type = TypeComponent.Type.HEALTH
        entity.add(typeComponent)


        engine.addEntity(entity)

        return entity
    }

    private fun createTexture(engine: PooledEngine): Texture {
        val texture = engine.createComponent(Texture::class.java)

        val textureFile = com.badlogic.gdx.graphics.Texture(Gdx.files.internal("like.png"))

        texture.texture = TextureRegion(textureFile)
        texture.zIndex = ZIndex.HEALTH_DISPLAY
        texture.scale = 0.15f

        return texture
    }
}


