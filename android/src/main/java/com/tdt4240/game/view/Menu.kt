package com.tdt4240.game.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.tdt4240.game.controller.UsernamePresenter
import com.tdt4240.game.view.utils.addButtonToTable
import com.tdt4240.game.view.utils.addLabelToTable
import com.tdt4240.game.view.utils.addPadding


/**
 * Menu screen.
 *
 * This view shows different buttons to transit to the
 * - game view (finding opponents),
 * - settings view
 * - changing username view
 * or leaving the game.
 */
class Menu(game: com.tdt4240.game.Game) : ScreenView(game) {

    private val usernamePresenter : UsernamePresenter = UsernamePresenter()

    private var userDisplayName = "Player 1"

    override fun show() {

        val table = Table()
        table.setFillParent(true)
        table.debug = true
        stage.addActor(table)

        val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

        // Creating the buttons
        val newGameButton = TextButton("Play", skin)
        val settingsButton = TextButton("Settings", skin)
        val exitButton = TextButton("Exit", skin)
        val setUsernameButton = TextButton("Change username", skin)
        val displayUserName = Label(usernamePresenter.username, skin)
        val userButton = TextButton(userDisplayName, skin)

        addButtonToTable(table, newGameButton)
        addPadding(table)
        addButtonToTable(table, settingsButton)
        addPadding(table)
        addButtonToTable(table, setUsernameButton)
        addPadding(table)
        addLabelToTable(table, displayUserName)
        addPadding(table)
        addButtonToTable(table, exitButton)
        addPadding(table)
        addButtonToTable(table, userButton)

        newGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.screen = Game(game)
            }
        })

        settingsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.screen = Settings(game)
            }
        })

        setUsernameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.screen = ChangeUsername(game)
            }
        })

        exitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Gdx.app.exit()
            }
        })

    }
}
