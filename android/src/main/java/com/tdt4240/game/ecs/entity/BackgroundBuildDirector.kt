package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.entity.builder.BackgroundEntityBuilder

/**
 * The BackgroundBuildDirector wraps the BackgroundEntityBuilder to create a background entity.
 *
 * This is just the background image.
 */
object BackgroundBuildDirector {

    fun construct(engine: PooledEngine, textureFilePath: String = "levels/background.jpg"): Entity {

        val entity = BackgroundEntityBuilder(engine, textureFilePath)
                .texture(0, ZIndex.BACKGROUND, Color())
                .position(Gdx.graphics.width / 2.0f * WorldSettings.PIXELS_TO_METERS, Gdx.graphics.height / 2.0f * WorldSettings.PIXELS_TO_METERS)
                .build()

        engine.addEntity(entity)
        return entity
    }

}

