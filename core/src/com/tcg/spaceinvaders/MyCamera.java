package com.tcg.spaceinvaders;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

public class MyCamera extends OrthographicCamera {

	private Viewport view;
	
	public MyCamera(boolean center) {
		super();
		view = new StretchViewport(MyConstants.WOLRD_WIDTH, MyConstants.WORLD_HEIGHT, this);
		view.apply();
		view.update((int) Game.SIZE.x, (int) Game.SIZE.y, center);
	}
	
	public boolean inView(float x, float y) {
		float leftX, rightX, bottom, top;
		leftX = this.position.x - (this.viewportWidth * .5f);
		rightX = this.position.x + (this.viewportWidth * .5f);
		top = this.position.y + (this.viewportHeight * .5f);
		bottom = this.position.y - (this.viewportHeight * .5f);
		return (((x > leftX) && (x < rightX)) && ((y < top) && (y > bottom)));
	}
	
	public boolean inView(Vector2 point) {
		return inView(point.x, point.y);
	}

	public void resize(int width, int height, boolean center) {
		view.update(height, width, center);
	}
	
	public float getLeft() {
		return this.position.x - (this.viewportWidth * .5f);
	}
	
	public float getRight() {
		return this.position.x + (this.viewportWidth * .5f);
	}
	
	public float getBottom() {
		return this.position.y - (this.viewportHeight * .5f);
	}
	
	public float getTop() {
		return this.position.y + (this.viewportHeight * .5f);
	}

}
