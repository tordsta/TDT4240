package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.Explosive
import com.tdt4240.game.ecs.component.HealthComponent
import com.tdt4240.game.ecs.entity.MineBuildDirector
import com.tdt4240.game.ecs.entity.ObstacleBuildDirector
import com.tdt4240.game.ecs.system.Collision
import com.tdt4240.game.ecs.system.Physics
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.tdt4240.game.ecs.system.Explosive as ExplosiveSystem


/**
 * Use case / scenario:
 *
 * An explosion affects all entities in its surrounding. The obstacles / boxes will take damage.
 *
 *
 */
class BoxExplosionTest : GameTest() {

    val NUM_STEPS = 100

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var box: Entity? = null
    private var mine: Entity? = null

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

        createScene()

    }

    fun createScene() {
        engine!!.removeAllEntities()

        val X_POSITION = 100

        // box is next to mine
        box = ObstacleBuildDirector.construct(engine!!, X_POSITION - 40, 200, world!!)
        mine = MineBuildDirector.construct(engine!!, X_POSITION, 200, world!!)

    }

    /**
     * Test to let the
     *
     * Desired test result: The health should take damage.
     *
     * Measure: The health of the box should be reduced
     */
    @Test
    fun testMineExplode() {
        val healthBefore = box!!.getComponent(HealthComponent::class.java).healthPoints

        // although nobody collided with the mine, we will let it explode
        val explosive = engine!!.createComponent(Explosive::class.java)
        explosive.strength = 10f
        mine!!.add(explosive)

        // let the user fall onto the mine
        for (step in 0..NUM_STEPS) {
            engine!!.update(1 / 60f)
        }

        val healthAfter = box!!.getComponent(HealthComponent::class.java).healthPoints

        assertTrue(healthAfter < healthBefore )
    }


}

