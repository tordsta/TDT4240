package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.entity.BulletBuildDirector
import com.tdt4240.game.ecs.entity.MineBuildDirector
import com.tdt4240.game.ecs.entity.ObstacleBuildDirector
import com.tdt4240.game.ecs.entity.PlayerBuildDirector
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerBullet
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPlayerHealth
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPlayerMine
import com.tdt4240.game.ecs.system.util.collision.ContactHandlerPool
import org.junit.Assert.assertNotNull
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
class ContactHandlerPoolTest : GameTest() {

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var box: Entity? = null
    private var mine: Entity? = null
    private var player: Entity? = null
    private var bullet: Entity? = null


    private var contactHandlerPool: ContactHandlerPool = ContactHandlerPool()


    @Before
    fun setup() {

        contactHandlerPool.register(ContactHandlerPlayerMine())
        contactHandlerPool.register(ContactHandlerPlayerHealth())
        contactHandlerPool.register(ContactHandlerBullet())

        this.world = World(Vector2(0f, -10f), true)

        engine = PooledEngine()

        createScene()
    }

    fun createScene() {
        engine!!.removeAllEntities()

        val X_POSITION = 100

        // box is next to mine
        box = ObstacleBuildDirector.construct(engine!!, X_POSITION - 40, 200, world!!)
        mine = MineBuildDirector.construct(engine!!, X_POSITION, 200, world!!)

        player = PlayerBuildDirector.construct(engine!!, X_POSITION + 60, 200, world!!, "", true)

        bullet = BulletBuildDirector.construct(engine!!, X_POSITION + 13f, 2343f, world!!, 20, 1f)
    }

    /**
     * Test to let the
     *
     * Desired test result: The health should take damage.
     *
     * Measure: The health of the box should be reduced
     */
    @Test
    fun testCollisionPlayerBullet() {

        var contact = CustomContact(player!!.getComponent(PhysicsBody::class.java).body.fixtureList.first(),
                bullet!!.getComponent(PhysicsBody::class.java).body.fixtureList.first())

        var handler = contactHandlerPool.getContactHandler(contact)

        assertNotNull(handler)
        assertNotNull(handler as ContactHandlerBullet)

        contact = CustomContact(bullet!!.getComponent(PhysicsBody::class.java).body.fixtureList.first(),
                player!!.getComponent(PhysicsBody::class.java).body.fixtureList.first())

        handler = contactHandlerPool.getContactHandler(contact)

        assertNotNull(handler)
        assertNotNull(handler as ContactHandlerBullet)

    }

}
