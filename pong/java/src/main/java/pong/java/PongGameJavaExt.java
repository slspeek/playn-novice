package pong.java;

import pong.core.*;
import pong.entities.Ball;
import pong.entities.Bat;

public class PongGameJavaExt extends PongGame {

    public PongGameJavaExt(DealWithAiBot aiBot, Arbiter arbiter, Bat botBat, Bat playerBat, Ball ball, Paintable pongWorld, MessageBoard messageBoard) {
        super(aiBot, arbiter, botBat, playerBat, ball, pongWorld, messageBoard);
    }

    protected void quit() {
        System.exit(0);
    }
}
