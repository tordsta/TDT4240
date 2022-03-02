package com.tdt4240.game.model

import android.util.Log
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ai.fsm.DefaultStateMachine
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.GameState
import com.tdt4240.game.Priority
import com.tdt4240.game.controller.ShootInputHandler
import com.tdt4240.game.controller.UserControlInputHandler
import com.tdt4240.game.ecs.component.PlayerState
import com.tdt4240.game.ecs.entity.BackgroundBuildDirector
import com.tdt4240.game.ecs.entity.HealthDisplayBuildDirector
import com.tdt4240.game.ecs.system.*
import com.tdt4240.game.ecs.system.util.Utils
import com.tdt4240.game.networking.GameSocketClient
import com.tdt4240.game.networking.NetworkAdapter
import com.tdt4240.game.networking.NetworkMode
import com.tdt4240.game.utils.SceneBuilder
import com.tdt4240.game.view.Game

/**
 * The GameModel contains the ECS engine with all the entities and systems.
 */
class GameModel {

    private lateinit var network: Network
    private lateinit var controlPanelSystem: ControlPanelSystem
    private lateinit var weaponPanelSystem: WeaponPanelSystem
    private lateinit var physics: Physics

    val engine: PooledEngine = PooledEngine()

    private lateinit var camera: Camera
    private var batch: SpriteBatch = SpriteBatch()

    // 0 is the gravity in horizontal direction, and -10f is the downward force
    private val world: World = World(Vector2(0f, -10f), true)

    private val sceneBuilder = SceneBuilder(TmxMapLoader().load("levels/levelData.tmx"), engine, world)

    private val gameSocketClient = GameSocketClient()


    init {
        Log.i("Game", "Init engine")
        initEngine()
        registerInputProcessors()
    }


    val stm = DefaultStateMachine<GameModel, GameState>(this, GameState.INIT)


    /**
     * Creates the different inputHandlers.
     */
    private fun registerInputProcessors() {
        // Two separate input handlers: one for buttons and one for shooting
        val userControlInputHandler = UserControlInputHandler(engine)
        val shootInputHandler = ShootInputHandler(engine)

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(userControlInputHandler)
        inputMultiplexer.addProcessor(shootInputHandler)
        Gdx.input.inputProcessor = inputMultiplexer

    }

    /**
     * Initialize the engine with all the systems
     */
    private fun initEngine() {
        // Rendering
        val renderingSystem = Render(batch, world)
        camera = renderingSystem.camera
        batch.projectionMatrix = camera.combined
        engine.addSystem(renderingSystem)

        // Control panel
        controlPanelSystem = ControlPanelSystem()
        engine.addSystem(controlPanelSystem)

        // Weapon select panel
        weaponPanelSystem = WeaponPanelSystem()
        engine.addSystem(weaponPanelSystem)

        // Bounds
        engine.addSystem(Bounds(Priority.BOUNDS))

        // Physics
        physics = Physics(world, Priority.PHYSICS)
        engine.addSystem(physics)

        // Collision
        engine.addSystem(Collision(world))

        // Explosive
        engine.addSystem(Explosive())

        // Delete
        val deleteSystem = Delete(world, Priority.DELETE)
        deleteSystem.deletionListener = object : DeletionListener {
            override fun onPlayerDiedCallback(playerID: String) {
                // TODO transit to view showing which player has won / died
                //renderingSystem.drawWinText()
                if (network.localPlayerID() == playerID) {
                    renderingSystem.setGameEndText("YOU LOST!")
                } else {
                    renderingSystem.setGameEndText("YOU WON!")
                }
            }
        }
        engine.addSystem(deleteSystem)

        // Network

        val networkAdapter = NetworkAdapter(gameSocketClient, this, sceneBuilder)
        network = Network(engine, networkAdapter)
        engine.addSystem(network)

        // BulletBuildDirector
        engine.addSystem(BulletSystem(world))

        // PlayerBuildDirector state
        engine.addSystem(PlayerStateSystem())

    }

    /**
     * Creates the scene and then entities.
     */
    fun createScene() {
        BackgroundBuildDirector.construct(engine, "levels/background.jpg")

        sceneBuilder.createStaticSceneFromMap()

        engine.getSystem(ControlPanelSystem::class.java).addPanel()

        HealthDisplayBuildDirector.construct(engine, 100, 900)
    }

    fun login(){
        gameSocketClient.login()
    }

    fun update(delta: Float) {
        stm.update()

        engine.update(delta)
    }

    /**
     * After calling this, the player gets control
     * and data is being sent to the network
     * to other players.
     */
    fun setPlayerActiveMode(){
        network.networkMode = NetworkMode.SENDER
        physics.mode = Physics.Mode.ACTIVE

        val playerID = network.localPlayerID()
        Utils.setActivePlayer(engine, playerID)

        Utils.setPlayerState(engine, playerID, PlayerState.STATE.MOVE)
    }

    /**
     * After calling this, the player's loses control
     * and data is being received through the network.
     */
    fun setPlayerInactiveMode(){
        network.networkMode = NetworkMode.RECEIVER
        physics.mode = Physics.Mode.PASSIVE

        val playerID = network.localPlayerID()

        Utils.setPlayerState(engine, playerID, PlayerState.STATE.WAIT)
    }

    fun setActiveIdle(){
        network.networkMode = NetworkMode.SENDER
        physics.mode = Physics.Mode.ACTIVE

        val playerID = network.localPlayerID()
        Utils.setActivePlayer(engine, playerID)

        Utils.setPlayerState(engine, playerID, PlayerState.STATE.WAIT)
    }

}