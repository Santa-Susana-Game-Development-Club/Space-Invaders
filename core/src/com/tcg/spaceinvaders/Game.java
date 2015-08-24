package com.tcg.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.tcg.spaceinvaders.MyConstants.States;
import com.tcg.spaceinvaders.managers.*;

public class Game extends ApplicationAdapter {
	
	private static final String TITLE = "Space Invaders";

	public static Vector2 SIZE, CENTER;
	
	private GameStateManager gsm;
	
	private int fps, frames;
	
	private float fpstime;
 	
	@Override
	public void create () {
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		Game.SIZE = new Vector2();
		Game.CENTER = new Vector2();
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
		
		fpstime = 0;
		fps = 0;
		frames = 0;
		
		gsm = new GameStateManager(States.PLAY); //TODO Change PLAY to SPLASH when SplashState is created
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		Controllers.addListener(new MyControllerProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		gsm.handleInput();
		gsm.update(dt);
		gsm.draw(dt);
		
		fpstime += dt;
		frames++;
		if(fpstime >= 1) {
			fps = frames;
			frames = 0;
			fpstime = 0;
		}
		Gdx.graphics.setTitle(Game.TITLE + " | " + fps + " fps");
	
		MyInput.update();
	}

	@Override
	public void resize(int width, int height) {
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
		gsm.resize(width, height);
	}

	@Override
	public void dispose() {
		gsm.dispose();
	}
}
