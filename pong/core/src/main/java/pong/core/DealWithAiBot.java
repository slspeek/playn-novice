/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import pong.entities.Ball;
import pong.entities.Bat;

/**
 * @author jan
 */
public class DealWithAiBot {

    final private int BAT_MARGIN;
    final private InterSector interSector;
    private Ball ball;
    private Bat botBat;

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setBotBat(Bat botBat) {
        this.botBat = botBat;
    }

    public DealWithAiBot(int BAT_MARGIN, InterSector interSector) {
        this.BAT_MARGIN = BAT_MARGIN;
        this.interSector = interSector;
    }

    public void calcAiBot() {
        if (botBat == null || ball == null) {
            throw new IllegalStateException("First set ball and botbat in DealWithAiBot");
        }
        try {
            Random random = new Random();
            int i = random.nextInt(6);
            if (i == 1) {
                System.out.println("Skipping AI");
                return;
            }
            //InterSector ai = new InterSector(pongWorld.WIDTH, pongWorld.HEIGHT, ball.getRadiusInUnits());
            final Body body = ball.getBody();
            final Vec2 position = body.getPosition();
            final Vec2 linearVelocity = body.getLinearVelocity();
            Collision coll = interSector.getPrediction(position, linearVelocity, BAT_MARGIN);
            botBat.setPos(coll.getPosition().x, botBat.getBody().getPosition().y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
