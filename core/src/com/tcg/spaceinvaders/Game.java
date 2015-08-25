package com.tcg.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.tcg.spaceinvaders.MyConstants.States;
import com.tcg.spaceinvaders.managers.*;

public class Game extends ApplicationAdapter {
	
	private static final String TITLE = "Space Invaders";

	public static Vector2 SIZE, CENTER;
	
	private GameStateManager gsm;
	
	private int fps, frames;
	
	private float fpstime;
	
	public static Content res;

	final static Save defaultSave = new Save(0);
	
	private static Save save;
	
	public static int SCORE, HIGHSCORE;
	
	public static final States defaultState = States.GAMEOVER;
 	
	@Override
	public void create () {
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		Game.SIZE = new Vector2();
		Game.CENTER = new Vector2();
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
		
		fpstime = 0;
		fps = 0;
		frames = 0;
		
		res = new Content();
		
		res.loadBitmapFont("font", "prstartk.ttf", "main", 12, Color.WHITE);
		
		res.loadSound("sound", "explosion.mp3", "explosion");
		res.loadSound("sound", "fastinvader1.mp3", "invader0");
		res.loadSound("sound", "fastinvader2.mp3", "invader1");
		res.loadSound("sound", "fastinvader3.mp3", "invader2");
		res.loadSound("sound", "fastinvader4.mp3", "invader3");
		res.loadSound("sound", "shoot.mp3", "shoot");
		res.loadSound("sound", "invaderkilled.wav", "endeath");
		res.loadSound("sound", "ufo.mp3", "ufo");
		
		SCORE = 0;
		
		Game.load();
		
		Game.HIGHSCORE = Game.save.getHighscore();
		
		gsm = new GameStateManager(Game.defaultState);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		Controllers.addListener(new MyControllerProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		gsm.handleInput();
		gsm.update(dt);
		gsm.draw(dt);
		
		fpstime += dt;
		frames++;
		if(fpstime >= 1) {
			fps = frames;
			frames = 0;
			fpstime = 0;
		}
		Gdx.graphics.setTitle(Game.TITLE + " | " + fps + " fps");
	
		MyInput.update();
	}

	@Override
	public void resize(int width, int height) {
		Game.SIZE.set(width, height);
		Game.CENTER.set(width * .5f, height * .5f);
		gsm.resize(width, height);
	}

	@Override
	public void dispose() {
		gsm.dispose();
		Game.save();
	}
	
	public static void load() {
		if (Gdx.files.local(MyConstants.saveFile).exists()) {
			try {
				save = Save.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			save = defaultSave;
			Game.save();
		}
	}
	
	public static void save() {
		save.setHighscore(Game.HIGHSCORE);
		try {
			save.save(save);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
