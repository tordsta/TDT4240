package com.tdt4240.game.controller;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.game.WorldSettings;
import com.tdt4240.game.ecs.component.BoundsComponent;
import com.tdt4240.game.ecs.component.PhysicsBody;
import com.tdt4240.game.ecs.component.PlayerState;
import com.tdt4240.game.ecs.component.Position;
import com.tdt4240.game.ecs.component.UserControllable;

import com.tdt4240.game.ecs.component.WeaponType;
import com.tdt4240.game.ecs.system.BulletSystem;
import com.tdt4240.game.ecs.system.util.Utils;

/**
 * InputProcessor system that handles input related to shooting a bullet.
 *
 */
public class ShootInputHandler extends InputAdapter {

    private PooledEngine engine;
    private BulletSystem bulletSystem;

    private ComponentMapper<UserControllable> userControllableMapper = ComponentMapper.getFor(UserControllable.class);
    private ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private ComponentMapper<PlayerState> playerStateMapper = ComponentMapper.getFor(PlayerState.class);
    private ComponentMapper<WeaponType> weaponTypeMapper = ComponentMapper.getFor(WeaponType.class);
    private ComponentMapper<PhysicsBody> bodyMapper = ComponentMapper.getFor(PhysicsBody.class);

    // Sat when a touchDown is registered
    private int touchDownX;
    private int touchDownY;

    private boolean shooting = false;

    public ShootInputHandler(PooledEngine engine) {
        this.engine = engine;
        bulletSystem = engine.getSystem(BulletSystem.class);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ImmutableArray<Entity> players = engine.getEntitiesFor(Family.all(UserControllable.class).get());

        // Redefines screenY so that it goes from bottom to top
        screenY = Gdx.graphics.getHeight() - screenY;

        // Finds the player it's turn it is, gets it component and checks if th click was on the player
        // TODO: use method for finding player whos turn it is
        for (Entity player : players) {
            UserControllable userControllable = userControllableMapper.get(player);
            PlayerState.STATE state = playerStateMapper.get(player).getType();
            if (userControllable.getEnable() && state == PlayerState.STATE.SHOOT) {
                BoundsComponent bounds = boundsMapper.get(player);
                Position position = positionMapper.get(player);

                if (bounds.getBounds().contains(screenX, screenY)) {
                    // Set center as start position
                    // TODO
                    touchDownX = screenX;
                    touchDownY = screenY;

                    shooting = true;


                    // Create the bullet and have it float midair
                    WeaponType weaponType = weaponTypeMapper.get(player);
                    bulletSystem.createBullet((int)(position.getPosition().x * WorldSettings.PIXELS_PER_METER), touchDownY, weaponType);
                }
            } else {
                System.out.println("Either not player or not in shoot state");
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {


        if (shooting) {
            // Redefines screenY so that it goes from bottom to top
            screenY = Gdx.graphics.getHeight() - screenY;

            // Calculate the velocity based on where the touch down and touch up was
            float velocityX = touchDownX - screenX;
            float velocityY = touchDownY - screenY;
            
            bulletSystem.shoot(velocityX, velocityY);

            shooting = false;

            Entity currentPlayer = Utils.getActivePlayer(engine);

            PlayerState playerState = playerStateMapper.get(currentPlayer);
            playerState.setType(PlayerState.STATE.WAIT);
        }

        return false;
    }
}