package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ecs.component.BoundsComponent
import com.tdt4240.game.ecs.component.Position

/**
 * The bounds system updates the bounds component based on the position component
 * such that they are in sync.
 */
class Bounds(priority: Int) : IteratingSystem(Family.all(BoundsComponent::class.java, Position::class.java).get(), priority) {
    private val positionMapper = ComponentMapper.getFor(Position::class.java)
    private val boundsMapper = ComponentMapper.getFor(BoundsComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = positionMapper.get(entity)
        val bounds = boundsMapper.get(entity)

        bounds.bounds = Rectangle((position.position.x * WorldSettings.PIXELS_PER_METER - BOUNDS_SIZE / 2),
                (position.position.y * WorldSettings.PIXELS_PER_METER - BOUNDS_SIZE / 2), BOUNDS_SIZE, BOUNDS_SIZE)
    }
    companion object {
        private const val BOUNDS_SIZE = 100f
    }
}