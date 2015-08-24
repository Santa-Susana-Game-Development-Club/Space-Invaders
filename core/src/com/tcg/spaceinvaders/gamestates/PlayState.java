package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.spaceinvaders.MyCamera;
import com.tcg.spaceinvaders.entities.*;
import com.tcg.spaceinvaders.managers.GameStateManager;

public class PlayState extends GameState {

	private MyCamera cam;
	
	private Player p;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		p = new Player();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		p.draw(sr, sb, dt);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height, true);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
