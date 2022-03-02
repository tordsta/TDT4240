package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.component.ButtonType
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.entity.builder.ButtonEntityBuilder

/**
 * Entity for button.
 */
object ButtonBuildDirector {

    fun construct(engine: PooledEngine, x: Int, y: Int, texturePath: String, buttonType: ButtonType.Type): Entity {

        // TODO migrate texture
        // Texture
        val tx = createTexture(engine, texturePath)


        val entity = ButtonEntityBuilder(engine)
                .type(TypeComponent.Type.BUTTON)
                .position(x * WorldSettings.PIXELS_TO_METERS, y * WorldSettings.PIXELS_TO_METERS)
                .button(buttonType)
                .bounds(x - tx.texture.regionWidth / 2f * tx.scale,
                        y - tx.texture.regionHeight / 2f * tx.scale,
                        tx.texture.regionWidth * tx.scale,
                        tx.texture.regionHeight * tx.scale)
                .build()


        entity.add(tx)


        return entity
    }

    private fun createTexture(engine: PooledEngine, texturePath: String): com.tdt4240.game.ecs.component.Texture {
        val texture = engine.createComponent(com.tdt4240.game.ecs.component.Texture::class.java)

        val textureFile = Texture(Gdx.files.internal(texturePath))
        textureFile.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        texture.texture = TextureRegion(textureFile)
        texture.zIndex = ZIndex.BUTTON
        texture.scale = 0.2f

        return texture
    }


}
