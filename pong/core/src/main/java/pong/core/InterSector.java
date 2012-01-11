package pong.core;

import org.jbox2d.common.Vec2;

/**
 * Calculates the intersection between the path the ball and a horizontal line
 * (y = C)
 * 
 */
public class InterSector {

	float WIDTH = 6;

        /**
         * Calculates the intersection of the path of ball and the horizontal
         * line y=c
         * @param pos The position of the ball
         * @param vel The velocity of the ball
         * @param c The horizontal line y=c
         * @return The time and place of intersection
         */
	public Collision getCollision(Vec2 pos, Vec2 vel, float c) {
		Collision firstGuess = getCollisionOnHorizontal(pos, vel, c);
		float xc = firstGuess.getPosition().x;
		if (xc < 0) {
			Collision secondGuess = getCollisionOnVertical(pos, vel, 0);
			float firstAmountOfTime = secondGuess.getTime();
			Vec2 newPosition = secondGuess.getPosition();
			Vec2 newSpeed = new Vec2(-vel.x, vel.y);
			Collision collision = getCollision(newPosition, newSpeed, c);
			return new Collision(collision.getPosition(), firstAmountOfTime
					+ collision.getTime());
		} else if (0 <= xc && xc < WIDTH) {
			return firstGuess;
		} else {
			Collision secondGuess = getCollisionOnVertical(pos, vel, WIDTH);
			float firstAmountOfTime = secondGuess.getTime();
			Vec2 newPosition = secondGuess.getPosition();
			Vec2 newSpeed = new Vec2(-vel.x, vel.y);
			Collision collision = getCollision(newPosition, newSpeed, c);
			return new Collision(collision.getPosition(), firstAmountOfTime
					+ collision.getTime());
		}
	}

	Collision getCollisionOnHorizontal(Vec2 ballPos, Vec2 ballVel, float c) {
		float x = ballPos.x + ((c - ballPos.y / ballVel.y) * ballVel.x);
		Vec2 returnValue = new Vec2(x, c);
		float time = (c - ballPos.y) / ballVel.y;
		Collision col = new Collision(returnValue, time);
		return col;
	}

	Collision getCollisionOnVertical(Vec2 ballPos, Vec2 ballVel, float c) {
		float y = ballPos.y + ((c - ballPos.x / ballVel.x) * ballVel.y);
		Vec2 returnValue = new Vec2(c, y);
		float time = (c - ballPos.x) / ballVel.x;
		Collision col = new Collision(returnValue, time);
		return col;
	}

}
