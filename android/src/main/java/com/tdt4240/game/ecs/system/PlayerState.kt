package com.tdt4240.game.ecs.system

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram

enum class PlayerState : State<PlayerStateSystem> {
    INIT {
        override fun enter(playerStateSystem: PlayerStateSystem) {}
    },

    MOVE {
        override fun enter(playerStateSystem: PlayerStateSystem) {
            playerStateSystem.engine.getSystem(ControlPanelSystem::class.java).enable()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).hidePanel()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).displayMenuButtons()
        }
    },

    SELECT_WEAPON {
        override fun enter(playerStateSystem: PlayerStateSystem) {
            playerStateSystem.engine.getSystem(ControlPanelSystem::class.java).disable()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).displayPanel()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).displayMenuButtons()
        }

        override fun exit(playerStateSystem: PlayerStateSystem) {
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).hidePanel()
        }

    },

    SHOOT {
        override fun enter(playerStateSystem: PlayerStateSystem) {
            playerStateSystem.engine.getSystem(ControlPanelSystem::class.java).disable()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).hidePanel()
            // TODO what playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).displayMenuButtons() or hide?
        }
    },

    WAIT {
        override fun enter(playerStateSystem: PlayerStateSystem) {
            playerStateSystem.engine.getSystem(ControlPanelSystem::class.java).disable()
            playerStateSystem.engine.getSystem(WeaponPanelSystem::class.java).disable()
        }
    };

    override fun exit(playerStateSystem: PlayerStateSystem) {}

    override fun onMessage(playerStateSystem: PlayerStateSystem, telegram: Telegram): Boolean {
        return false;
    }

    override fun update(playerStateSystem: PlayerStateSystem) {}
}