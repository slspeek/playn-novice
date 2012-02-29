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
    
	public boolean skip = false; 
 //	private float deltaMiss = 0;	// 
	private PongGame    game;
	private PongWorld   pongWorld;
        private Ball        ball;
        private Bat         botBat;
        public int          BAT_MARGIN;
        public int          skipAiAtScores[] = {1, -1};
	// private int botBatHits = world.botScoreBoard.getScore();

	public DealWithAiBot(Ball ball, PongWorld pongWorld, Bat botBat)
	{
	    this.game = pongWorld.getGame();
            this.pongWorld = pongWorld;
            this.ball = ball;
            this.botBat = botBat;
	    BAT_MARGIN = game.getBatMargin();
            skipAiAtScores[0] = this.calcBotBatMiss();
            skipAiAtScores[1] = this.calcBotBatMiss();
            while (skipAiAtScores[0] == skipAiAtScores[1])
            {
                skipAiAtScores[1] = this.calcBotBatMiss();
            }    
	}

	public void calcAiBot()
	{
            InterSector ai = new InterSector(pongWorld.WIDTH, pongWorld.HEIGHT);
            final Body body = ball.getBody();
            final Vec2 position = body.getPosition();
            final Vec2 linearVelocity = body.getLinearVelocity();
            if (linearVelocity.y < 0 && position.y > BAT_MARGIN && position.y < pongWorld.HEIGHT - 4 * BAT_MARGIN) 
            {
                Collision coll = ai.getCollision(position, linearVelocity, BAT_MARGIN);
                botBat.setPos(coll.getPosition().x, botBat.getBody().getPosition().y);
            }
        }


	// integer calc when to skip  
    	private int calcBotBatMiss() 
	{
            Random generator = new Random();
            return generator.nextInt(game.WINNING_SCORE);
	}
 
}
