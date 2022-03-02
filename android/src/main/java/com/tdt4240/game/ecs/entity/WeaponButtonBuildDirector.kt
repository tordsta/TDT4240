package com.tdt4240.game.ecs.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.tdt4240.game.ecs.component.ButtonType
import com.tdt4240.game.ecs.component.WeaponPanelButton
import com.tdt4240.game.ecs.component.WeaponType

/**
 * Entity for weapon button.
 */
object WeaponButtonBuildDirector {

    private const val SCALE = 0.3f

    fun createWeaponNavigationButton(engine: PooledEngine, x: Int, y: Int, type: WeaponPanelButton.Type) {
        val texturePath = getWeaponNavigationTexturePath(type)
        val buttonType = ButtonType.Type.WEAPON_NAVIGATION

        val entity = ButtonBuildDirector.construct(engine, x, y, texturePath, buttonType)

        // Action
        val panelButtonType = engine.createComponent(WeaponPanelButton::class.java)
        panelButtonType.type = type
        entity.add(panelButtonType)


        entity.getComponent(com.tdt4240.game.ecs.component.Texture::class.java).scale = SCALE

        engine.addEntity(entity)
    }

    fun createWeaponSelectButton(engine: PooledEngine, x: Int, y: Int, type: WeaponType.Type) {
        val texturePath = getWeaponSelectTexturePath(type)
        val buttonType = ButtonType.Type.WEAPON_SELECT
        val entity = ButtonBuildDirector.construct(engine, x, y, texturePath, buttonType)

        // Weapon type
        val weaponType = engine.createComponent(WeaponType::class.java)
        weaponType.type = type
        entity.add(weaponType)

        val panelButtonType = engine.createComponent(WeaponPanelButton::class.java)
        panelButtonType.type = WeaponPanelButton.Type.SELECT
        entity.add(panelButtonType)


        entity.getComponent(com.tdt4240.game.ecs.component.Texture::class.java).scale = SCALE
        engine.addEntity(entity)
    }

    fun createShowWeaponsButton(engine: PooledEngine, x: Int, y: Int, type: WeaponType.Type): Entity {
        val texturePath = getWeaponSelectTexturePath(type)
        val buttonType = ButtonType.Type.SHOW_WEAPONS
        val entity = ButtonBuildDirector.construct(engine, x, y, texturePath, buttonType)

        // Weapon type
        val weaponType = engine.createComponent(WeaponType::class.java)
        weaponType.type = type
        entity.add(weaponType)

        entity.getComponent(com.tdt4240.game.ecs.component.Texture::class.java).scale = SCALE

        engine.addEntity(entity)
        return entity
    }

    fun startShootButton(engine: PooledEngine, x: Int, y: Int): Entity {
        val texturePath = "target.png"
        val buttonType = ButtonType.Type.START_SHOOT
        val entity = ButtonBuildDirector.construct(engine, x, y, texturePath, buttonType)

        entity.getComponent(com.tdt4240.game.ecs.component.Texture::class.java).scale = SCALE

        engine.addEntity(entity)
        return entity
    }

    private fun getWeaponNavigationTexturePath(type: WeaponPanelButton.Type): String {
        return when (type) {
            WeaponPanelButton.Type.LEFT -> "weapons/navigation/left.png"
            WeaponPanelButton.Type.RIGHT -> "weapons/navigation/right.png"
            else -> throw IllegalArgumentException("Weapon type do not exist:$type")
        }
    }

    fun getWeaponSelectTexturePath(type: WeaponType.Type): String {
        return when (type) {
            WeaponType.Type.BOMB -> "weapons/bomb.png"
            WeaponType.Type.RIFLE -> "weapons/rifle.png"
            WeaponType.Type.SLINGSHOT -> "weapons/slingshot.png"
            WeaponType.Type.CANNON -> "weapons/cannon.png"
            WeaponType.Type.GUN -> "weapons/gun.png"
            WeaponType.Type.GRENADE -> "weapons/grenade.png"
        }
    }

}
