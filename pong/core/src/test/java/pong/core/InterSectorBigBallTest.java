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
}
