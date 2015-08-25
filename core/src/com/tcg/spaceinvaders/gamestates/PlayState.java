package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.entities.*;
import com.tcg.spaceinvaders.managers.GameStateManager;

public class PlayState extends GameState {

	final boolean debug = true;
	
	private MyCamera cam;

	private Viewport view;
	
	private Player p;
	
	private Array<Shield> shields;
	
	private Array<Enemy> enemies;
	
	private float moveTime, moveTimer;
	
	private boolean enemyUp;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(true);
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, cam);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, true);
		
		p = new Player();
		
		shields = new Array<Shield>();
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 3) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 5) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 2) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 4) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .15f - (Shield.totalHeight * .5f)));
	
		enemies = new Array<Enemy>();
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 11; col++) {
				enemies.add(new Enemy(row, col));
			}
		}
		
		moveTimer = .5f;
		moveTime = 0;
		
		enemyUp = false;
		
	}

	@Override
	public void handleInput() {
		p.handleInput();
	}

	@Override
	public void update(float dt) {
		p.update();
		for(Shield s : shields) {
			s.update();
		}
		
		moveTime+= dt;
		for(Enemy e : enemies) {
			e.update(moveTime >= moveTimer, enemyUp);
		}
		if(moveTime >= moveTimer) {
			enemyUp = !enemyUp;
			moveTime = 0;
		}
		for(Enemy e : enemies) {
			if(e.getRight() >= MyConstants.GAME_WIDTH || e.getX() <= 0) {
				for(Enemy e1 : enemies) {
					e1.bounceVelocity();
				}
				break;
			}
		}
	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		
		
		sr.begin(ShapeType.Filled);
		for(Shield s : shields) {
			s.draw(sr);
		}
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		for(Enemy e : enemies) {
			e.draw(sr, sb, dt);
		}
		p.draw(sr, sb, dt);
		sb.end();
		
		if(debug) debug(dt, sb, sr);

	}
	
	private void debug(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(MyConstants.GAME_WIDTH, 0, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH, MyConstants.WORLD_HEIGHT);
		sr.end();
		
		sr.begin(ShapeType.Line);
		for(Shield s : shields) {
			s.debug(sr);
		}
		p.debug(sr);
		for(Enemy e : enemies) {
			e.debug(sr);
		}
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height, true);
	}

	@Override
	public void dispose() {
		p.dispose();
		for(Enemy e : enemies) {
			e.dispose();
		}
	}

}
