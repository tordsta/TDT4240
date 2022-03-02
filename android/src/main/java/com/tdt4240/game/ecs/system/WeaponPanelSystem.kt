package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.tdt4240.game.ecs.entity.WeaponButtonBuildDirector
import com.tdt4240.game.ecs.system.util.Utils

import com.badlogic.gdx.math.MathUtils.floor
import com.tdt4240.game.ecs.component.*
import com.tdt4240.game.ecs.component.Delete

class WeaponPanelSystem : EntitySystem() {

    private val weaponTypeMapper = ComponentMapper.getFor(WeaponType::class.java)
    private val weaponPanelButtonMapper = ComponentMapper.getFor(WeaponPanelButton::class.java)
    private val buttonTypeMapper = ComponentMapper.getFor(ButtonType::class.java)
    private val textureMapper = ComponentMapper.getFor(Texture::class.java)

    private var weaponPanelPage: Int = 0
    private var showPanel: Boolean = false

    private val BUTTON_WIDTH = 200
    private val Y_PADDING = Gdx.graphics.height / 2
    private val X_PADDING = (Gdx.graphics.width - BUTTON_WIDTH * 5) / 2


    private lateinit var showWeaponsButton: Entity
    private lateinit var startShootButton: Entity

    init {
        weaponPanelPage = 0
        showPanel = false

    }

    fun processArrowButton(entity: Entity) {
        val button = weaponPanelButtonMapper.get(entity)
        // Get number of pages in the panel with weapon, showing 3 weapons per page
        val lastPage = floor(WeaponType.values().size / 3f)

        when (button.type) {
            WeaponPanelButton.Type.LEFT ->
                // If player pressed "go-left" button, and the panel is showing a later page than the first
                if (weaponPanelPage > 0) {
                    weaponPanelPage -= 1
                    switchPanelPage()
                }
            WeaponPanelButton.Type.RIGHT ->
                // If the player pressed "go-right" button, and isn't on the last page
                if (weaponPanelPage < lastPage - 1) {
                    weaponPanelPage += 1
                    switchPanelPage()
                }
            // WeaponPanelButton.Type.SELECT -> TODO()
            else -> return
        }
    }

    fun togglePanel() {
        if (showPanel) hidePanel() else displayPanel()
    }

    fun processWeapon(entity: Entity) {
        val weaponType = weaponTypeMapper.get(entity).type
        hidePanel()
        updateWeaponToPlayer(weaponType)
    }

    private fun updateWeaponToPlayer(weaponType: WeaponType.Type?) {
        if (weaponType != null) {
            val activePlayer = Utils.getActivePlayer(engine)

            if (activePlayer != null) {
                // Update the players weapon component
                val weaponTypeComponent = weaponTypeMapper.get(activePlayer)

                if (weaponTypeComponent != null)
                    weaponTypeComponent.type = weaponType
                else {
                    System.out.println("Error: player do not have a weapon type")
                }

            }

            updateShowWeaponButton(weaponType)
        }
    }

    private fun updateShowWeaponButton(weaponType: WeaponType.Type) {
        for (button in engine.getEntitiesFor(Family.all(ButtonType::class.java).get())) {
            val buttonType = buttonTypeMapper.get(button).type
            val texture = textureMapper.get(button)

            if (buttonType === ButtonType.Type.SHOW_WEAPONS) {
                val texturePath = WeaponButtonBuildDirector.getWeaponSelectTexturePath(weaponType)
                val textureFile = com.badlogic.gdx.graphics.Texture(Gdx.files.internal(texturePath))
                texture.texture = TextureRegion(textureFile)
            }
        }
    }

    private fun switchPanelPage() {
        // Remove current weaponButtons
        val weaponButtons = engine.getEntitiesFor(Family.all(WeaponType::class.java, ButtonType::class.java).get())

        for (i in weaponButtons.size() - 1 downTo 0) {
            val weaponButton = weaponButtons.get(i)

            val buttonType = buttonTypeMapper.get(weaponButton)


            if (buttonType.type === ButtonType.Type.WEAPON_SELECT) {
                engine.removeEntity(weaponButton)
            }
        }

        createWeaponButtons(Y_PADDING)
    }

    private fun createPanel() {
        WeaponButtonBuildDirector.createWeaponNavigationButton(engine as PooledEngine, calculateXPadding(0), Y_PADDING, WeaponPanelButton.Type.LEFT)
        WeaponButtonBuildDirector.createWeaponNavigationButton(engine as PooledEngine, calculateXPadding(4), Y_PADDING, WeaponPanelButton.Type.RIGHT)

        createWeaponButtons(Y_PADDING)


        val SIDE_PANEL_X_PADDING = 100
        showWeaponsButton = WeaponButtonBuildDirector.createShowWeaponsButton(engine as PooledEngine, SIDE_PANEL_X_PADDING, 800, WeaponType.Type.GUN)
        startShootButton = WeaponButtonBuildDirector.startShootButton(engine as PooledEngine, SIDE_PANEL_X_PADDING, 650)
    }

    private fun disposePanel() {
        panelButtons().forEach { button -> button.add(Delete()) }
    }


    fun displayMenuButtons() {
        showWeaponsButton.remove(Hide::class.java)
        startShootButton.remove(Hide::class.java)
    }

    fun hideMenuButtons() {
        showWeaponsButton.add(Hide())
        startShootButton.add(Hide())
    }

    fun enable() {
        displayMenuButtons()
    }

    fun disable() {
        hideMenuButtons()
        hidePanel()
    }

    fun hidePanel() {
        showPanel = false
        panelButtons().forEach { it.hide() }
    }

    fun displayPanel() {
        showPanel = true
        panelButtons().forEach { it.show()}
    }

    private fun createWeaponButtons(yPadding: Int) {
        val weaponTypes = WeaponType.values()

        val startIndex = weaponPanelPage * 3
        WeaponButtonBuildDirector.createWeaponSelectButton(engine as PooledEngine, calculateXPadding(1), yPadding, weaponTypes[startIndex])
        WeaponButtonBuildDirector.createWeaponSelectButton(engine as PooledEngine, calculateXPadding(2), yPadding, weaponTypes[startIndex + 1])
        WeaponButtonBuildDirector.createWeaponSelectButton(engine as PooledEngine, calculateXPadding(3), yPadding, weaponTypes[startIndex + 2])
    }

    private fun calculateXPadding(position: Int): Int {
        return X_PADDING + BUTTON_WIDTH * position
    }

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)

        createPanel()
        hidePanel()
    }

    private fun panelButtons(): ImmutableArray<Entity> {
        return engine.getEntitiesFor(Family.all(WeaponPanelButton::class.java).get())
    }
}
