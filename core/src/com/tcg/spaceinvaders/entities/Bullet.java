package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bullet extends Entity {

	public enum Type {
		FRIENDLY, HOSTILE
	}
	
	Type type;
	
	public Bullet(Type type, Entity e) {
		super();
		this.type = type;
		if(type == Type.FRIENDLY) {
			bounds.set(e.getCenter().x, e.getTop() + 1, 1, 10);
			vel.set(0, 5f);
		} else {
			bounds.set(e.getCenter().x, e.getY() - 11, 1, 10);
			vel.set(0, -5f);
		}
	}

	public void update() {
		bounds.x += vel.x;
		bounds.y += vel.y;
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		if(type == Type.FRIENDLY) {
			sr.setColor(Color.RED);
		} else {
			sr.setColor(Color.GREEN);
		}
		sr.rect(getX(), getY(), getWidth(), getHeight());
		sr.setColor(Color.WHITE);
	}

	@Override
	public void dispose() {}
	
	public Type getType() {
		return this.type;
	}

}
