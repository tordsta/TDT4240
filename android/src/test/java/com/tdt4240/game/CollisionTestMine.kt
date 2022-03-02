package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.Explosive
import com.tdt4240.game.ecs.entity.MineBuildDirector
import com.tdt4240.game.ecs.entity.PlayerBuildDirector
import com.tdt4240.game.ecs.system.Collision
import com.tdt4240.game.ecs.system.Physics
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CollisionTestMine : GameTest() {

    val NUM_STEPS = 1000

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var player: Entity? = null
    private var mine: Entity? = null

    @Before
    fun setup() {

        world = World(Vector2(0f, -10f), true)
        engine = PooledEngine()

        // Physics
        val physicsSystem = Physics(world!!, 1)
        engine!!.addSystem(physicsSystem)

        val collisionSystem = Collision(world!!)
        engine!!.addSystem(collisionSystem)

        createScene()

    }

    fun createScene() {
        engine!!.removeAllEntities()

        val X_POSITION = 100

        // player is above mine
        player = PlayerBuildDirector.construct(engine!!, X_POSITION, 500, world!!, "", true)

        mine = MineBuildDirector.construct(engine!!, X_POSITION, 200, world!!)

    }

    private fun isAboutToExplode(entity: Entity?): Boolean {
        return entity!!.getComponent(Explosive::class.java) != null
    }

    /**
     * Test to let the player fall onto a mine.
     *
     * Desired test result: The mine should explode.
     *
     * Measure: The explosive component should be added to the mine entitiy
     */
    @Test
    fun testMineExplode() {
        // mine should not be active
        assertFalse(isAboutToExplode(mine))

        // let the user fall onto the mine
        for (step in 0..NUM_STEPS) {
            engine!!.update(1 / 60f)
        }

        // the mine should explode now
        assertTrue(isAboutToExplode(mine))
    }


}

