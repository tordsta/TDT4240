package com.tdt4240.game.controller;

import android.util.Log;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.tdt4240.game.ecs.component.BoundsComponent;
import com.tdt4240.game.ecs.component.ButtonType;

import com.tdt4240.game.ecs.component.PlayerState;
import com.tdt4240.game.ecs.system.ControlPanelSystem;
import com.tdt4240.game.ecs.system.WeaponPanelSystem;
import com.tdt4240.game.ecs.system.util.Utils;


/**
 * InputProcessor system that handles input related to moving a player.
 */
// TODO: rename to PlayerControlInputHandler?
public class UserControlInputHandler extends InputAdapter {

    private PooledEngine engine;

    private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private ComponentMapper<ButtonType> buttonTypeMapper = ComponentMapper.getFor(ButtonType.class);
    private ComponentMapper<PlayerState> playerStateMapper = ComponentMapper.getFor(PlayerState.class);

    private WeaponPanelSystem weaponPanelSystem;
    private ControlPanelSystem controlPanelSystem;

    public UserControlInputHandler(PooledEngine engine) {
        this.engine = engine;

        weaponPanelSystem = engine.getSystem(WeaponPanelSystem.class);
        controlPanelSystem = engine.getSystem(ControlPanelSystem.class);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.all(ButtonType.class).get());
        Entity player = Utils.getActivePlayer(engine);

        // Redefines screenY so that it goes from bottom to top
        screenY = Gdx.graphics.getHeight() - screenY;


        if (buttons.size() == 0 || player == null) {
            Log.i("UserControlInputHandler", "returning early from touchDown");
            return false;
        }
        for (Entity entity : buttons) {
            if (clickedWithinBounds(entity, screenX, screenY)) {
                Log.i("UserControlInputHandler", "clicked button");

                final ButtonType buttonType = buttonTypeMapper.get(entity);

                PlayerState playerState = playerStateMapper.get(player);

                Log.i("UserControlInputHandler", buttonType.getType().toString());

                switch (buttonType.getType()) {
                    case WEAPON_NAVIGATION:
                        weaponPanelSystem.processArrowButton(entity);
                        break;
                    case WEAPON_SELECT:
                        playerState.setType(PlayerState.STATE.SHOOT);
                        weaponPanelSystem.processWeapon(entity);
                        break;
                    case SHOW_WEAPONS:
                        playerState.setType(PlayerState.STATE.SELECT_WEAPON);
                        weaponPanelSystem.togglePanel();
                        break;
                    case PLAYER_NAVIGATION:
                        playerState.setType(PlayerState.STATE.MOVE);
                        controlPanelSystem.processButton(entity);
                        break;
                    case START_SHOOT:
                        playerState.setType(PlayerState.STATE.SHOOT);
                        break;
                }
                return false;
            }

        }
        return false;
    }

    /**
     * Returning either true or false based on if the click was inside the bounds of the entity (the player).
     *
     * @param entity Player
     * @param screenX Integer
     * @param screenY Integer
     * @return Boolean
     */
    private boolean clickedWithinBounds(Entity entity, int screenX, int screenY) {
        final BoundsComponent bounds = boundsMapper.get(entity);

        return bounds.getBounds().contains(screenX, screenY);
    }

}