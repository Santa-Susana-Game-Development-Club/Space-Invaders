package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.managers.GameStateManager;
import com.tcg.spaceinvaders.managers.MyInput;

public class GameOverState extends GameState {
	
	private MyCamera cam;

	private Viewport view;
	
	private String gameover, score, exit;
	
	private float gX, gY, gH, sX, sY, sH, eX, eY, eH;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		setValues();

	}
	
	private void setValues() {
		
		gameover = "Game Over";
		
		gH = Game.res.getHeight("main", gameover, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		
		gX = 10f;
		
		gY = (MyConstants.WORLD_HEIGHT * .5f) + gH + gH + gH + 5;
		
		score = MyConstants.getScore(Game.SCORE);
		
		sH = Game.res.getHeight("main", score, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		
		sX = 10f;

		sY = gY - gH - sH - 5;
		
		exit = "Press Enter to Exit";
		
		eH = Game.res.getHeight("main", exit, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		
		eX = 10f;
		
		eY = sY - sH - eH - 5;
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.START)) {
			gsm.setState(Game.defaultState);
		}

	}

	@Override
	public void update(float dt) {
		setValues();

	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("main").draw(sb, gameover, gX, gY, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		Game.res.getFont("main").draw(sb, score, sX, sY, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		Game.res.getFont("main").draw(sb, exit, eX, eY, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
