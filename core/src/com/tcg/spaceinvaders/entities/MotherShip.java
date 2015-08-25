package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.spaceinvaders.Game;
import com.tcg.spaceinvaders.MyConstants;

public class MotherShip extends Entity {

	final float speed = 2.02982f;
	
	private Texture tex;
	
	private int pointWorth;
	
	public MotherShip() {
		super();
		tex = new Texture("entities/enemies/mothership.png");
		if(MathUtils.randomBoolean()) {
			bounds.set(0, MyConstants.WORLD_HEIGHT * .9f, tex.getWidth(), tex.getHeight());
			vel.set(speed, 0);
		} else {
			bounds.set(MyConstants.GAME_WIDTH, MyConstants.WORLD_HEIGHT * .9f, tex.getWidth(), tex.getHeight());
			vel.set(-speed, 0);
		}
		if(MathUtils.randomBoolean()) {
			pointWorth = 50;
		} else if(MathUtils.randomBoolean()) {
			pointWorth = 100;
		} else if(MathUtils.randomBoolean()) {
			pointWorth = 150;
		} else {
			pointWorth = 300;
		}
		Game.res.getSound("ufo").play();
	}

	public void update() {
		bounds.x += vel.x;
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.draw(tex, getX(), getY());
	}

	@Override
	public void dispose() {
		tex.dispose();
	}

	public int getPointWorth() {
		return pointWorth;
	}

}
