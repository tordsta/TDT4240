package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.component.PlayerID
import com.tdt4240.game.ecs.component.Delete as DeleteComponent

interface DeletionListener{
    fun onPlayerDiedCallback(playerID: String)
}

/**
 * The delete system is used to remove entities from the engine.
 *
 * This is preferred to removing entities directly as some components need to be cleaned up e.g.
 * the physics body has to be destroyed.
 */
class Delete(private val world: World, priority: Int = 0) : IteratingSystem(Family.all(DeleteComponent::class.java).get(), priority) {

    private val physicsBodyMapper = ComponentMapper.getFor(PhysicsBody::class.java)
    private val playerIDMapper = ComponentMapper.getFor(PlayerID::class.java)


    override fun processEntity(entity: Entity, deltaTime: Float) {
        // need to destroy body or otherwise an orphaned fixture will remain in the scene
        if(physicsBodyMapper.has(entity)) {
            world.destroyBody(physicsBodyMapper.get(entity).body)
        }

        if(playerIDMapper.has(entity)){
            val playerID = playerIDMapper.get(entity).id
            deletionListener?.onPlayerDiedCallback(playerID)
        }

        engine.removeEntity(entity)
    }

    var deletionListener: DeletionListener? = null



}
