/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

import java.util.Random;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import pong.core.InterSector;
import pong.core.PongGame;
import pong.core.PongWorld;
import pong.entities.Ball;
import pong.entities.Bat;

/**
 * @author jan
 */
public class DealWithAiBot {

    private PongGame game;
    private PongWorld pongWorld;
    private Ball ball;
    private Bat botBat;
    public int BAT_MARGIN;
    public boolean mayRun = false;

    public DealWithAiBot(Ball ball, PongWorld pongWorld, Bat botBat) {
        this.game = pongWorld.getGame();
        this.pongWorld = pongWorld;
        this.ball = ball;
        this.botBat = botBat;
        BAT_MARGIN = game.getBatMargin();

    }

    public void calcAiBot() {
//        Random random = new Random();
//        int i = random.nextInt(6);
//        if (i == 1) {
//            return;
//        }
        if (!mayRun) {
            return;
        }
        InterSector ai = new InterSector(pongWorld.WIDTH, pongWorld.HEIGHT);
        final Body body = ball.getBody();
        final Vec2 position = body.getPosition();
        final Vec2 linearVelocity = body.getLinearVelocity();
        if (linearVelocity.y < 0 && position.y > BAT_MARGIN && position.y < pongWorld.HEIGHT - 4 * BAT_MARGIN) {
            Collision coll = ai.getPredictionForBallMovingUp(position, linearVelocity, BAT_MARGIN);
            botBat.setPos(coll.getPosition().x, botBat.getBody().getPosition().y);
            mayRun = false;
        }
    }
}
