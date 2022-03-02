package com.tdt4240.game

import com.badlogic.gdx.Gdx


/**
 * WorldSettings contains the dimensions of the screen in both the pixel system and the metric
 * system for the physics engine.
 */
object WorldSettings{
    // Amount of pixels each metre of box2d objects contains
    const val PIXELS_PER_METER = 100.0f
    // The ratio for converting pixels to metres
    const val PIXELS_TO_METERS = 1.0f / PIXELS_PER_METER
    val WORLD_WIDTH = Gdx.graphics.width * PIXELS_TO_METERS
    val WORLD_HEIGHT = Gdx.graphics.height * PIXELS_TO_METERS
}