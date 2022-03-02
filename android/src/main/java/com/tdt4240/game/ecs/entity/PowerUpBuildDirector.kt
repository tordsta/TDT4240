package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.HealthPowerup
import com.tdt4240.game.ecs.component.NetworkID
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.entity.ObstacleBuildDirector as ObstacleComponent

object PowerUpBuildDirector {

    private const val HEALTH_POINTS: Int = 100

    private const val BOX_SIZE: Int = 40
    private val COLOR: Color = Color(1.0f, 0.0f, 0.0f, 1.0f)

    fun construct(engine: PooledEngine, x: Int, y: Int, world: World): Entity {
        val obstacleCreator = BaseObstacleBuildDirector(BOX_SIZE, COLOR)
        val entity = obstacleCreator.create(engine, x, y, world)

        val typeComponent = engine.createComponent(TypeComponent::class.java)
        typeComponent.type = TypeComponent.Type.POWERUP
        entity.add(typeComponent)

        val healthComponent = engine.createComponent(HealthPowerup::class.java)
        healthComponent.powerUpPoints = HEALTH_POINTS
        entity.add(healthComponent)

        // Network ID
        val networkComponent = engine.createComponent(NetworkID::class.java)
        entity.add(networkComponent)

        engine.addEntity(entity)

        return entity
    }


}
