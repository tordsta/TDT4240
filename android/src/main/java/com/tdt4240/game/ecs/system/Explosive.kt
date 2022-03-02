package com.tdt4240.game.ecs.system

import android.util.Log
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.Color
import com.tdt4240.game.ecs.component.*
import com.tdt4240.game.ecs.component.Delete
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Color.PURPLE
import com.tdt4240.game.ecs.component.HealthComponent



class Explosive : IteratingSystem(Family.all(com.tdt4240.game.ecs.component.Explosive::class.java, Position::class.java).get()) {

    private val positionMapper = ComponentMapper.getFor(Position::class.java)
    private val explosiveMapper = ComponentMapper.getFor(com.tdt4240.game.ecs.component.Explosive::class.java)
    private val physicsBodyMapper = ComponentMapper.getFor(PhysicsBody::class.java)
    private val healthMapper = ComponentMapper.getFor(HealthComponent::class.java)
    private val typeMapper = ComponentMapper.getFor(TypeComponent::class.java)

    private var physicsEntities: ImmutableArray<Entity>? = null
    private var healthEntities: ImmutableArray<Entity>? = null

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)

        physicsEntities = engine.getEntitiesFor(Family.all(PhysicsBody::class.java).get())
        healthEntities = engine.getEntitiesFor(Family.all(HealthComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val explosionPosition = positionMapper.get(entity)
        val explosion = explosiveMapper.get(entity)

        physicsEntities!!.forEach { physicsEntity -> applyExplosion(physicsEntity, explosion, explosionPosition) }
        healthEntities!!.forEach { healthEntity -> damageEntity(healthEntity, explosion, explosionPosition) }

        // remove the entity that exploded
        entity.add(Delete())
    }

    private fun applyExplosion(entity: Entity, explosion: com.tdt4240.game.ecs.component.Explosive, position: Position) {
        val body = physicsBodyMapper.get(entity)

        val bodyPosition = body.body.position

        // the explosion direction with the length as euclidean distance
        val explosionDirection = bodyPosition.sub(position.position)

        val distance = explosionDirection.len()

        if(distance > explosion.range){
            return
        }

        body.body.applyLinearImpulse(explosionDirection.nor().scl(IMPULSE_SCALING_FACTOR * explosion.strength / distance), body.body.worldCenter, true)

    }

    private fun damageEntity(entity: Entity, explosion: com.tdt4240.game.ecs.component.Explosive, position: Position) {
        if (!healthMapper.has(entity)) {
            return
        }

        val body = physicsBodyMapper.get(entity)
        val bodyPosition = body.body.position

        val explosionDirection = bodyPosition.sub(position.position)
        val distance = explosionDirection.len()

        if(distance > explosion.range){
            return
        }

        val strength = explosion.strength

        damageEntity(entity, strength)

    }

    private fun damageEntity(entity: Entity, strength: Float) {
        if (!healthMapper.has(entity)) {
            return
        }

        val health = healthMapper.get(entity)

        val healthPointsToDiminish = Math.round(strength)

        Log.i("Explosive system", "Reducing health of entity ${typeMapper.get(entity)?.type} by $healthPointsToDiminish points")

        health.decrease(healthPointsToDiminish)


        val type = typeMapper.get(entity).type

        // If is obstacle, change color to display health
        if (type == TypeComponent.Type.OBSTACLE) {
            handleChangeHealthLevel(entity);
        }

        // The entity has died
        if (health.isDead()) { // the entity has died
            handleDeath(entity)
        }
    }

    private fun handleDeath(entity: Entity) {
        entity.add(Delete())
    }

    companion object {
        private const val IMPULSE_SCALING_FACTOR = 0.01f
    }

    private fun handleChangeHealthLevel(entity: Entity) {
        val health = healthMapper.get(entity) ?: return

        val healthLevel = health.getHealthLevel()

        val healthColors = HashMap<Int, Color>()
        healthColors[1] = Color.PURPLE
        healthColors[2] = Color.ORANGE
        healthColors[3] = Color.YELLOW

        val healthColor = (healthColors as java.util.Map<Int, Color>).getOrDefault(healthLevel, Color.YELLOW)

        val texture = entity.getComponent(Texture::class.java)

        val pixmap = Pixmap(40, 40, Pixmap.Format.RGBA8888)
        pixmap.setColor(healthColor)
        pixmap.fillRectangle(0, 0, 40, 40)

        val newTexture = com.badlogic.gdx.graphics.Texture(pixmap)
        val textureRegion = TextureRegion()
        textureRegion.setRegion(newTexture)
        pixmap.dispose()

        texture.texture = textureRegion
    }
}
