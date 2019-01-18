package com.tord.game.sprits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;
import com.tord.game.states.GameStateManager;
import com.tord.game.states.MenuState;
import com.tord.game.states.Task1State;

public class Button {
    private GameStateManager gsm;
    private TextureRegion texture;
    private Vector3 position;
    private OrthographicCamera cam;
    private int state;

    public Button(GameStateManager gsm, int stateNumber, OrthographicCamera cam, int x, int y){
        this.gsm = gsm;
        this.cam = cam;
        this.state = stateNumber;
        texture = new TextureRegion(new Texture("playButton.png"), 90, 35, 75, 75 );
        position = new Vector3(x, y, 0);
        //switch case til state
        //stateToSwitchTo

    }

    private void setState(int stateNr){
        switch (stateNr){
            case 0:
                gsm.set(new MenuState(gsm));
                break;
            case 1:
                gsm.set(new Task1State(gsm));
                break;
        }
    }

    public void update(float dt){
        //if this is clicked sett state to this.gamestateforthisbutton
        //this can be found from position
        if(Gdx.input.justTouched()) {
            if(Gdx.input.getX() > position.x && Gdx.input.getX() < position.x + texture.getRegionWidth() &&
               Gdx.input.getY() > (Oving1.HEIGHT - texture.getRegionHeight() - position.y) && Gdx.input.getY() < (Oving1.HEIGHT - position.y + texture.getRegionHeight())
               ){
                setState(state);
            }
        }
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Vector3 getPosition() {
        return position;
    }

}
