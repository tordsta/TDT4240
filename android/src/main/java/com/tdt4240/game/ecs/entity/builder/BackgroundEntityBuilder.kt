package com.tdt4240.game.ecs.entity.builder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef

class BackgroundEntityBuilder(engine: PooledEngine, val textureFilePath: String) : EntityBuilder(engine) {

    override fun getTextureScale(textureRegion: TextureRegion, box_size: Int): Float {
        val widthScale = Gdx.graphics.width.toFloat() / textureRegion.regionWidth
        val heightScale = Gdx.graphics.height.toFloat() / textureRegion.regionHeight

        // val smallerScale = Math.min(width_scale, height_scale)
        val greaterScale = Math.max(widthScale, heightScale)

        return greaterScale


    }

    override fun createTexture(box_size: Int, color: Color): TextureRegion {
        val textureFile = com.badlogic.gdx.graphics.Texture(Gdx.files.internal(textureFilePath))
        textureFile.setFilter(com.badlogic.gdx.graphics.Texture.TextureFilter.Linear, com.badlogic.gdx.graphics.Texture.TextureFilter.Linear)

        return TextureRegion(textureFile)
    }

    override fun createBodyDef(x: Float, y: Float): BodyDef {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFixtureDef(box_size: Int): FixtureDef {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}