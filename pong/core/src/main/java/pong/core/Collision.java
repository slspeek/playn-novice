package pong.core;

import org.jbox2d.common.Vec2;

/**
 * Holds the time and position at the collision point.
 * 
 * @author youssef
 * 
 */
public class Collision {

	private final Vec2 position;
	private final float time;

	public Collision(Vec2 position, float time) {
		super();
		this.position = position;
		this.time = time;
	}

	public Vec2 getPosition() {
		return position;
	}

	public float getTime() {
		return time;
	}
}
