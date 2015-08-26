package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.spaceinvaders.MyConstants;

public class Star {

	private Vector2 pos;
	
	public Star(float width) {
		pos = new Vector2(MathUtils.random(width), MathUtils.random(MyConstants.WORLD_HEIGHT));
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(MyConstants.randomColor());
		sr.rect(pos.x, pos.y, 1, 1);
		sr.setColor(Color.WHITE);
	}

}
