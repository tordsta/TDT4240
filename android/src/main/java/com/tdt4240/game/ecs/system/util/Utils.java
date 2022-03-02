package com.tdt4240.game.ecs.system.util;

import android.util.Log;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.tdt4240.game.ecs.component.PlayerID;
import com.tdt4240.game.ecs.component.PlayerState;
import com.tdt4240.game.ecs.component.TypeComponent;
import com.tdt4240.game.ecs.component.UserControllable;
import com.tdt4240.game.ecs.system.Network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Different utils methods.
 */
public class Utils {

    static private ComponentMapper<UserControllable> userControllableMapper = ComponentMapper.getFor(UserControllable.class);

    /**
     * Returning the player who's turn it is.
     *
     * @param engine PooledEngine
     * @return Player
     */
    public static Entity getActivePlayer(Engine engine) {

        ImmutableArray<Entity> players = engine.getEntitiesFor(Family.all(UserControllable.class).get());

        for (Entity player : players) {
            UserControllable userControllable = userControllableMapper.get(player);

            if (userControllable.getEnable()) {
                return player;
            }
        }

        return null;

    }

    public static void setActivePlayer(Engine engine, String playerID) {
        ImmutableArray<Entity> players = engine.getEntitiesFor(Family.all(UserControllable.class).get());

        for (Entity player : players) {
            userControllableMapper
                    .get(player)
                    .setEnable(
                            player
                                    .getComponent(PlayerID.class)
                                    .getId()
                                    .equals(playerID)
                    );

        }


    }

    public static void setPlayerState(@NotNull PooledEngine engine, @Nullable String playerID, @NotNull PlayerState.STATE state) {
        Entity playerEntity = getPlayerById(engine, playerID);

        if (playerEntity != null) {
            playerEntity.getComponent(PlayerState.class).setType(state);
        } else {
            Log.w("Utils", "Player " + playerID + " is not ready");
        }

    }

    private static Entity getPlayerById(PooledEngine engine, String playerID) {
        ImmutableArray<Entity> players = engine.getEntitiesFor(Family.all(UserControllable.class).get());

        for (Entity player : players) {
            if (player.getComponent(PlayerID.class).getId().equals(playerID)) {
                return player;
            }
        }

        return null;
    }

    public static PlayerState.STATE getLocalPlayerState(PooledEngine engine) {
        String playerID = engine.getSystem(Network.class).localPlayerID();
        return Utils.getPlayerState(engine, playerID);
    }

    private static PlayerState.STATE getPlayerState(PooledEngine engine, String playerID) {
        Entity playerEntity = getPlayerById(engine, playerID);
        if (playerEntity != null) {
            return playerEntity.getComponent(PlayerState.class).getType();
        }else{
            return null;
        }
    }
}
