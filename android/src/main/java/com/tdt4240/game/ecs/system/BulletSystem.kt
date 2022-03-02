package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.Bullet
import com.tdt4240.game.ecs.component.Delete
import com.tdt4240.game.ecs.component.PhysicsBody
import com.tdt4240.game.ecs.component.WeaponType

/**
 * Performs actions on the bullet entities.
 * It is an EntitySystem instead of IteratingSystem so one can rather call the methods directly than looping over all of them.
 *
 */
class BulletSystem
/**
 * Constructor for the BulletSystem
 * @param world
 */
(private val world: World) : EntitySystem() {

    // Since maximum one should exist at all times
    private var bullet: Entity? = null

    private val physicsBodyMapper = ComponentMapper.getFor(PhysicsBody::class.java)


    /**
     * Called when a user shoots (realising the bullet).
     * @param velocityX
     * @param velocityY
     */
    fun shoot(velocityX: Float, velocityY: Float) {
        val body = physicsBodyMapper.get(bullet!!)

        if (body == null) {
            println("BulletBuildDirector has no body")
            return
        }
        // So that it doesn't fall at once
        body.body.type = BodyDef.BodyType.DynamicBody

        val impulse = Vector2(velocityX, velocityY).scl(IMPULSE_MULTIPLIER)
        body.body.applyLinearImpulse(impulse, body.body.worldCenter, true)
    }

    /**
     * Called when the user start the shooting process (before aiming and realising the bullet).
     * @param x The x position to the player.
     * @param y The y position to the player.
     */
    fun createBullet(x: Int, y: Int, weaponType: WeaponType) {
        disposeBullets()

        // The position of the bullet given the players position
        val Y_TO_THE_RIGHT_FOR_PLAYER = y + 100

        // Get the damage of the bullet based on the weapon
        val damage = WeaponType.bulletDamage(weaponType.type)
        val range = WeaponType.bulletRange(weaponType.type)
        bullet = com.tdt4240.game.ecs.entity.BulletBuildDirector.construct(engine as PooledEngine, x.toFloat(), Y_TO_THE_RIGHT_FOR_PLAYER.toFloat(), world, damage, range)
    }

    /**
     * Disposing of other bullets so only one can exist.
     */
    // Could we maybe rather have BulletEntity as singleton?
    private fun disposeBullets() {
        val bullets = engine.getEntitiesFor(Family.all(Bullet::class.java).get())

        bullets.forEach { entity -> entity.add(Delete()) }
    }

    companion object {

        private val IMPULSE_MULTIPLIER = 0.1f / 110
    }
}
