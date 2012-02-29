package pong.core;
import static junit.framework.Assert.*;

import org.jbox2d.common.Vec2;
import org.junit.Test;

public class InterSectorTest {

	InterSector interSector = new InterSector(6,4);
	@Test
	public void testGetCollisionHorizontalVerySimple() {
		float c = 1;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(0,-1f);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		Collision col = interSector.getCollisionOnHorizontal(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1f, col.getTime());
	}
	
	@Test
	public void testGetCollisionHorizontalDiagonal() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,2f);
		Vec2 ballVel = new Vec2(-1f,-1f);
		
		Vec2 expectedCollision = new Vec2(0f,c);
		Collision col = interSector.getCollisionOnHorizontal(ballPos, ballVel, c);
		
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(2f, col.getTime());
	}
	
	@Test
	public void testGetCollisionHorizontalHarder() {
		float c = 0;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(2f,-1f);
		
		Vec2 expectedCollision = new Vec2(5f,0);
		
		Collision col = interSector.getCollisionOnHorizontal(ballPos, ballVel, c);
		assertEquals(expectedCollision, interSector.getCollisionOnHorizontal(ballPos, ballVel, c).getPosition());
		assertEquals(2f, col.getTime());
	}

	@Test
	public void testGetCollisionVerticalVerySimple() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,2f);
		Vec2 ballVel = new Vec2(-2f,-1f);
		
		Vec2 expectedCollision = new Vec2(c,1f);
		Collision col = interSector.getCollisionOnVertical(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1f, col.getTime());
	}

	// JT: latest
	// Collision with direction of ball to player side
	// Velocity y is positive...
	
	public void testGetCollisionVerticalVerySimpleInverse() {
		float HEIGHT = 29f;
		float c = HEIGHT;
		Vec2 ballPos = new Vec2(1f,28f);
		Vec2 ballVel = new Vec2(0f, 1f);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		Collision col = interSector.getCollisionOnVertical(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1f, col.getTime());
	}
	
	@Test
	public void testGetCollisionVerticalDiagonal() {
		float c = 6;
		Vec2 ballPos = new Vec2(4f,5f);
		Vec2 ballVel = new Vec2(1f,-1f);
		
		Vec2 expectedCollision = new Vec2(c, 3);
		Collision col = interSector.getCollisionOnVertical(ballPos, ballVel, c);
		
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(2f, col.getTime());
		
	}
	
	@Test
	public void testGetCollisionSimpleCase1() {
		float c = 1;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(0,-1f);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1f, col.getTime());
	}
	
	@Test
	public void testGetCollisionSimpleCase2() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,2f);
		Vec2 ballVel = new Vec2(-1f,-1f);
		
		Vec2 expectedCollision = new Vec2(0f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(2f, col.getTime());
		
	}
	
	@Test
	public void testGetCollisionSimpleCase3() {
		float c = 0;
		Vec2 ballPos = new Vec2(1f,2f);
		Vec2 ballVel = new Vec2(2f,-1f);
		
		Vec2 expectedCollision = new Vec2(5f,0);
		
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		assertEquals(expectedCollision, interSector.getCollisionOnHorizontal(ballPos, ballVel, c).getPosition());
		assertEquals(2f, col.getTime());
	}
	
	@Test
	public void testGetCollisionOverLeftWall() {
		float c = 0;
		Vec2 ballPos = new Vec2(2f,3f);
		Vec2 ballVel = new Vec2(-1f,-1f);
		
		Vec2 expectedCollision = new Vec2(1f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(3f, col.getTime());
	}
	
	@Test
	public void testGetCollisionOverRightWall() {
		float c = 0;
		Vec2 ballPos = new Vec2(5f,3f);
		Vec2 ballVel = new Vec2(1f,-2f);
		
		Vec2 expectedCollision = new Vec2(5.5f,c);
		Collision col = interSector.getCollision(ballPos, ballVel, c);
		
		assertEquals(expectedCollision, col.getPosition());
		assertEquals(1.5f, col.getTime());
	}
}
