package com.tord.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tord.game.Oving1;
import com.tord.game.sprits.Button;
import com.tord.game.sprits.HeliControlled;

public class Task3State extends State {
    private HeliControlled heli;
    private Texture background;
    private Button menuButton;
    BitmapFont bitmapFont;

    public Task3State(GameStateManager gsm) {
        super(gsm);
        heli = new HeliControlled(50,50, 256, 109);
        //cam.setToOrtho(false, Oving1.WIDTH*2, Oving1.HEIGHT*2);
        background = new Texture("background.jpg");
        menuButton = new Button(
                gsm,
                0,
                cam,
                (Oving1.WIDTH / 8 - (75/2)),
                (Oving1.HEIGHT - 120)
        );
        bitmapFont = new BitmapFont();
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
        sb.draw(heli.getTexture(), heli.getPosition().x, heli.getPosition().y, heli.getWidth(), heli.getHeight());
        sb.draw(menuButton.getTexture(), menuButton.getPosition().x, menuButton.getPosition().y);
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bitmapFont.draw(sb, heli.getPosition().toString(), 10, Oving1.HEIGHT - 20);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
