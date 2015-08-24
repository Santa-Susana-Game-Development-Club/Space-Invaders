package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.*;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.entities.*;
import com.tcg.spaceinvaders.managers.GameStateManager;

public class PlayState extends GameState {

	final boolean debug = true;
	
	private MyCamera cam;

	private Viewport view;
	
	private Player p;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		p = new Player();
	}

	@Override
	public void handleInput() {
		p.handleInput();

	}

	@Override
	public void update(float dt) {
		p.update();

	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		
		if(debug) debug(dt, sb, sr);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		p.draw(sr, sb, dt);
		sb.end();

	}
	
	private void debug(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(0, 0, MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT);
		sr.setColor(Color.BLACK);
		sr.rect(0, 0, MyConstants.GAME_WIDTH, MyConstants.WORLD_HEIGHT);
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
