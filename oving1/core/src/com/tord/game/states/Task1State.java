package com.tord.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tord.game.Oving1;
import com.tord.game.sprits.Button;
import com.tord.game.sprits.Heli;

public class Task1State extends State {
    private Heli heli;
    private Texture background;
    private Button menuButton;


    public Task1State(GameStateManager gsm) {
        super(gsm);
        heli = new Heli(50,50, 132, 54);
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
        heli.update(dt);
        menuButton.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, Oving1.WIDTH, Oving1.HEIGHT);
        sb.draw(heli.getSprite(), heli.getPosition().x, heli.getPosition().y, heli.getWidth(), heli.getHeight());
        sb.draw(menuButton.getTexture(), menuButton.getPosition().x, menuButton.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
