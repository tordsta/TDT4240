package com.tdt4240.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import com.tdt4240.game.WorldSettings
import com.tdt4240.game.ecs.component.*
import com.tdt4240.game.ecs.system.util.ZIndexComparator
import java.util.*

// this is based on https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/
class Render(private val spriteBatch: SpriteBatch, internal var world: World) : SortedIteratingSystem(Family.all(Texture::class.java, Position::class.java).exclude(Hide::class.java).get(), ZIndexComparator()) {
    private val textureMapper: ComponentMapper<Texture> = ComponentMapper.getFor(Texture::class.java)
    private val positionMapper: ComponentMapper<Position> = ComponentMapper.getFor(Position::class.java)
    private val healthMapper = ComponentMapper.getFor(HealthComponent::class.java)
    private val typeMapper = ComponentMapper.getFor(TypeComponent::class.java)

    // Sorts the images based on the z position of the positionComponent
    private val comparator: Comparator<Entity>? = null

    private val cam: OrthographicCamera = OrthographicCamera(WorldSettings.WORLD_WIDTH, WorldSettings.WORLD_HEIGHT)
    internal var viewport: Viewport? = null

    internal var debugRenderer = Box2DDebugRenderer()

    private var healthFont: BitmapFont? = null
    private var gameEndFont: BitmapFont? = null

    private var gameEndText : String? = null

    val camera: Camera
        get() = cam

    init {

        cam.position.set(WorldSettings.WORLD_WIDTH / 2, WorldSettings.WORLD_HEIGHT / 2, 0f)
        cam.update()
        createHealthFont()

        createGameEndFont()
    }// Gets all entities with a Texture and Position component

    override fun update(deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cam.update()
        spriteBatch.projectionMatrix = cam.combined
        spriteBatch.enableBlending()
        spriteBatch.begin()

        //System.out.println("beginUpdate");

        super.update(deltaTime)

        spriteBatch.end()

        //debugRenderer.render(world, cam.combined);
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = positionMapper.get(entity)
        val texture = textureMapper.get(entity)


        val width = texture.texture.regionWidth.toFloat()
        val height = texture.texture.regionHeight.toFloat()

        val originX = width / 2
        val originY = height / 2


        // If the entity is a player and is the current active player
        // TODO: Need to know if it is the player of the current screen
        if (typeMapper.has(entity)) {
            if (typeMapper.get(entity).type === TypeComponent.Type.PLAYER) {
                drawHealth(entity)
            }
        }

        if (gameEndText != null) {
            drawGameEndText()
        }

        spriteBatch.draw(texture.texture,
                position.position.x - originX,
                position.position.y - originY,
                originX,
                originY,
                width,
                height,
                texture.scale * WorldSettings.PIXELS_TO_METERS,
                texture.scale * WorldSettings.PIXELS_TO_METERS,
                Math.toDegrees(position.orientation.toDouble()).toFloat())
    }

    private fun drawHealth(entity: Entity) {
        val healthComponent = healthMapper.get(entity)
                ?: //Log.w("HealthDisplayBuildDirector", "invalid health component!");
                return

        val healthPoints = healthComponent.healthPoints
        val xPosition = 20 * WorldSettings.PIXELS_TO_METERS
        val yPosition = 1000 * WorldSettings.PIXELS_TO_METERS

        healthFont!!.draw(spriteBatch, Integer.toString(healthPoints), xPosition, yPosition)
    }

    private fun createHealthFont() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size = 80
        parameter.color = Color.RED

        healthFont = generator.generateFont(parameter)

        healthFont!!.data.setScale(0.023f)
        generator.dispose()

    }

    private fun createGameEndFont() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size = 80
        parameter.color = Color.RED

        gameEndFont = generator.generateFont(parameter)

        gameEndFont!!.data.setScale(0.023f)
        generator.dispose()


    }
    private fun drawGameEndText() {
        val xPosition = ((Gdx.graphics.width / 2) - 300) * WorldSettings.PIXELS_TO_METERS
        val yPosition = (Gdx.graphics.height / 2) * WorldSettings.PIXELS_TO_METERS  + (100 * WorldSettings.PIXELS_TO_METERS)

        gameEndFont!!.draw(spriteBatch, gameEndText, xPosition, yPosition)
    }

    fun setGameEndText(text : String) {
        this.gameEndText = text
    }
}
