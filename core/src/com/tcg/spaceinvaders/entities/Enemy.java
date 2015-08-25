package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.spaceinvaders.MyConstants;

public class Enemy extends Entity {

	private Texture texDown;
	private Texture texUp;
	
	public static final float width = 12, height = 8;
	
	private boolean up;
	
	private int pointWorth;
	
	public Enemy(int row, int col) {
		super();
		String path = "entities/enemies/";
		if(row < 2) {
			texDown = new Texture(path + "bottom0.png");
			texUp = new Texture(path + "bottom1.png");
			pointWorth = 10;
		} else if(row < 4) {
			texDown = new Texture(path + "center0.png");
			texUp = new Texture(path + "center1.png");
			pointWorth = 20;
		} else {
			texDown = new Texture(path + "top0.png");
			texUp = new Texture(path + "top1.png");
			pointWorth = 30;
		}
		bounds.set((MyConstants.GAME_WIDTH * .075f) + (col * (width * 1.5f)) + ((width - texDown.getWidth()) * .5f), (MyConstants.WORLD_HEIGHT * .6f) + (row * (height * 2.5f)), texDown.getWidth(), texDown.getHeight());
		vel.set(2.5f, 0);
	}

	public void update(boolean move, boolean up) {
		this.up = up;
		if(move) {
			bounds.x += vel.x;
		}
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		if(this.up) {
			sb.draw(texUp, getX(), getY());
		} else {
			sb.draw(texDown, getX(), getY());
		}
	}
	
	public void bounceVelocity() {
		vel.x *= -1;
		bounds.x += vel.x;
		bounds.y -= 2;
	}

	@Override
	public void dispose() {
		texDown.dispose();
		texUp.dispose();
	}

	public int getPointWorth() {
		return pointWorth;
	}

}
