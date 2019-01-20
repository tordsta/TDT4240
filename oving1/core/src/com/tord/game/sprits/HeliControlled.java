package com.tord.game.sprits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;

public class HeliControlled {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private int width;
    private int height;
    private int speed = 0;
    private int movementSpeed = 2;

    public HeliControlled(int x, int y, int width, int height){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("attackhelicopter.PNG");
        this.width = width;
        this.height = height;
    }

    public void update(float dt){
        velocity.add(0,0,0);
        position.add(velocity.x, velocity.y,0 );

        //up movement, checks upper border
        if(Gdx.input.isKeyPressed(Input.Keys.W) && position.y + height <= Oving1.HEIGHT){
            position.add(0,movementSpeed,0);
        }
        //left movement, checks left border
        if(Gdx.input.isKeyPressed(Input.Keys.A) && position.x >= 0){
            position.add(-movementSpeed,0,0);
        }
        //right movement, checks right border
        if(Gdx.input.isKeyPressed(Input.Keys.D) && position.x + width <= Oving1.WIDTH){
            position.add(movementSpeed,0,0);
        }
        //bottom movement, checks bottom border
        if(Gdx.input.isKeyPressed(Input.Keys.S) && position.y >= 0){
            position.add(0,-movementSpeed,0);
        }


        //handle out of bounds
        //if for hver av de fire kantene om den er utenfor skjermen
        //gang hastighet med -1
        //changes velocity for hitting the right side
        if(position.x + width >= Oving1.WIDTH){
            velocity.x = velocity.x * (-1);
        }
        //changes velocity for hitting the left side
        if(position.x <= 0){
            velocity.x = velocity.x * (-1);
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
