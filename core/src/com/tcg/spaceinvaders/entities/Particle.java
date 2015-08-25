package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.spaceinvaders.MyConstants;

public class Particle {

	private float time, timer;
	
	private boolean shouldRemove;
	
	private Texture tex;
	
	private float x, y;
	
	public Particle(Entity e) {
		time = 0;
		timer = .5f;
		String path = "entities/enemies/explode.png";
		tex = new Texture(path);
		x = e.getCenter().x - (tex.getWidth() * .5f);
		y = e.getCenter().y - (tex.getHeight() * .5f);
	}
	
	public void render(float dt, SpriteBatch sb) {
		
		time += dt;
		shouldRemove = time > timer;
		
		sb.setColor(MyConstants.randomColor());
		sb.draw(tex, x, y);
		sb.setColor(Color.WHITE);
	}
	
	public void dispose() {
		tex.dispose();
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

}
