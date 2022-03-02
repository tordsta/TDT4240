package com.tdt4240.game.ecs.system

import android.util.Log
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.NetworkID
import com.tdt4240.game.ecs.component.NetworkIdProvider
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.entity.*
import com.tdt4240.game.networking.NetworkAdapter
import com.tdt4240.game.networking.NetworkMode

class Network(private val engine: PooledEngine, private val networkAdapter: NetworkAdapter)
    : IteratingSystem(Family.all(NetworkID::class.java).get()) {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        when (networkMode) {
            NetworkMode.RECEIVER -> networkAdapter.updateLocalEntity(entity)
            NetworkMode.SENDER -> networkAdapter.updateRemoteEntity(entity)
        }
    }

    override fun update(deltaTime: Float) {
        networkAdapter.networkMode = networkMode
        networkAdapter.processPendingCallbacks()

        if (networkAdapter.isInitialized()) {
            networkAdapter.beginUpdate()

            createNonExistingEntities()

            super.update(deltaTime)

            networkAdapter.endUpdate()
        }
    }


    // Entities
    private fun doesNotExistLocally(networkId: String): Boolean {
        val networkIdEntities = engine.getEntitiesFor(Family.all(NetworkID::class.java).get())

        fun idMatches(entity: Entity): Boolean {
            return entity.getComponent(NetworkID::class.java).id == networkId
        }

        return networkIdEntities.toArray().find { idMatches(it) } == null
    }

    var networkMode: NetworkMode = NetworkMode.RECEIVER


    private fun onlyRemoteNetworkIDs(): MutableList<String> {
        val onlyRemoteNetworkIds: MutableList<String> = mutableListOf()

        networkAdapter.allNetworkIDs()?.forEach {
            if (doesNotExistLocally(it)) {
                onlyRemoteNetworkIds.add(it)
            }
        }

        return onlyRemoteNetworkIds
    }

    private fun createNonExistingEntities() {
        val onlyRemoteNetworkIDs = onlyRemoteNetworkIDs()

        //Log.i("ENTITY DIFFERENCE", "remote:" + onlyRemoteNetworkIds.size.toString() + " all" + size)

        onlyRemoteNetworkIDs.forEach {
            Log.i("Network system", "Creating entity $it")
            createEntity(it, engine, engine.getSystem(Physics::class.java).world)
        }
    }


    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        networkAdapter.leaveRoom()
        println("CLEANUP, unsubscribe from room entities and rooms.")
    }

    fun localPlayerID(): String? {
        return networkAdapter.localPlayerID()
    }

    private fun createEntity(networkId: String, engine: PooledEngine, world: World) {
        val type = networkAdapter.getEntityType(networkId)

        if(type == null){
            Log.w("Network system", "The entity for networkID $networkId cannot be created because the type is not available!")
            return
        }

        NetworkIdProvider.instance.nextId = networkId
        // the entity (prototype) will be added to engine
        createEntityPrototype(type, engine, world, networkId)
    }


    private fun createEntityPrototype(type: Any, engine: PooledEngine, world: World, networkId: String): Entity? {
        var entity: Entity? = null

        // construct a prototype
        when (TypeComponent.Type.valueOf(type.toString())) {
            TypeComponent.Type.PLAYER -> {
                entity = PlayerBuildDirector.construct(engine, 0, 0, world, "", false)
            }

            TypeComponent.Type.POWERUP -> {
                entity = PowerUpBuildDirector.construct(engine, 0, 0, world)
            }

            TypeComponent.Type.BULLET -> {
                entity = BulletBuildDirector.construct(engine, 0f, 0f, world, 20, 1f)
            }

            //            TypeComponent.Type.BUTTON -> {
            //                TODO("does not make sense to add this?")
            //            }

            TypeComponent.Type.HEALTH -> {
                entity = HealthDisplayBuildDirector.construct(engine, 0, 0)
            }

            //            TypeComponent.Type.GROUND -> {
            //                TODO("does not make sense to add this?")
            //            }

            TypeComponent.Type.MINE -> {
                entity = MineBuildDirector.construct(engine, 0, 0, world)
            }

            //            TypeComponent.Type.MISSILE -> {
            //                TODO("missile builder does not exist")
            //            }

            TypeComponent.Type.OBSTACLE -> {
                entity = ObstacleBuildDirector.construct(engine, 0, 0, world)
            }

            //            TypeComponent.Type.WEAPON -> {
            //                TODO("weapon builder does not exist")
            //            }

            else -> {
                Log.w("Network data reader", "Don't know how to construct entity '$networkId' of type '$type'")
            }
        }
        return entity
    }


}
