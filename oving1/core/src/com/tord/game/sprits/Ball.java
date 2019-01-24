package com.tord.game.sprits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;

public class Ball {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private int diameter;
    private int speed;
    private Pixmap pixmap;
    private Rectangle bounds;

    public Ball(int positionX, int positionY, int diameter, int speed){
        position = new Vector3(positionX,positionY,0);
        velocity = new Vector3(speed,speed,0);
        this.speed = speed;
        this.diameter = diameter;
        bounds = new Rectangle(position.x,position.y,diameter,diameter);
        pixmap = new Pixmap(diameter+1,diameter+1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(diameter/2, diameter/2, diameter/2);
        texture = new Texture(pixmap);
    }

    public void update(float dt, boolean collision) {
        //handles collision
        if(collision){
            velocity.x = velocity.x * (-1);
        }

        velocity.add(0, 0, 0);
        position.add(velocity.x, velocity.y, 0);

        //updates the bounds for the next iteration
        bounds = new Rectangle(position.x,position.y,diameter,diameter);

        //handels out of bounds
        //changes velocity for hitting the top side
        if(position.y + diameter >= Oving1.HEIGHT){
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

    public int getDiameter() {
        return diameter;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBounds() {return bounds; }

    public void setSpeed(int speed) {
        this.speed = speed;
        this.velocity = new Vector3(speed,speed,0);
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

}
