package pong.core;

import static junit.framework.Assert.*;

import org.jbox2d.common.Vec2;
import org.junit.Test;

public class InterSectorBigBallTest {

    InterSector interSector = new InterSector(6, 4, .5f);

    @Test
    public void testGetCollision() {

        Vec2 ballPos = new Vec2(4f, .5f);
        Vec2 ballVel = new Vec2(1f, 1f);

        Vec2 expectedCollision = new Vec2(1f, .5f);
        Collision col = interSector.getPrediction(ballPos, ballVel, 0);
        assertEquals(expectedCollision, col.getPosition());
    }
    
    @Test
    public void testGetCollision2() {

        interSector = new InterSector(4f, 3f, 0.5f);
        Vec2 ballPos = new Vec2(2f, 2.5f);
        Vec2 ballVel = new Vec2(1.5f, -.5f);

        Vec2 expectedCollision = new Vec2(2f, .5f);
        Collision col = interSector.getPrediction(ballPos, ballVel, 0);
        assertEquals(expectedCollision, col.getPosition());
    }
    
    
    
    @Test
    public void testGetCollision3() {

        interSector = new InterSector(4f, 3f, 0.5f);
        Vec2 ballPos = new Vec2(1f, 2.5f);
        Vec2 ballVel = new Vec2(1f, -2f);

        Vec2 expectedCollision = new Vec2(2f, .5f);
        Collision col = interSector.getPrediction(ballPos, ballVel, 0);
        assertEquals(expectedCollision, col.getPosition());
    }
    
    @Test(expected=IllegalStateException.class)
    public void testGetCollision4() {

        interSector = new InterSector(4f, 3f, 0.5f);
        Vec2 ballPos = new Vec2(0.5f, 1.5f);
        Vec2 ballVel = new Vec2(1f, 1f);

        Vec2 expectedCollision = new Vec2(3f, 1f);
        Collision col = interSector.getPrediction(ballPos, ballVel, 0);
        assertEquals(expectedCollision, col.getPosition());
    }
}
