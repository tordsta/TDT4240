package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.ButtonActionComponent
import com.tdt4240.game.ecs.component.ButtonType

/**
 * Entity for navigation button to navigate a user.
 */
object NavigationButtonBuildDirector {

    fun createPlayerNavigationButton(engine: PooledEngine, x: Int, y: Int, action: ButtonActionComponent.Action) {


        val texturePath = getPlayerNavigationTexturePath(action)
        val buttonType = ButtonType.Type.PLAYER_NAVIGATION

        val entity = ButtonBuildDirector.construct(engine, x, y, texturePath, buttonType)

        // Action
        val buttonAction = engine.createComponent(ButtonActionComponent::class.java)
        buttonAction.action = action
        entity.add(buttonAction)

        engine.addEntity(entity)
    }

    private fun getPlayerNavigationTexturePath(action: ButtonActionComponent.Action): String {
        return when (action) {
            ButtonActionComponent.Action.LEFT -> "controller/left.png"
            ButtonActionComponent.Action.RIGHT -> "controller/right.png"
            ButtonActionComponent.Action.JUMP -> "controller/jump.png"
        }
    }

}
