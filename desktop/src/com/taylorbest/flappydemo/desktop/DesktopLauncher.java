package com.taylorbest.flappydemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.taylorbest.flappydemo.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Game.HEIGHT;
		config.width = Game.WIDTH;
		config.title = Game.TITLE;

		new LwjglApplication(new Game(), config);
	}
}
