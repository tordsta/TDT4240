package com.tdt4240.game.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.tdt4240.game.Game

/**
 * Base class for screens. This class initializes the stage.
 */
open class ScreenView internal  constructor(game: Game): Base(game){

    private val camera: OrthographicCamera = OrthographicCamera()
    var stage: Stage

    init{
        camera.setToOrtho(false)
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(
            game.backgroundColor.r,
            game.backgroundColor.g,
            game.backgroundColor.b,
            game.backgroundColor.a
        )
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()

        stage.draw()
    }
}