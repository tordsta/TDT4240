package com.tdt4240.game.utils

import android.util.Log
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.tdt4240.game.ecs.entity.*
import java.util.*

class SceneBuilder(private val tiledMap: TiledMap, val engine: PooledEngine, val world: World) {
    private val spawnPoints = LinkedList<Vector2>()

    private fun mapObjects(type: String): List<MapObject> {
        fun isCertainMapObject(groundObject: MapObject): Boolean {
            return groundObject is RectangleMapObject || groundObject is PolygonMapObject
        }

        val layer = tiledMap.layers.get(type)

        return layer?.objects?.filter(::isCertainMapObject) ?: ArrayList()
    }

    private fun mapScalingWidth(): Float {
        val mapWidth = tiledMap.properties.get("width", Int::class.java)
        val tilePixelWidth = tiledMap.properties.get("tilewidth", Int::class.java)
        val mapPixelWidth = mapWidth * tilePixelWidth

        return Gdx.graphics.width.toFloat() / mapPixelWidth
    }

    private fun mapScalingHeight(): Float {
        val mapHeight = tiledMap.properties.get("height", Int::class.java)
        val tilePixelHeight = tiledMap.properties.get("tileheight", Int::class.java)
        val mapPixelHeight = mapHeight * tilePixelHeight

        return Gdx.graphics.height.toFloat() / mapPixelHeight
    }


    fun createStaticSceneFromMap() {
        mapObjects("collision").forEach(this::createGround)
        mapObjects("spawns").forEach(this::registerSpawnPoint)

        if (spawnPoints.isEmpty())
            Log.e("SceneBuilder", "No spawn points available!")
        else
            Log.i("SceneBuilder", "${spawnPoints.size} spawn points available")
    }

    fun createEntitiesForMap() {
        mapObjects("powerups").forEach(this::createPowerUp)
        mapObjects("mines").forEach(this::createMine)
        mapObjects("obstacles").forEach(this::createObstacles)
    }

    private fun createGround(mapObject: MapObject) {
        GroundBuildDirector.construct(engine, mapObject, world, mapScalingWidth(), mapScalingHeight())
    }

    private fun createObstacles(mapObject: MapObject) {
        ObstacleBuildDirector.construct(engine, mapObject.getPosition().x.toInt(),
                mapObject.getPosition().y.toInt() + 15, world)
    }

    private fun createPowerUp(mapObject: MapObject) {
        PowerUpBuildDirector.construct(engine, (mapScalingWidth() * mapObject.getPosition().x).toInt(),
                (mapScalingHeight() * mapObject.getPosition().y).toInt() + 15, world)
    }

    private fun registerSpawnPoint(mapObject: MapObject) {
        spawnPoints.add(mapObject.getPosition())
    }


    private fun createMine(mapObject: MapObject) {
        MineBuildDirector.construct(
                engine,
                mapObject.getPosition().x.toInt(),
                mapObject.getPosition().y.toInt() + 15,
                world)
    }

    private fun nextPlayerSpawnPoint(): Vector2 {
        return spawnPoints.pop()
    }

    fun addNewPlayer(newPlayerID: String) {
        val spawnPoint = nextPlayerSpawnPoint()
        Log.i("SOCKET:LOGIN:CREATE_PLAYER", "construct player $newPlayerID")
        PlayerBuildDirector.construct(
                engine,
                spawnPoint.x.toInt(),
                spawnPoint.y.toInt(),
                world,
                newPlayerID,
                false
        )
    }

}


fun MapObject.getIntProp(property_name: String): Int {
    return properties.get(property_name).toString().toInt()
}

fun MapObject.getFloatProp(property_name: String): Float {
    return properties.get(property_name).toString().toFloat()
}

fun MapObject.getPosition(): Vector2 {
    return Vector2(getFloatProp("x"), getFloatProp("y"))
}