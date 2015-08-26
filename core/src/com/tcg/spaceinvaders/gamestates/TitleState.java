package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.MyConstants.States;
import com.tcg.spaceinvaders.entities.Star;
import com.tcg.spaceinvaders.managers.GameStateManager;
import com.tcg.spaceinvaders.managers.MyInput;

public class TitleState extends GameState {
	
	private MyCamera cam;

	private Viewport view;
	
	private String title, enter;
	
	private float gX, gY, gH, eX, eY, eH;
	
	private Array<Star> stars;
	
	public TitleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		setValues();
		
		stars = new Array<Star>();
		
		for(int i = 0; i < 75; i++) {
			stars.add(new Star(MyConstants.WOLRD_WIDTH));
		}

	}
	
	private void setValues() {
		
		title = Game.TITLE;
		
		gH = Game.res.getHeight("main", title, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		
		gX = 10f;
		
		gY = (MyConstants.WORLD_HEIGHT * .55f) + (gH * .5f);
		
		enter = "Press Enter to Play";
		
		eH = Game.res.getHeight("main", enter, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		
		eX = 10f;
		
		eY = gY - (eH * 3f) - 5;
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.START)) {
			gsm.setState(States.PLAY);
		}
		if(MyInput.keyPressed(MyInput.BACK)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		setValues();

	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		for(Star s : stars) {
			s.draw(sr);
		}
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("main").draw(sb, title, gX, gY, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
		Game.res.getFont("main").draw(sb, enter, eX, eY, MyConstants.WOLRD_WIDTH - 20, Align.center, true);
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
