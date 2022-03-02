package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.component.Position


/**
 * The physics system updates the physics engine.
 *
 * In active networkMode, the position component is updated by the position of the phyiscs body.
 * in passive networkMode, the beginUpdate is done vice versa.
 */
class Physics(val world: World, priority: Int) : IteratingSystem(Family.all(PhysicsBody::class.java, Position::class.java).get(), priority) {

    private val positionMapper = ComponentMapper.getFor(Position::class.java)
    private val physicsBodyMapper = ComponentMapper.getFor(PhysicsBody::class.java)

    enum class Mode{
        ACTIVE, // the physics engine is active, position component updated by physics body
        PASSIVE // the physics engine is not used, physics body updated by position component
    }

    var mode  = Mode.ACTIVE

    override fun update(deltaTime: Float) {
        if(mode == Mode.ACTIVE) {
            world.step(deltaTime, WORLD_VELOCITY_ITERATIONS, WORLD_POSITION_ITERATIONS)
        }

        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = positionMapper.get(entity)
        val physicsBody = physicsBodyMapper.get(entity)

        when(mode){
            Mode.ACTIVE -> {
                position.position = physicsBody.body.worldCenter
                position.orientation = physicsBody.body.angle
            }
            Mode.PASSIVE -> {
                physicsBody.body.setTransform(position.position, position.orientation)
            }
        }
    }

    companion object {
        private const val WORLD_VELOCITY_ITERATIONS = 6
        private const val WORLD_POSITION_ITERATIONS = 2
    }
}
