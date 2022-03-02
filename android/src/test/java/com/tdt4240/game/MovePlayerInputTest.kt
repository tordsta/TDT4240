package com.tdt4240.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.ButtonActionComponent
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.controller.UserControlInputHandler
import com.tdt4240.game.ecs.entity.NavigationButtonBuildDirector
import com.tdt4240.game.ecs.entity.PlayerBuildDirector
import com.tdt4240.game.ecs.system.ControlPanelSystem
import com.tdt4240.game.ecs.system.Physics
import org.junit.Assert.assertTrue
import org.junit.Before
import org.mockito.Mockito
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
class MovePlayerInputTest : GameTest() {

    private var world: World? = null

    private var engine: PooledEngine? = null

    private var player: Entity? = null


    private val WINDOW_HEIGHT = 720

    @Before
    fun setup() {

        // Mock the input producer
        // the user should always press the "RIGHT" button
        Gdx.input = Mockito.mock(Input::class.java)
        Mockito.`when`(Gdx.input.isTouched).thenReturn(true)
        Mockito.`when`(Gdx.input.x).thenReturn(X_BUTTON_LEFT)
        Mockito.`when`(Gdx.input.y).thenReturn(WINDOW_HEIGHT - Y_BUTTON_LEFT)

        Gdx.graphics = Mockito.mock(Graphics::class.java)
        Mockito.`when`(Gdx.graphics.height).thenReturn(WINDOW_HEIGHT)




        world = World(Vector2(0f, -10f), true)
        engine = PooledEngine()

        // Physics
        val physicsSystem = Physics(world!!, 1)
        engine!!.addSystem(physicsSystem)

        val controlPanelSystem = ControlPanelSystem()
        engine!!.addSystem(controlPanelSystem)


        NavigationButtonBuildDirector.createPlayerNavigationButton(engine!!, X_BUTTON_LEFT, Y_PADDING, ButtonActionComponent.Action.LEFT)
        NavigationButtonBuildDirector.createPlayerNavigationButton(engine!!, 200, Y_PADDING, ButtonActionComponent.Action.RIGHT)
        NavigationButtonBuildDirector.createPlayerNavigationButton(engine!!, 720, Y_PADDING, ButtonActionComponent.Action.JUMP)

        // Gdx.input.setInputProcessor(rawInputHandler);

        val X_POSITION_PLAYER_INITIAL = 100
        player = PlayerBuildDirector.construct(engine!!, X_POSITION_PLAYER_INITIAL, 200, world!!, "", true)


        val rawInputHandler = UserControlInputHandler(engine)
        Gdx.input.inputProcessor = rawInputHandler
    }

    val Y_PADDING = 70
    val X_BUTTON_LEFT = 70
    val Y_BUTTON_LEFT = Y_PADDING


    val NUM_STEPS = 100
    val POS_DIFF = 10



    /**
     * Test to let the
     *
     * Desired test result: The health should take damage.
     *
     * Measure: The health of the box should be reduced
     */
    //@Test
    fun testMovePlayerByButtonClick() {

        val initialPosition = player!!.getComponent(PhysicsBody::class.java).body.position.x
        println(initialPosition)

        for (step in 0..NUM_STEPS) {
            engine!!.update(1 / 60f)
        }


        val finalPosition = player!!.getComponent(PhysicsBody::class.java).body.position.x

        println(finalPosition)

        assertTrue(finalPosition - initialPosition > POS_DIFF)
    }

}

