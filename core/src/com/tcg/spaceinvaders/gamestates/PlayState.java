package com.tcg.spaceinvaders.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.tcg.spaceinvaders.*;
import com.tcg.spaceinvaders.MyConstants.States;
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
	
	private HUD hud;
	
	private Array<Particle> particles;
	
	private Array<Star> stars;
	
	private Array<Texture> troub;
	
	private float troubTime, troubTimer;
	
	private int troubFrame, pTroubFrame;
	
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
		
		particles = new Array<Particle>();
		
		shields.clear();
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 3) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) - (Shield.totalWidth * 5) - (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 2) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
		shields.add(new Shield((MyConstants.GAME_WIDTH * .5f) + (Shield.totalWidth * 4) + (Shield.totalWidth * .5f), MyConstants.WORLD_HEIGHT * .2f - (Shield.totalHeight * .5f)));
	
		Game.SCORE = 0;
		
		reset();
		
		sound = 0;
		
		bullets = new Array<Bullet>();
		
		troubTime = 0;
		troubTimer = .5f;
		
		
		troub = new Array<Texture>();
		
		String path = "devguy/", png = ".png";
		
		for(int i = 0; i < 10; i++) {
			troub.add(new Texture(path + i + png));
		}
		
		troubFrame = MathUtils.random(troub.size - 1);
		
		pTroubFrame = troubFrame;
		
		hud = new HUD();
		
		stars = new Array<Star>();
		
		for(int i = 0; i < 75; i++) {
			stars.add(new Star(MyConstants.GAME_WIDTH));
		}
		
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
		if(MyInput.keyPressed(MyInput.BACK)) {
			gsm.setState(States.TITLE);
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
			boolean shouldBreak = false;
			for(Bullet b : bullets) {
				if(ship.collidingWith(b)) {
					bullets.removeValue(b, true);
					Game.SCORE += ship.getPointWorth();
					Game.res.getSound("explosion").play();
					particles.add(new Particle(ship));
					ship.dispose();
					motherShip.removeValue(ship, true);
					shouldBreak = true;
					motherShipTime = 0;
					motherShipTimer *= 1.25f;
					continue;
				}
			}
			if(shouldBreak) break;
			if(ship.getX() < 0 || ship.getX() > MyConstants.GAME_WIDTH) {
				motherShip.removeValue(ship, true);
				motherShipTime = 0;
				motherShipTimer *= 1.25f;
			}
		}
		
		for(Bullet b : bullets) {
			b.update();
			if(b.collidingWith(p) && b.getType() == Type.HOSTILE) {
				Game.res.getSound("explosion").play();
				gsm.setState(States.GAMEOVER);
			}
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
			if(e.getY() <= MyConstants.WORLD_HEIGHT * .2f + (Shield.totalHeight * .5f)) {
				gsm.setState(States.GAMEOVER);
				break;
			}
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
					Game.SCORE += e.getPointWorth();
					particles.add(new Particle(e));
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
		
		if(Game.SCORE > Game.HIGHSCORE) {
			Game.HIGHSCORE = Game.SCORE;
		}
		
		troubTime += dt;
		if(troubTime >= troubTimer) {
			troubFrame = MathUtils.random(troub.size - 1);
			while(troubFrame == pTroubFrame) {
				troubFrame = MathUtils.random(troub.size - 1);
			}
			troubTime = 0;
		}
		
		pTroubFrame = troubFrame;
	}

	@Override
	public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
		
		
		sr.begin(ShapeType.Filled);
		for(Star s : stars) {
			s.draw(sr);
		}
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
		for(Particle pa : particles) {
			pa.render(dt, sb);
			if(pa.isShouldRemove()) {
				pa.dispose();
				particles.removeValue(pa, true);
			}
		}
		sb.draw(troub.get(troubFrame), MyConstants.GAME_WIDTH, 100);
		sb.end();
		
		if(debug) debug(dt, sb, sr);
		
		hud.render(sb, cam);

	}
	
	private void debug(float dt, SpriteBatch sb, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(MyConstants.GAME_WIDTH, 0, MyConstants.WOLRD_WIDTH - MyConstants.GAME_WIDTH, MyConstants.WORLD_HEIGHT);
		sr.setColor(Color.RED);
		sr.rect(MyConstants.WOLRD_WIDTH, 0, -5, MyConstants.WORLD_HEIGHT);
		sr.setColor(Color.WHITE);
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
		for(MotherShip ship : motherShip) {
			ship.dispose();
		}
		for(Particle pa : particles) {
			pa.dispose();
		}
		Game.save();
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
