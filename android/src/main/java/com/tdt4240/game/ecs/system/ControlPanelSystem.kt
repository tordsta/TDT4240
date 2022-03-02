package com.tdt4240.game.ecs.system


import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.*
import com.tdt4240.game.ecs.component.Delete
import com.tdt4240.game.ecs.entity.NavigationButtonBuildDirector

import com.tdt4240.game.ecs.system.util.Utils

class ControlPanelSystem : EntitySystem() {

    private val buttonActionMapper = ComponentMapper.getFor(ButtonActionComponent::class.java)
    private val physicsBodyMapper = ComponentMapper.getFor(PhysicsBody::class.java)

    private var enabled = true


    fun processButton(entity: Entity) {
        if (enabled) {
            val action = buttonActionMapper.get(entity)
            updatePlayerPosition(action.action)
        }
    }

    private fun updatePlayerPosition(action: ButtonActionComponent.Action?) {
        if (action == null) {
            return
        }

        val activePlayer = Utils.getActivePlayer(engine)

        if (activePlayer != null) {
            //Log.i("ControlPanelSystem", "Updating player position");

            val physicsBody = physicsBodyMapper.get(activePlayer)
            when (action) {
                ButtonActionComponent.Action.LEFT -> physicsBody.body.applyForceToCenter(Vector2(-1f, 0f).scl(FORCE_MULTIPLIER), true)
                ButtonActionComponent.Action.RIGHT -> physicsBody.body.applyForceToCenter(Vector2(1f, 0f).scl(FORCE_MULTIPLIER), true)
                ButtonActionComponent.Action.JUMP -> physicsBody.body.applyLinearImpulse(Vector2(0f, 1f).scl(IMPULSE_MULTIPLIER), physicsBody.body.worldCenter, true)
            }
        }
    }

    fun disposePanel() {
        navigationButtons().forEach { button -> button.add(Delete()) }
    }

    fun addPanel() {
        val Y_PADDING = 70

        val width = Gdx.graphics.width

        NavigationButtonBuildDirector.createPlayerNavigationButton(engine as PooledEngine, width - 200, Y_PADDING, ButtonActionComponent.Action.LEFT)
        NavigationButtonBuildDirector.createPlayerNavigationButton(engine as PooledEngine, width - 70, Y_PADDING, ButtonActionComponent.Action.RIGHT)
        NavigationButtonBuildDirector.createPlayerNavigationButton(engine as PooledEngine, 70, Y_PADDING, ButtonActionComponent.Action.JUMP)
    }

    fun enable() {
        enabled = true
        displayPanel()
    }

    fun disable() {
        enabled = false
        hidePanel()
    }

    private fun hidePanel() {
        navigationButtons().forEach{ it.hide() }
    }

    private fun displayPanel() {
        navigationButtons().forEach{ it.show() }
    }

    private fun navigationButtons(): ImmutableArray<Entity> {
        return engine.getEntitiesFor(Family.all(ButtonType::class.java, ButtonActionComponent::class.java).get())
    }

    companion object {

        private const val FORCE_MULTIPLIER = 100f
        private const val IMPULSE_MULTIPLIER = 1f
    }

}
