package com.tdt4240.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tdt4240.game.Game;

/**
 * View while searching for an opponenent.
 */

/*
 Searching (and finding) an opponent is almost instant, so this is unused. Leaving
 it here for future reference.
public class SearchOpponent extends Base {

    private Stage stage;

    private OrthographicCamera camera;

    SearchOpponent(Game game) {
        super(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private SpriteBatch batch = new SpriteBatch();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        getGame().getFont().draw(batch, "Searching opponent", 100, 150);
        batch.end();

    }

}*/
