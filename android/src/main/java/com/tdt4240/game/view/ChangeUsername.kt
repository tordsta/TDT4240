package com.tdt4240.game.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.tdt4240.game.Game
import com.tdt4240.game.controller.UsernamePresenter
import com.tdt4240.game.view.utils.addButtonToTable
import com.tdt4240.game.view.utils.addPadding

/**
 * View for changing the username.
 */
// TODO: refactor to ChangePlayername?
class ChangeUsername internal constructor(game: Game) : ScreenView(game) {

    private val usernameP : UsernamePresenter = UsernamePresenter()

    override fun show() {
        val table = Table()
        table.debug = true
        table.setFillParent(true)
        table.top()
        stage.addActor(table)

        val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

        val setUsernameButton = TextButton("Save username", skin)
        val usernameTextField = TextField(usernameP.username, skin)
        addPadding(table)
        table.add(usernameTextField)
        addPadding(table)
        addButtonToTable(table, setUsernameButton)

        setUsernameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                usernameP.username = usernameTextField.text
                game.screen = Menu(game)
            }
        })
    }

}