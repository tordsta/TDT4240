package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ZIndex
import com.tdt4240.game.ecs.component.PlayerState
import com.tdt4240.game.ecs.component.TypeComponent
import com.tdt4240.game.ecs.component.WeaponType
import com.tdt4240.game.ecs.entity.builder.PlayerEntityBuilder

/**
 * Entity for a player.
 */
object PlayerBuildDirector {
    private const val WIDTH = 50
    private const val HEIGHT = 50

    fun construct(engine: PooledEngine, x: Int, y: Int, world: World, playerID: String, controllable: Boolean): Entity {

        val entity = PlayerEntityBuilder(engine)
                .type(TypeComponent.Type.PLAYER)
                .bounds(x.toFloat(), y.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())
                .texture(WIDTH, ZIndex.PLAYER, Color())
                .physicsBody(x.toFloat(), y.toFloat(), world, box_size = WIDTH)
                .position(x.toFloat() * WorldSettings.PIXELS_TO_METERS, y.toFloat() * WorldSettings.PIXELS_TO_METERS)
                .health(100)
                .playerState(PlayerState.STATE.WAIT)
                .playerID(playerID)
                .userControllable(controllable)
                .networkID()
                .weapon(WeaponType.Type.GUN)
                .build()


        engine.addEntity(entity)

        return entity
    }

}
