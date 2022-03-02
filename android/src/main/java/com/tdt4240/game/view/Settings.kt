package com.tdt4240.game.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.tdt4240.game.Game
import com.tdt4240.game.view.utils.addButtonToTable
import com.tdt4240.game.view.utils.addLabelToTable
import com.tdt4240.game.view.utils.addPadding
import com.tdt4240.game.model.Settings as SettingsModel

/**
 * View of the settings.
 */
class Settings internal constructor(game: Game) : ScreenView(game) {

    private val table = Table()
    private val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
    private val soundLabel = Label("Sound", skin)
    private val soundButton = TextButton("Sound", skin)
    private val backButton = TextButton("Back", skin)

    init {
        soundButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                SettingsModel.toggleSound()
            }
        })
        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = Menu(game)
            }
        })

    }

    override fun show() {
        table.setFillParent(true)
        table.debug = true
        stage.addActor(table)
        stage.addActor(soundButton)
        addLabelToTable(table, soundLabel)
        addButtonToTable(table, soundButton)
        addPadding(table)
        addButtonToTable(table, backButton)
    }


}
