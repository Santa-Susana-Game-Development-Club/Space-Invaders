package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.spaceinvaders.MyConstants;
import com.tcg.spaceinvaders.managers.MyInput;

public class Player extends Entity {

	private Texture tex;
	
	public Player() {
		super();
		tex = new Texture("entities/player.png");
		setDimensions(tex.getWidth(), tex.getHeight());
		setPosition((MyConstants.WOLRD_WIDTH * .5f) - (getWidth() * .5f), MyConstants.WORLD_HEIGHT * .10f - (getHeight() * .5f));
	}
	
	public void handleInput() {
		if(MyInput.keyDown(MyInput.LEFT)) {
			vel.x = -2.5f;
		} else if(MyInput.keyDown(MyInput.RIGHT)) {
			vel.x = 2.5f;
		} else {
			vel.x = 0;
		}
	}
	
	public void update() {
		setDimensions(tex.getWidth(), tex.getHeight());
		
		bounds.x += vel.x;
		bounds.x = MyConstants.clamp(bounds.x, 0, MyConstants.GAME_WIDTH - getWidth());
		
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
