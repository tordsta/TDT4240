package com.tdt4240.game.controller;

import com.tdt4240.game.model.UsernameObject;

public class UsernamePresenter {
    public void setUsername(String username){
        //handle model
        //TODO implement firebase

        UsernameObject.INSTANCE.setUsername(username);

    }

    public String getUsername() {
        //handle model
        //TODO implement firebase

        String username = UsernameObject.INSTANCE.getUsername();

        return username;
    }
}
