/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.joints.LineJointDef;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import pong.entities.Ceiling;
import pong.entities.Ground;
import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import pong.entities.Ball;
import pong.entities.Bat;

/**
 *
 * @author steven
 */
public class PongGameFactory {

    Ground ground;
    Ceiling ceiling;
    PongWorld pongWorld;
    MessageBoard messageBoard;
    ScoreBoard playerScoreBoard, botScoreBoard;
    Bat botBat, playerBat;
    Ball ball;
    DealWithAiBot aiBot;
    InterSector interSector;
    Arbiter arbiter;
    private final int WIDTH = 40;
    private final int HEIGHT = 30;
    private final int BAT_MARGIN = 1;
    private final float BALL_RADIUS = 1f;
    private int WINNING_SCORE = 3;

    public PongGame get() {
        // load and show our background image
        Image bgImage = assetManager().getImage("images/Horizontal_Line.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        graphics().rootLayer().add(bgLayer);

        // create our world layer (scaled to "world space")
        GroupLayer worldLayer = graphics().createGroupLayer();
        worldLayer.setScale(1f / PongWorld.physUnitPerScreenUnit);
        graphics().rootLayer().add(worldLayer);

        pongWorld = new PongWorld(worldLayer, 640, 480);

        ground = new Ground(pongWorld, pongWorld.getWorld(), WIDTH / 2, HEIGHT, 0);
        pongWorld.add(ground);

        ceiling = new Ceiling(pongWorld, pongWorld.getWorld(), WIDTH / 2, 0f, 0f);
        pongWorld.add(ceiling);

        Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 64);
        messageBoard = new MessageBoard(font, new Vec2(14, 7), 15f, 20f, 0xFFCCCCCC);
        pongWorld.dynamicLayer.add(messageBoard.getLayer());


        font = graphics().createFont("Helvetica", Font.Style.PLAIN, 36);
        playerScoreBoard = new ScoreBoard(font, new Vec2(17, 2), 2f, 3f, 0xFFCCCCCC);
        botScoreBoard = new ScoreBoard(font, new Vec2(21, 2), 2f, 3f, 0xFFCCCCCC);
        pongWorld.dynamicLayer.add(playerScoreBoard.getLayer());
        pongWorld.dynamicLayer.add(botScoreBoard.getLayer());
        messageBoard.setMessage("Press space to begin");

        interSector = new InterSector(WIDTH, HEIGHT, BALL_RADIUS);
        aiBot = new DealWithAiBot(BAT_MARGIN, interSector);



        playerBat = new Bat(pongWorld, pongWorld.getWorld(), WIDTH / 2, HEIGHT - BAT_MARGIN, 0, aiBot, true);
        pongWorld.add(playerBat);

        botBat = new Bat(pongWorld, pongWorld.getWorld(), WIDTH / 2, BAT_MARGIN, 0, aiBot, false);
        pongWorld.add(botBat);

        ball = new Ball(pongWorld, pongWorld.getWorld(), WIDTH / 2, HEIGHT / 2, 0, BALL_RADIUS);
        pongWorld.add(ball);

        aiBot.setBall(ball);
        aiBot.setBotBat(botBat);
        
        //Player bat fixated
        LineJointDef jd = new LineJointDef();
        // Bouncy limit
        Vec2 axis = new Vec2(1.0f, 0.0f);
        axis.normalize();
        jd.initialize(ground.getBody(), playerBat.getBody(), new Vec2(0.0f,
                8.5f), axis);

        jd.motorSpeed = 0.0f;
        jd.maxMotorForce = 100.0f;
        jd.enableMotor = true;
        jd.lowerTranslation = -4.0f;
        jd.upperTranslation = 4.0f;
        pongWorld.getWorld().createJoint(jd);

        //Bot bat fixated
        jd = new LineJointDef();
        // Bouncy limit
        axis = new Vec2(1.0f, 0.0f);
        axis.normalize();
        jd.initialize(ceiling.getBody(), botBat.getBody(), new Vec2(0.0f,
                8.5f), axis);

        jd.motorSpeed = 0.0f;
        jd.maxMotorForce = 100.0f;
        jd.enableMotor = true;
        jd.lowerTranslation = -4.0f;
        jd.upperTranslation = 4.0f;
        pongWorld.getWorld().createJoint(jd);

        arbiter = new Arbiter(playerScoreBoard, botScoreBoard, WINNING_SCORE);
        PongGame game = new PongGame(aiBot, arbiter, botBat, playerBat, ball, pongWorld, messageBoard);
        
        
        arbiter.setGame(game);
        ground.setGame(game);
        ceiling.setGame(game);

        
        // create the walls
        Body wallLeft = pongWorld.getWorld().createBody(new BodyDef());
        PolygonShape wallLeftShape = new PolygonShape();
        wallLeftShape.setAsEdge(new Vec2(0, 0), new Vec2(0, HEIGHT));
        wallLeft.createFixture(wallLeftShape, 0.0f);
        Body wallRight = pongWorld.getWorld()
                .createBody(new BodyDef());
        PolygonShape wallRightShape = new PolygonShape();
        wallRightShape.setAsEdge(new Vec2(WIDTH, 0), new Vec2(WIDTH, HEIGHT));
        wallRight.createFixture(wallRightShape, 0.0f);
        System.out.println("End of init");
        return game;
    }

}

