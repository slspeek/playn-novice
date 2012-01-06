package pong.core;

import org.jbox2d.common.Vec2;

public class InterSector {
	Vec2 getCollision(Vec2 balPos, Vec2 balVel, float c) {
		float x = balPos.x +((c - balPos.y / balVel.y)* balVel.x);
		Vec2 returnValue = new Vec2(x,c);
		return returnValue;
	}
}
