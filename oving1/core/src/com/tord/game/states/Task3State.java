package com.tord.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.tord.game.Oving1;
import com.tord.game.sprits.Button;
import com.tord.game.sprits.HeliAnimation;

import java.util.Random;

public class Task3State extends State {
    private HeliAnimation heli1;
    private HeliAnimation heli2;
    private HeliAnimation heli3;
    private HeliAnimation heli4;
    private Texture background;
    private Button menuButton;
    private Random rand = new Random();

    public Task3State(GameStateManager gsm) {
        super(gsm);
        heli1 = new HeliAnimation(rand.nextInt(Oving1.WIDTH-162),rand.nextInt(Oving1.HEIGHT-65), 162, 65, rand.nextInt(10)-5,rand.nextInt(10)-5);
        heli2 = new HeliAnimation(rand.nextInt(Oving1.WIDTH-162),rand.nextInt(Oving1.HEIGHT-65), 162, 65, rand.nextInt(10)-5,rand.nextInt(10)-5);
        heli3 = new HeliAnimation(rand.nextInt(Oving1.WIDTH-162),rand.nextInt(Oving1.HEIGHT-65), 162, 65, rand.nextInt(10)-5,rand.nextInt(10)-5);
        heli4 = new HeliAnimation(rand.nextInt(Oving1.WIDTH-162),rand.nextInt(Oving1.HEIGHT-65), 162, 65, rand.nextInt(10)-5,rand.nextInt(10)-5);
        //cam.setToOrtho(false, Oving1.WIDTH*2, Oving1.HEIGHT*2);
        background = new Texture("background.jpg");
        menuButton = new Button(
                gsm,
                0,
                cam,
                (Oving1.WIDTH / 8 - (75/2)),
                (Oving1.HEIGHT - 80)
        );
    }


    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();

        //handles collision with other helicopters
        boolean collision1 = false;
        boolean collision2 = false;
        boolean collision3 = false;
        boolean collision4 = false;

        if(Intersector.overlaps(heli1.getBounds(), heli2.getBounds()) ||
           Intersector.overlaps(heli1.getBounds(), heli3.getBounds()) ||
           Intersector.overlaps(heli1.getBounds(), heli4.getBounds())
        ){ collision1 = true; }
        if(Intersector.overlaps(heli2.getBounds(), heli1.getBounds()) ||
           Intersector.overlaps(heli2.getBounds(), heli3.getBounds()) ||
           Intersector.overlaps(heli2.getBounds(), heli4.getBounds())
        ){ collision2 = true; }
        if(Intersector.overlaps(heli3.getBounds(), heli2.getBounds()) ||
           Intersector.overlaps(heli3.getBounds(), heli1.getBounds()) ||
           Intersector.overlaps(heli3.getBounds(), heli4.getBounds())
        ){ collision3 = true; }
        if(Intersector.overlaps(heli4.getBounds(), heli2.getBounds()) ||
           Intersector.overlaps(heli4.getBounds(), heli3.getBounds()) ||
           Intersector.overlaps(heli4.getBounds(), heli1.getBounds())
        ){ collision4 = true; }


        heli1.update(dt, collision1);
        heli2.update(dt, collision2);
        heli3.update(dt, collision3);
        heli4.update(dt, collision4);
        menuButton.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, Oving1.WIDTH, Oving1.HEIGHT);
        sb.draw(heli1.getTexture(), heli1.getPosition().x, heli1.getPosition().y, heli1.getWidth(), heli1.getHeight());
        sb.draw(heli2.getTexture(), heli2.getPosition().x, heli2.getPosition().y, heli2.getWidth(), heli2.getHeight());
        sb.draw(heli3.getTexture(), heli3.getPosition().x, heli3.getPosition().y, heli3.getWidth(), heli3.getHeight());
        sb.draw(heli4.getTexture(), heli4.getPosition().x, heli4.getPosition().y, heli4.getWidth(), heli4.getHeight());
        sb.draw(menuButton.getTexture(), menuButton.getPosition().x, menuButton.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
