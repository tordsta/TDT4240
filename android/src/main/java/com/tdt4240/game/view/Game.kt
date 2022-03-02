package com.tdt4240.game.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.tdt4240.game.Game
import com.tdt4240.game.model.GameModel

import com.tdt4240.game.ecs.system.Delete as DeleteSystem
import com.tdt4240.game.ecs.system.Explosive as ExplosiveSystem

class Game(game: Game) : ScreenView(game) {

    private val gameModel = GameModel()

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameModel.update(delta)
    }


}

