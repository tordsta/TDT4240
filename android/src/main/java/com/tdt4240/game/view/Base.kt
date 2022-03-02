package com.tdt4240.game.view

import com.badlogic.gdx.Screen
import com.tdt4240.game.Game

open class Base constructor(protected var game: Game) : Screen {
    override fun hide() {}

    override fun show() {}

    override fun render(delta: Float) {}

    override fun pause() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {}
}