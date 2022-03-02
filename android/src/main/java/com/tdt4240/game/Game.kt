package com.tdt4240.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.tdt4240.game.controller.UsernamePresenter
import com.tdt4240.game.view.Menu
import com.badlogic.gdx.Game as GDXGame

/**
 * The main controller for the whole game.
 *
 * Extending Game instead of ApplicationsAdapter because we use screens. TODO: is still still relevant?
 */
class Game : GDXGame() {

    // TODO: this one seems to have a lot of unused code.
    internal val TITLE = "Tanks can't jump"
    internal val WIDTH = 800f
    internal val HEIGHT = 400f


    lateinit var font: BitmapFont

    val backgroundColor = Color(0.5f, 0.5f, 0.5f, 1f)

    override fun create() {
        font = BitmapFont()
        this.setScreen(Menu(this))
    }

    override fun dispose() {
        font.dispose()
    }



}
