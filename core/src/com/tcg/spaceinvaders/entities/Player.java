package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.spaceinvaders.Game;
import com.tcg.spaceinvaders.MyConstants;

public class Player extends Entity {

	private Texture tex;
	
	public Player() {
		super();
		tex = new Texture("entities/player.png");
		setDimensions(tex.getWidth(), tex.getHeight());
		setPosition((MyConstants.WOLRD_WIDTH * .5f) - (getWidth() * .5f), MyConstants.WORLD_HEIGHT * .10f - (getHeight() * .5f));
	}
	
	public void handleInput() {
		
	}
	
	public void update() {
		
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.draw(tex, getX(), getY());
	}

	@Override
	public void dispose() {
		tex.dispose();
	}

}
