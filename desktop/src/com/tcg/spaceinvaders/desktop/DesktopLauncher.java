package com.tcg.spaceinvaders.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.spaceinvaders.Game;
import com.tcg.spaceinvaders.MyConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MyConstants.WOLRD_WIDTH;
		config.height = MyConstants.WORLD_HEIGHT;
		config.addIcon("icons/win.png", FileType.Internal);
		config.addIcon("icons/lin.png", FileType.Internal);
		config.addIcon("icons/mac.png", FileType.Internal);
		new LwjglApplication(new Game(), config);
	}
}
