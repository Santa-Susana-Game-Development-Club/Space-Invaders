package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.entities.*;
import com.tcg.spaceinvaders.entities.Bullet.Type;
import com.tcg.spaceinvaders.managers.GameStateManager;
import com.tcg.spaceinvaders.managers.MyInput;

public class PlayState extends GameState {

	final boolean debug = false;
	
	private MyCamera cam;

	private Viewport view;
	
	private Player p;
	
	private Array<Shield> shields;
	
	private Array<Enemy> enemies;

	private Array<Bullet> bullets;
	
	private Array<MotherShip> motherShip;
	
	private float moveTime, moveTimer;
	
	private boolean enemyUp;
	
	private float motherShipTime, motherShipTimer;
	
	private int sound;
	
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
		
		enemies = new Array<Enemy>();
		motherShip  = new Array<MotherShip>();
		
		shields = new Array<Shield>();
		
		shields.clear();
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 3) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 5) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 2) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 4) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
	
		
		reset();
		
		sound = 0;
		
		bullets = new Array<Bullet>();
		
	}

	@Override
	public void handleInput() {
		p.handleInput();
		if(MyInput.keyDown(MyInput.SHOOT)) {
			boolean shoot = true;
			for(Bullet b : bullets) {
				if(b.getType() == Type.FRIENDLY) {
					shoot = false;
					break;
				}
			}
			if(shoot) {
				Game.res.getSound("shoot").play();
				bullets.add(new Bullet(Type.FRIENDLY, p));
			}
		}
	}

	@Override
	public void update(float dt) {
		p.update();
		motherShipTime += dt;
		if(motherShipTime > motherShipTimer) {
			motherShipTime = 0;
			motherShip.add(new MotherShip());
		}
		for(MotherShip ship : motherShip) {
			ship.update();
			if(ship.getX() < 0 || ship.getX() > MyConstants.GAME_WIDTH) {
				motherShip.removeValue(ship, true);
				motherShipTime = 0;
				motherShipTimer *= 1.25f;
			}
		}
		for(Bullet b : bullets) {
			b.update();
			if(b.getY() <= 0 || b.getTop() >= MyConstants.WORLD_HEIGHT) {
				bullets.removeValue(b, true);
				continue;
			}
		}
		for(Shield s : shields) {
			s.update(bullets);
		}
		
		moveTime+= dt;
		for(Enemy e : enemies) {
			e.update(moveTime >= moveTimer, enemyUp);
			float chance = (1 - ((float)enemies.size / (float) (5f * 13f))) / 500f;
			MyConstants.clamp(chance, 0f, 0.0010461538f);
			if(MathUtils.randomBoolean(chance)) {
				bullets.add(new Bullet(Type.HOSTILE, e));
			}
		}
		if(moveTime >= moveTimer) {
			enemyUp = !enemyUp;
			moveTime = 0;
			sound++;
			sound = (int)MyConstants.wrap(sound, 0, 3);
			Game.res.getSound("invader" + sound).play();
		}
		for(Enemy e : enemies) {
			for(Bullet b : bullets) {
				if(e.collidingWith(b) && b.getType() == Type.FRIENDLY) {
					bullets.removeValue(b, true);
					Game.res.getSound("endeath").play();
					e.dispose();
					enemies.removeValue(e, true);
					moveTimer *= .95f;
					break;
				}
			}
			if(e.getRight() >= MyConstants.GAME_WIDTH || e.getX() <= 0) {
				for(Enemy e1 : enemies) {
					e1.bounceVelocity();
				}
				break;
			}
		}
		
		if(enemies.size == 0) {
			reset();
		}
	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		
		
		sr.begin(ShapeType.Filled);
		for(Shield s : shields) {
			s.draw(sr);
		}
		for(Bullet b : bullets) {
			b.draw(sr, sb, dt);
		}
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		for(Enemy e : enemies) {
			e.draw(sr, sb, dt);
		}
		for(MotherShip ship : motherShip) {
			ship.draw(sr, sb, dt);
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
	
	private void reset() {
		enemies.clear();
		motherShip.clear();
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 13; col++) {
				enemies.add(new Enemy(row, col));
			}
		}

		
		moveTimer = .75f;
		moveTime = 0;
		
		enemyUp = false;
		
		motherShipTime = 0;
		motherShipTimer = 7.5f;
	}

}
