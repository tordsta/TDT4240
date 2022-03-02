package com.tdt4240.game

/**
 * ZIndex allows to easily change the "layering" of the rendered scene.
 * Elements with small indices are drawn first.
 *
 * ZIndex is used by the Rendering System.
 */
object ZIndex{

    const val BACKGROUND = 0

    const val GROUND = 1

    const val OBSTACLE = 100
    const val PLAYER = 100

    const val BUTTON = 100

    const val HEALTH_DISPLAY = 100

    const val WEAPON = 110
}