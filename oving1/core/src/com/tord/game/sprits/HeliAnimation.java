package com.tord.game.sprits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.tord.game.Oving1;

public class HeliAnimation {
    private Vector3 position;
    private Vector3 velocity;
    private Array<Texture> textures = new Array<Texture>();
    private int width;
    private int height;
    private Animation heliAnimation;
    private int animationSpeed = 100; //ms per frame
    private Rectangle bounds;

    public HeliAnimation(int x, int y, int width, int height, int speedX, int speedY){
        position = new Vector3(x,y,0);
        velocity = new Vector3(speedX,speedY,0);
        this.width = width;
        this.height = height;
        //initialize array of textures
        Texture h1 = new Texture("heli1.png");
        Texture h2 = new Texture("heli2.png");
        Texture h3 = new Texture("heli3.png");
        Texture h4 = new Texture("heli4.png");
        this.textures.add(h1,h2,h3,h4);
        //send array of textures to animation
        heliAnimation = new Animation(this.textures, animationSpeed);
        bounds = new Rectangle(x,y,width,height);
    }

    public void update(float dt, boolean collision){
        //handles collision
        if(collision){
            velocity.x = velocity.x * (-1);
            velocity.y = velocity.y * (-1);
        }

        heliAnimation.update(dt);
        velocity.add(0,0,0);
        position.add(velocity.x, velocity.y,0 );

        //updates the bounds for the next iteration
        bounds = new Rectangle(position.x,position.y,width,height);

        //handle out of bounds
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
        return heliAnimation.getFrame();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {return bounds; }

}
