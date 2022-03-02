package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.*
import com.badlogic.gdx.ai.fsm.DefaultStateMachine
import com.tdt4240.game.ecs.system.util.Utils
import com.tdt4240.game.ecs.component.PlayerState as PlayerStateComponent

class PlayerStateSystem : EntitySystem() {

    private val stm = DefaultStateMachine<PlayerStateSystem, PlayerState>(this, PlayerState.INIT)

    override fun update(deltaTime: Float) {
        val localPlayerState = Utils.getLocalPlayerState(engine as PooledEngine) ?: return

        val state = playerStateToPlayerState(localPlayerState)
        if (stm.currentState !== state) {
            stm.changeState(state)
        }
    }

    private fun playerStateToPlayerState(playerState : PlayerStateComponent.STATE) : PlayerState{
        return when(playerState){
            PlayerStateComponent.STATE.MOVE -> PlayerState.MOVE
            PlayerStateComponent.STATE.SELECT_WEAPON -> PlayerState.SELECT_WEAPON
            PlayerStateComponent.STATE.SHOOT -> PlayerState.SHOOT
            PlayerStateComponent.STATE.WAIT -> PlayerState.WAIT
        }
    }

}
