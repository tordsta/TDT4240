package com.tdt4240.game.view.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

fun addButtonToTable(table: Table, newGameButton: TextButton) {
    newGameButton.label.setFontScale(1.2f)
    table.add(newGameButton).prefWidth(300f * density).prefHeight(40f * density)

}

fun addLabelToTable(table: Table, newLabel: Label) {
    table.add(newLabel).prefWidth(300f * density).prefHeight(40f * density)

}

fun addPadding(table: Table) {
    table.row().pad(10f, 0f, 10f, 0f)
}

private var density = Gdx.graphics.density
