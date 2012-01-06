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
		Vec2 ballVel = new Vec2(-1f,0);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		
		assertEquals(expectedCollision, interSector.getCollision(ballPos, ballVel, c));
		
	}
	
	@Test
	public void testGetCollisionDiagonal() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,2f);
		Vec2 ballVel = new Vec2(-1f,-1f);
		
		Vec2 expectedCollision = new Vec2(0f,c);
		
		assertEquals(expectedCollision, interSector.getCollision(ballPos, ballVel, c));
		
	}
	

}
