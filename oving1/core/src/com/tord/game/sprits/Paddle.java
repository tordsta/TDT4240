package com.tord.game.sprits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGB888;

public class Paddle {
    private Vector3 position;
    private Vector3 velocity;
    private int width;
    private int height;
    private int movementSpeed;
    private Rectangle bounds;
    private Texture texture;
    private int up;
    private int down;


    public Paddle(int positionX, int positionY, int width, int height, int movementSpeed, int up, int down){
        position = new Vector3(positionX,positionY,0);
        velocity = new Vector3(0,0,0);
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.up = up;
        this.down = down;
        texture = new Texture(width,height, RGB888);
        bounds = new Rectangle(positionX,positionY,width,height);
    }

    public void update(float dt){
        //up movement, checks upper border
        if(Gdx.input.isKeyPressed(up) && position.y + height <= Oving1.HEIGHT){
            position.add(0,movementSpeed,0);
        }
        //bottom movement, checks bottom border
        if(Gdx.input.isKeyPressed(down) && position.y >= 0){
            position.add(0,-movementSpeed,0);
        }

        bounds = new Rectangle(position.x,position.y,width,height);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture(){
        return texture;
    }
}
