package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.tcg.spaceinvaders.MyConstants;

public class Shield {

	public static final int totalWidth = 22;
	public static final int totalHeight = 16;
	
	private float startX, startY;
	
	private Array<Rectangle> bounds;
	
	public Shield(float x, float y) {
		this.startX = x;
		this.startY = y;
		bounds = new Array<Rectangle>();
		for(int row = 0; row < Shield.totalHeight; row++) { 
			for(int col = 0; col < Shield.totalWidth; col++) {
				if((row < 2) && (col > 4 && col < 16)) continue;
				if((row == 2) && (col > 5 && col < 15)) continue;
				if((row == 3) && (col > 6 && col < 14)) continue;
				if((row == 12) && ((col == 0) || (col == 21))) continue;
				if((row == 13) && ((col < 2) || (col > 19))) continue;
				if((row == 14) && ((col < 3) || (col > 18))) continue;
				if((row == 15) && ((col < 4) || (col > 17))) continue;
				bounds.add(new Rectangle(col + x, row + y, 1, 1));
			}
		}
	}
	
	public void update(Array<Bullet> bullets) {
		for(Rectangle r : bounds) {
			for(Bullet b : bullets) {
				if(r.overlaps(b.getBounds())) {
					bounds.removeValue(r, true);
					bullets.removeValue(b, true);
					continue;
				}
			}
		}
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(MyConstants.rgba(0, 255, 0, 255));
		for(Rectangle r : bounds) {
			sr.rect(r.x, r.y, r.width, r.height);
		}
		sr.setColor(Color.WHITE);
	}
	
	public void debug(ShapeRenderer sr) {
		sr.rect(startX, startY, totalWidth, totalHeight);
	}

}
