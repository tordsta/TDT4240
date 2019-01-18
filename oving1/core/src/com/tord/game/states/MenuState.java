package com.tord.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tord.game.Oving1;
import com.tord.game.sprits.Button;

//jeg skal ha en meny som går til en ny state ved klikk på objekt

public class MenuState extends State {
    private Texture background;
    private Button task1Button;
    private Button task2Button;
    private Button task3Button;
    private Button task4Button;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.jpg");
        task1Button = new Button(
                gsm,
                1,
                cam,
                (Oving1.WIDTH / 2 - (75/2)),
                ((Oving1.HEIGHT / 2) + 150)
        );
        task2Button = new Button(
                gsm,
                2,
                cam,
                (Oving1.WIDTH / 2 - (75/2)),
                ((Oving1.HEIGHT / 2) + 50)
        );

    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
        task1Button.update(dt);
        task2Button.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Oving1.WIDTH, Oving1.HEIGHT);
        sb.draw(task1Button.getTexture(), task1Button.getPosition().x, task1Button.getPosition().y);
        sb.draw(task2Button.getTexture(), task2Button.getPosition().x, task2Button.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
