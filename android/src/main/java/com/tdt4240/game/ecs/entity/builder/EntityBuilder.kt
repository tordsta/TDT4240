package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.component.*

// TODO: documentation
abstract class EntityBuilder(val engine: PooledEngine) {

    val entity: Entity = engine.createEntity()

    protected abstract fun createTexture(box_size: Int, color: Color): TextureRegion
    protected abstract fun createBodyDef(x: Float, y: Float): BodyDef
    protected abstract fun createFixtureDef(box_size: Int): FixtureDef

    fun texture(BOX_SIZE: kotlin.Int, Z_INDEX: Int, COLOR: Color): EntityBuilder {
        val textureComponent = engine.createComponent(Texture::class.java)

        textureComponent.texture = createTexture(BOX_SIZE, COLOR)
        textureComponent.scale = getTextureScale(textureComponent.texture, BOX_SIZE)
        textureComponent.zIndex = Z_INDEX

        entity.add(textureComponent)

        return this
    }

    protected open fun getTextureScale(textureRegion: TextureRegion, box_size: Int): Float {
        return if (textureRegion.regionWidth > 0) box_size.toFloat() / textureRegion.regionWidth else 1.0f
    }

    fun type(type: TypeComponent.Type): EntityBuilder {
        val typeComponent = engine.createComponent(TypeComponent::class.java)
        typeComponent.type = type

        entity.add(typeComponent)

        return this
    }

    fun physicsBody(x: Float, y: Float, world: World, box_size: Int): EntityBuilder {

        val bodyComponent = engine.createComponent(PhysicsBody::class.java)
        bodyComponent.body = world.createBody(createBodyDef(x, y))
        val fixture = bodyComponent.body.createFixture(createFixtureDef(box_size))
        fixture.userData = entity

        entity.add(bodyComponent)

        return this
    }

    fun position(x: Float = 0f, y: Float = 0f): EntityBuilder {
        val position = engine.createComponent(Position::class.java)
        position.position.x = x
        position.position.y = y
        entity.add(position)

        return this
    }


    fun bullet(damage: Int, range: Float): EntityBuilder {
        val bullet = engine.createComponent(com.tdt4240.game.ecs.component.Bullet::class.java)
        bullet.damage = damage
        bullet.range = range
        entity.add(bullet)

        return this
    }

    fun button(buttonType: ButtonType.Type): EntityBuilder {
        val buttonTypeComp = engine.createComponent(ButtonType::class.java)
        buttonTypeComp.type = buttonType
        entity.add(buttonTypeComp)

        return this
    }

    fun bounds(x: Float, y: Float, width: Float, height: Float): EntityBuilder {
        val bounds = engine.createComponent(BoundsComponent::class.java)
        bounds.bounds = Rectangle(x, y, width, height)
        entity.add(bounds)

        return this
    }


    fun userControllable(enabled: Boolean = false): EntityBuilder {
        val userControllable = engine.createComponent(UserControllable::class.java)
        userControllable.enable = enabled
        entity.add(userControllable)

        return this
    }

    fun playerID(id: String): EntityBuilder {
        val playerId = engine.createComponent(PlayerID::class.java)
        playerId.id = id
        entity.add(playerId)

        return this
    }

    fun weapon(type: WeaponType.Type): EntityBuilder {
        val weaponType = engine.createComponent(WeaponType::class.java)
        weaponType.type = type
        entity.add(weaponType)

        return this
    }

    fun health(healthPoints: Int): EntityBuilder {

        // Health
        val health = engine.createComponent(HealthComponent::class.java)
        health.healthPoints = healthPoints
        entity.add(health)

        return this
    }

    fun playerState(state: PlayerState.STATE): EntityBuilder {
        val stateComponent = engine.createComponent(PlayerState::class.java)
        stateComponent.type = state
        entity.add(stateComponent)

        return this
    }

    fun networkID(): EntityBuilder {
        entity.add(engine.createComponent(NetworkID::class.java))
        return this
    }


    fun build(): Entity {
        return entity
    }


}