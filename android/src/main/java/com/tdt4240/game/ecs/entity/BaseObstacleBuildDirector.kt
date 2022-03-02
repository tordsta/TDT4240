package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.entity.builder.ObstacleEntityBuilder

/**
 * Creates an obstacle with
 * - TextureComponent
 * - PhysicsBodyComponent
 * - PositionComponent
 */
class BaseObstacleBuildDirector(val boxSize: Int, val color: Color) {

    fun create(engine: PooledEngine, x: Int, y: Int, world: World): Entity {

        return ObstacleEntityBuilder(engine)
                .physicsBody(x.toFloat(), y.toFloat(), world, boxSize)
                .position(x.toFloat() * WorldSettings.PIXELS_TO_METERS, y.toFloat() * WorldSettings.PIXELS_TO_METERS)
                .texture(boxSize, ZIndex.OBSTACLE, color)
                .build()
    }
}