package com.tord.game.sprits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<Texture> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    //takes inn array of textures
    public Animation(Array<Texture> textures, int frameTime){
        //set frames to array of textures
        frames = textures;
        //int framesWidth = region.getRegionWidth() / frameCount;
        //for(int i = 0; i < frameCount; i++){
        //    frames.add(new TextureRegion(region, i*framesWidth, 0, framesWidth, region.getRegionHeight()));
        //}
        this.frameCount = textures.size;
        this.maxFrameTime = frameTime / 1000;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime =+ dt;
        //updates the frame
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        //restarts the animation
        if(frame >= frameCount){
            frame = 0;
        }
    }

    public Texture getFrame(){
        return frames.get(frame);
    }
}
