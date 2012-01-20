/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

import org.jbox2d.common.Vec2;
import org.junit.*;
import static org.junit.Assert.*;
import pong.entities.Bat;

/**
 *
 * @author youssef
 */
public class PongGameTest {

    public PongGameTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of increaseSpeed method, of class PongGame.
     */
    @Test
    public void testGetIncreasedSpeed() {
        PongGame instance = new PongGame();
        
        Vec2 oldSpeed = new Vec2(1,0);
        Vec2 newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(oldSpeed.x + PongGame.DELTA, newSpeed.x, 0.0001f );
        
        oldSpeed = newSpeed;
        newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(PongGame.MAXBATSPEED, newSpeed.x, 0.0001f );
        
        oldSpeed = newSpeed;
        newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(PongGame.MAXBATSPEED, newSpeed.x, 0.0001f );
        System.out.println("" + newSpeed);
    }
    
     @Test
    public void testGetIncreasedSpeed2() {
        PongGame instance = new PongGame();
        
        Vec2 oldSpeed = new Vec2(-1,0);
        Vec2 newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(oldSpeed.x + PongGame.DELTA, newSpeed.x, 0.0001f );
        
        oldSpeed = newSpeed;
        newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(PongGame.MAXBATSPEED - 1, newSpeed.x, 0.0001f );
        
        oldSpeed = newSpeed;
        newSpeed = instance.getIncreasedSpeed(true, oldSpeed);
        Assert.assertEquals(PongGame.MAXBATSPEED, newSpeed.x, 0.0001f );
        System.out.println("" + newSpeed);
}
}
