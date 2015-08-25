package com.tcg.spaceinvaders.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.tcg.spaceinvaders.Game;
import com.tcg.spaceinvaders.MyCamera;
import com.tcg.spaceinvaders.MyConstants;

public class HUD {

	private String scoreT, highScoreTa, highScoreTb, score, highScore, shoot;

	private float hstaX, hstaY, hstaH;
	
	private float hstbX, hstbY, hstbH;
	
	private float stX, stY, stH;
	
	private float sX, sY, sH;
	
	private float hsX, hsY;
	
	private float shootX, shootY;
	
	public HUD() {
		setValues();
	}
	
	private void setValues() {
		scoreT = "Score";
		score = MyConstants.getScore(0);
		
		stH = Game.res.getHeight("main", scoreT, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		stX = MyConstants.GAME_WIDTH + 5;
		stY = MyConstants.WORLD_HEIGHT * .85f + (stH * .5f);
		
		sH = Game.res.getHeight("main", score, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		sX = MyConstants.GAME_WIDTH + 5;
		sY = stY - stH - 7;
		
		highScoreTa = "High";
		highScoreTb = "Score";
		highScore = MyConstants.getScore(0);

		hstaH = Game.res.getHeight("main", highScoreTa, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		
		hstaX = MyConstants.GAME_WIDTH + 5;
		hstaY = sY - sH - 12;
		
		hstbH = Game.res.getHeight("main", highScoreTb, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);

		hstbX = MyConstants.GAME_WIDTH + 8;
		hstbY = hstaY - hstaH - 2;
		
		hsX = MyConstants.GAME_WIDTH + 5;
		hsY = hstbY - hstbH - 7; 
		
		shoot = "Press Space to Shoot!";
		
		shootX = 10;
		shootY = MyConstants.WORLD_HEIGHT - 12;
				
	}
	
	public void render(SpriteBatch sb, MyCamera cam) {
		setValues();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("main").draw(sb, scoreT, stX, stY, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		Game.res.getFont("main").draw(sb, score, sX, sY, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		Game.res.getFont("main").draw(sb, highScoreTa, hstaX, hstaY, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		Game.res.getFont("main").draw(sb, highScoreTb, hstbX, hstbY, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		Game.res.getFont("main").draw(sb, highScore, hsX, hsY, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH - 10, Align.left, false);
		Game.res.getFont("main").draw(sb, shoot, shootX, shootY, MyConstants.GAME_WIDTH, Align.center, true);
		sb.end();
	}

}
