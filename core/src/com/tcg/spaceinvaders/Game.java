package com.tcg.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {
	
	public static Vector2 SIZE, CENTER;
 	
	@Override
	public void create () {
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		Game.SIZE = new Vector2();
		Game.CENTER = new Vector2();
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
	}

	@Override
	public void dispose() {
		// TODO
	}
}
