package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.spaceinvaders.Game;
import com.tcg.spaceinvaders.MyCamera;
import com.tcg.spaceinvaders.MyConstants;
import com.tcg.spaceinvaders.MyConstants.States;
import com.tcg.spaceinvaders.entities.Star;
import com.tcg.spaceinvaders.managers.GameStateManager;
import com.tcg.spaceinvaders.managers.MyInput;

public class SplashState extends GameState {

	private MyCamera cam;

	private Viewport view;
	
	private Texture tex;
	
	private String text;
	
	private float textX, textY, textW, texX, texY, texW, texH;
	
	private Array<Star> stars;
	
	private float time, timer;
	
	public SplashState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);

		tex = new Texture("splash.png");
		
		setValues();
		
		stars = new Array<Star>();
		
		for(int i = 0; i < 75; i++) {
			stars.add(new Star(MyConstants.WOLRD_WIDTH));
		}
		
		timer = 5;
		time = 0;
		
	}
	
	private void setValues() {
		
		text = "Santa Susana Game Development Club Presents";
		
		texH = tex.getHeight();
		texW = tex.getWidth();
		
		texX = (MyConstants.WOLRD_WIDTH * .5f) - (texW * .5f);
		texY = (MyConstants.WORLD_HEIGHT * .5f) - (texH * .5f);
		
		textW = MyConstants.WOLRD_WIDTH - 20;
		
		textX = 10;
		textY = texY - 12;
		
	}

	@Override
	public void handleInput() {
		
		if(MyInput.keyPressed(MyInput.START)) {
			gsm.setState(States.TITLE);
		}
		
	}

	@Override
	public void update(float dt) {
		
		setValues();
		
		time += dt;
		if(time >= timer) {
			gsm.setState(States.TITLE);
		}
		
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
		sb.draw(tex, texX, texY);
		Game.res.getFont("main").draw(sb, text, textX, textY, textW, Align.center, true);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		tex.dispose();
	}

}
