package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.entity.PlayerBuildDirector
import com.tdt4240.game.ecs.system.Physics
import org.junit.Before

class MovementCollisionTest : GameTest() {

    val NUM_STEPS = 1000

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var player1: Entity? = null
    private var player2: Entity? = null

    private var mine: Entity? = null

    @Before
    fun setup() {

        world = World(Vector2(0f, -10f), true)
        engine = PooledEngine()

        // Physics
        val physicsSystem = Physics(world!!, 1)
        engine!!.addSystem(physicsSystem)

        createScene()

    }

    fun createScene() {
        engine!!.removeAllEntities()

        val X_LEFT_POS = 100
        val X_RIGHT_POS = 500

        // player is above mine
        player1 = PlayerBuildDirector.construct(engine!!, X_LEFT_POS, 200, world!!, "", true)
        player2 = PlayerBuildDirector.construct(engine!!, X_RIGHT_POS, 200, world!!, "", false)


    }


    val POS_DIFF = 10

    /**
     * Test to let the player fall onto a mine.
     *
     * Desired test result: The mine should explode.
     *
     * Measure: The explosive component should be added to the mine entitiy
     */
/*     @Test
   fun testMovePlayerIndirect() {
        // mine should not be active

        val initialPosition = player2!!.getComponent(PhysicsBody::class.java)!!.body.position.cpy()

        // let the user fall onto the mine
        for (step in 0..NUM_STEPS) {
            player1!!.getComponent(PhysicsBody::class.java).body.setLinearVelocity(10f, 0f)

            // loooop
            engine!!.beginUpdate(1 / 60f)
        }

        val endPosition = player2!!.getComponent(PhysicsBody::class.java)!!.body.position.cpy()

        // the mine should explode now
        assertTrue(endPosition.x - initialPosition.x > POS_DIFF)
    }*/


}

