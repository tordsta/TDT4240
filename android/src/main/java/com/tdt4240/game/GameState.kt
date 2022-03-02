package com.tdt4240.game

import android.util.Log
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.tdt4240.game.model.GameModel

/**
 * The states for the game state machine.
 *
 * Controls the different states in the game, like enabling/disabling control panels,
 * and changing network mode (either SENDER, or RECEIVER).
 */
enum class GameState : State<GameModel> {


    INIT {
        override fun enter(gameModel: GameModel) {
            Log.i("StateMachine", "Entering INIT")
        }

        override fun update(gameModel: GameModel) {
            gameModel.stm.changeState(START_GAME)
        }

        override fun exit(gameModel: GameModel?) {}

        override fun onMessage(gameModel: GameModel?, telegram: Telegram?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    },

    START_GAME {
        override fun enter(gameModel: GameModel) {
            Log.i("StateMachine", "Entering GAME_STATE")
            gameModel.createScene()
            gameModel.login()
            gameModel.setPlayerInactiveMode()
        }

        override fun update(gameModel: GameModel) {}

        override fun exit(gameModel: GameModel?) {}

        override fun onMessage(gameModel: GameModel?, telegram: Telegram?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    },


    WAITING_TURN {

        override fun enter(gameModel: GameModel) {
            Log.i("StateMachine", "Entering WAITING_TURN")
            gameModel.setPlayerInactiveMode()
        }

        override fun update(gameModel: GameModel?) {}

        override fun exit(gameModel: GameModel?) {
            //gameModel!!.setPlayerActiveMode()
        }

        override fun onMessage(gameModel: GameModel?, telegram: Telegram?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    },

    YOUR_TURN {

        override fun enter(gameModel: GameModel) {
            Log.i("StateMachine", "Entering YOUR_TURN")
            gameModel.setPlayerActiveMode()
        }

        override fun update(gameModel: GameModel) {}

        override fun exit(gameModel: GameModel) {
            gameModel.setPlayerInactiveMode()
        }

        override fun onMessage(gameModel: GameModel, telegram: Telegram?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    },

    WAIT_FOR_PLAYER_TO_JOIN {
        override fun enter(gameModel: GameModel) {
            Log.i("StateMachine", "Entering WAIT_FOR_PLAYER_TO_JOIN")
            gameModel.setActiveIdle()
        }

        override fun update(gameModel: GameModel) {}

        override fun exit(gameModel: GameModel) {
            //gameModel.setPlayerInactiveMode()
        }

        override fun onMessage(gameModel: GameModel, telegram: Telegram?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


}
