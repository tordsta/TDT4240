package com.tdt4240.game.networking

import com.badlogic.gdx.math.Vector2

data class Spawn(
    var taken: Boolean? = false,
    var x: Float = 0f,
    var y: Float = 0f
){
    fun position(): Vector2 {
        return Vector2(x, y)
    }
}