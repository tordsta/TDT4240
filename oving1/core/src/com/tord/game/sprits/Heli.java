package com.tord.game.sprits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;

public class Heli {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private int width;
    private int height;
    private int speed = 5;
    private Sprite sprite;
    private boolean firstTimeFlip = true;

    public Heli(int x, int y, int width, int height){
        position = new Vector3(x,y,0);
        velocity = new Vector3(speed,speed,0);
        texture = new Texture("attackhelicopter.PNG");
        sprite = new Sprite(texture);
        this.width = width;
        this.height = height;
    }

    public void update(float dt){
        velocity.add(0,0,0);
        position.add(velocity.x, velocity.y,0 );

        //handle out of bounds
        //if for hver av de fire kantene om den er utenfor skjermen
            //gang hastighet med -1
        //changes velocity for hitting the right side
        if(position.x + width >= Oving1.WIDTH){
            velocity.x = velocity.x * (-1);
            if(!firstTimeFlip){
                sprite.flip(true, false);
            } else {
                this.firstTimeFlip = false;
            }
        }
        //changes velocity for hitting the left side
        if(position.x <= 0){
            velocity.x = velocity.x * (-1);
            sprite.flip(true, false);
        }
        //changes velocity for hitting the top side
        if(position.y + height >= Oving1.HEIGHT){
            velocity.y = velocity.y * (-1);
        }
        //changes velocity for hitting the bottom side
        if(position.y <= 0) {
            velocity.y = velocity.y * (-1);
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() { return sprite; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
