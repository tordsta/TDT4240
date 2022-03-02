package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.HealthComponent
import com.tdt4240.game.ecs.component.NetworkID
import com.tdt4240.game.ecs.component.TypeComponent

/**
 * Entity for an obstacle.
 */
object ObstacleBuildDirector {

    private const val BOX_SIZE: Int = 40
    private val COLOR: Color = Color.YELLOW

    fun construct(engine: PooledEngine, x: Int, y: Int, world: World): Entity {

        val obstacleCreator = BaseObstacleBuildDirector(BOX_SIZE, COLOR)

        val entity = obstacleCreator.create(engine, x, y, world)

        val healthComponent = engine.createComponent(HealthComponent::class.java)
        healthComponent.healthPoints = 100
        entity.add(healthComponent)

        val typeComponent = engine.createComponent(TypeComponent::class.java)
        typeComponent.type = TypeComponent.Type.OBSTACLE
        entity.add(typeComponent)

        // Network ID
        val networkComponent = engine.createComponent(NetworkID::class.java)
        entity.add(networkComponent)

        engine.addEntity(entity)

        return entity
    }
}


