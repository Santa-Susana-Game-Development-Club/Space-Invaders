package com.tcg.spaceinvaders.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.spaceinvaders.MyConstants.States;
import com.tcg.spaceinvaders.gamestates.*;

public class GameStateManager {

	private GameState gameState;
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	public GameStateManager(States state) {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		setState(state);
	}
	
	public void setState(States state) {
		if(gameState != null) gameState.dispose();
		if(state == States.SPLASH) {
			gameState = new SplashState(this);
		}
		if(state == States.TITLE) {
			gameState = new TitleState(this);
		}
		if(state == States.PLAY) {
			gameState = new PlayState(this);
		}
		if(state == States.GAMEOVER) {
			gameState = new GameOverState(this);
		}
	}
	
	public void handleInput() {
		gameState.handleInput();
	}
	
	public void update(float dt) {
		gameState.update(dt);
	}
	
	public void draw(float dt) {
		gameState.draw(dt, sb, sr);
	}
	
	public void resize(int width, int height) {
		gameState.resize(width, height);
	}
	
	public void dispose() {
		gameState.dispose();
	}
	
}
