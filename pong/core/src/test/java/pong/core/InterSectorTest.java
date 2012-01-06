package pong.core;
import static junit.framework.Assert.*;

import org.jbox2d.common.Vec2;
import org.junit.Test;

public class InterSectorTest {

	InterSector interSector = new InterSector();
	@Test
	public void testGetCollisionVerySimple() {
		float c = 1;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(0,-1f);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1f, col.getTime());
	}
	
	@Test
	public void testGetCollisionDiagonal() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,2f);
		Vec2 ballVel = new Vec2(-1f,-1f);
		
		Vec2 expectedCollision = new Vec2(0f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(2f, col.getTime());
		
	}
	
	@Test
	public void testGetCollisionHarder() {
		float c = 0;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(2f,-1f);
		
		Vec2 expectedCollision = new Vec2(5f,0);
		
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		assertEquals(expectedCollision, interSector.getCollision(ballPos, ballVel, c).getPosition());
		assertEquals(2f, col.getTime());
	}

}
