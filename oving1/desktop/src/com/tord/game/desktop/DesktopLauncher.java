package com.tord.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tord.game.Oving1;


public class DesktopLauncher {
	public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Oving1.WIDTH;
        config.height = Oving1.HEIGHT;
        config.title = Oving1.TITLE;
        new LwjglApplication(new Oving1(), config);
    }
}
