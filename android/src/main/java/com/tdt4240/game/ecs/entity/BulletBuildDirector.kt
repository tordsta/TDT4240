package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.*
import com.tdt4240.game.WorldSettings

import com.tdt4240.game.ecs.component.*
import com.tdt4240.game.ecs.entity.builder.BulletEntityBuilder

/**
 * Entity for bullet.
 */
object BulletBuildDirector {

    private const val BOX_SIZE: Int = 20
    private val COLOR: Color = Color.BLACK
    private const val Z_INDEX = 110

    fun construct(engine: PooledEngine, x: Float, y: Float, world: World, damage: Int, range: Float): Entity {

        val entity = BulletEntityBuilder(engine)
                .texture(BOX_SIZE, Z_INDEX, COLOR)
                .type(TypeComponent.Type.BULLET)
                .physicsBody(x, y, world, BOX_SIZE)
                .position(x * WorldSettings.PIXELS_TO_METERS, y * WorldSettings.PIXELS_TO_METERS)
                .bullet(damage, range)
                .networkID()
                .build()

        engine.addEntity(entity)
        return entity
    }

}


