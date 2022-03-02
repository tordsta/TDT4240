package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.HealthComponent
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.entity.MineBuildDirector
import com.tdt4240.game.ecs.entity.ObstacleBuildDirector
import com.tdt4240.game.ecs.entity.PlayerBuildDirector
import com.tdt4240.game.ecs.system.Collision
import com.tdt4240.game.ecs.system.Physics
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.tdt4240.game.ecs.system.Delete as DeleteSystem
import com.tdt4240.game.ecs.system.Explosive as ExplosiveSystem


/**
 * Use case / scenario:
 *      An explosion should damage entities.
 *      If they have no health left, they should be removed from the scene.
 *
 * Measure:
 *      Two objects, the mine itself and an obstacle should be removed from the engine.
 *
 *
 */
class BoxExplosionDeathTest : GameTest() {

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var box: Entity? = null
    private var mine: Entity? = null
    private var player: Entity? = null


    @Before
    fun setup() {



        world = World(Vector2(0f, -10f), true)
        engine = PooledEngine()

        // Physics
        val physicsSystem = Physics(world!!, 1)
        engine!!.addSystem(physicsSystem)

        val explosionSystem = ExplosiveSystem()
        engine!!.addSystem(explosionSystem)

        val collisionSystem = Collision(world!!)
        engine!!.addSystem(collisionSystem)

        val deleteSystem = DeleteSystem(world!!)
        engine!!.addSystem(deleteSystem)

        createScene()

    }

    fun createScene() {
        engine!!.removeAllEntities()

        val X_POSITION = 100

        // box is next to mine
        box = ObstacleBuildDirector.construct(engine!!, X_POSITION - 40, 200, world!!)
        mine = MineBuildDirector.construct(engine!!, X_POSITION, 200, world!!)

        player = PlayerBuildDirector.construct(engine!!, X_POSITION + 60, 200, world!!, "abc", true)

    }

    /**
     * Test to let the
     *
     * Desired test result: The health should take damage.
     *
     * Measure: The health of the box should be reduced
     */
    @Test
    fun testCollisionCausingEntityDeath() {

        var removedEntities : ArrayList<TypeComponent.Type> = ArrayList()

        engine!!.addEntityListener(object : EntityListener{
            override fun entityRemoved(entity: Entity?) {
                removedEntities.add(entity!!.getComponent(TypeComponent::class.java).type)
            }
            override fun entityAdded(entity: Entity?) {}
        })


        // although nobody collided with the mine, we will let it explode

        var contact = CustomContact(
                player!!.getComponent(PhysicsBody::class.java).body.fixtureList.first(),
                mine!!.getComponent(PhysicsBody::class.java).body.fixtureList.first())

        var collisionSystem = engine!!.getSystem(Collision::class.java)
        collisionSystem.handleContact(contact)


        // let the engine handle the explosion
        engine!!.update(1 / 60f)

        // the mine should be removed from the scene
        assertEquals(1, removedEntities.stream().filter { t  -> t == TypeComponent.Type.MINE}.count())
        // the box/obstacle should have lost health
        assert(box!!.getComponent(HealthComponent::class.java).healthPoints < 100)
    }

}

class CustomContact : Contact {
    var storedFixtureA : Fixture
    var storedFixtureB : Fixture

    override fun getFixtureA(): Fixture {
        return storedFixtureA
    }

    override fun getFixtureB(): Fixture {
        return storedFixtureB
    }

    constructor(fixtureA : Fixture, fixtureB : Fixture) : super(null, 10L){
        this.storedFixtureA = fixtureA
        this.storedFixtureB = fixtureB
    }


}
