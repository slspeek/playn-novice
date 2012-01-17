package pong.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.LineJoint;
import org.jbox2d.dynamics.joints.LineJointDef;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;
import pong.entities.Ball;
import pong.entities.Bat;

/**
 * PlayN Entry Point
 *
 * @author youssef
 *
 */
public class PongGame implements Game {

    int DELTA = 9;
    ImageLayer bgLayer;
    float INITIAL_BALL_SPEED = 10f;
    // main layer that holds the world. note: this gets scaled to world space
    GroupLayer worldLayer;
    // main world
    PongWorld world = null;
    boolean worldLoaded = false;
    Bat bat;
    Bat botBat;
    Ball ball;
    LineJoint joint;
    protected boolean ballLoaded;
    int score = 100;
    private int BAT_MARGIN = 1;

    private void startBall() {
        if (!ballLoaded && worldLoaded) {
            ball = new Ball(world, world.world,
                    PongWorld.WIDTH / 2, PongWorld.HEIGHT / 2, 0);

            Random r = new Random();
            float alfa = (float) (r.nextFloat() * Math.PI / 2);
            float vx = (float) (INITIAL_BALL_SPEED * Math.cos(Math.PI / 4 + alfa));
            float vy = (float) (INITIAL_BALL_SPEED * Math.sin(Math.PI / 4 + alfa));

            ball.setLinearVelocity(vx, vy);
            world.add(ball);
            ballLoaded = true;
            world.messageBoard.setMessage("                  ");
        }
    }

    @Override
    public void init() {
        System.out.println("Hello");
        try {
            // load and show our background image
            Image bgImage = assetManager().getImage("images/Black.png");
            bgLayer = graphics().createImageLayer(bgImage);
            graphics().rootLayer().add(bgLayer);

            // create our world layer (scaled to "world space")
            worldLayer = graphics().createGroupLayer();
            worldLayer.setScale(1f / PongWorld.physUnitPerScreenUnit);
            graphics().rootLayer().add(worldLayer);

            world = new PongWorld(worldLayer);
            worldLoaded = true;

            bat = new Bat(world, world.world, PongWorld.WIDTH / 2,
                    PongWorld.HEIGHT - BAT_MARGIN, 0);
            world.add(bat);

            botBat = new Bat(world, world.world, PongWorld.WIDTH / 2,
                    BAT_MARGIN, 0);
            world.add(botBat);
            // hook up our pointer listener
            pointer().setListener(new Pointer.Adapter() {

                public void onPointerStart(Pointer.Event event) {
                    startBall();

                }

                @Override
                public void onPointerDrag(Pointer.Event event) {
//startBall();
                    float x = event.x();
                    Vec2 oldPos = bat.getBody().getPosition();
                    Vec2 newPos = new Vec2(Math.max(0, x), oldPos.y);
                    bat.setPos(newPos.x * PongWorld.physUnitPerScreenUnit, newPos.y);

                }
            });

            PlayN.keyboard().setListener(new Keyboard.Adapter() {

                @Override
                public void onKeyDown(Keyboard.Event event) {
                    switch (event.key()) {
                        case SPACE:
                            startBall();
                            break;
                        case LEFT:
                            increaseSpeed(false, bat);
                            break;
                        case RIGHT:
                            increaseSpeed(true, bat);
                            break;
                        case X:
                            bat.getBody().setLinearVelocity(new Vec2(0, 0));
                            break;
                        case Q:
                            quit();
                            break;

                    }

                }

                private void increaseSpeed(boolean right, Bat bat) {
                    Vec2 oldSpeed = bat.getBody().getLinearVelocity();
                    float vx = oldSpeed.x;
                    float newVx = vx + (right ? DELTA : -DELTA);
                    bat.getBody().setLinearVelocity(new Vec2(newVx, oldSpeed.y));
                }

                @Override
                public void onKeyUp(Keyboard.Event event) {
                }
            });

            initJoint();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //startBall();
    }

    private void initJoint() {
        LineJointDef jd = new LineJointDef();

        // Bouncy limit
        Vec2 axis = new Vec2(1.0f, 0.0f);
        axis.normalize();
        jd.initialize(world.ground.getBody(), bat.getBody(), new Vec2(0.0f,
                8.5f), axis);

        jd.motorSpeed = 0.0f;
        jd.maxMotorForce = 100.0f;
        jd.enableMotor = true;
        jd.lowerTranslation = -4.0f;
        jd.upperTranslation = 4.0f;
        // jd.enableLimit = true;
        joint = (LineJoint) world.world.createJoint(jd);

    }

    @Override
    public void paint(float alpha) {
        if (worldLoaded) {
            world.paint(alpha);
        }
    }

    @Override
    public void update(float delta) {
        if (worldLoaded) {
            world.update(delta);

            dealWithAIBotBat();
        }
    }

    @Override
    public int updateRate() {
        return 25;
    }

    protected void quit() {
    }

    private void dealWithAIBotBat() {
        if (ball != null) {
            InterSector ai = new InterSector(PongWorld.WIDTH, PongWorld.HEIGHT);
            final Body body = ball.getBody();
            final Vec2 position = body.getPosition();
            final Vec2 linearVelocity = body.getLinearVelocity();
            if (linearVelocity.y < 0 && position.y > BAT_MARGIN && position.y < PongWorld.HEIGHT - 4 * BAT_MARGIN) {
                Collision coll = ai.getCollision(position, linearVelocity, BAT_MARGIN);
                botBat.setPos(coll.getPosition().x, botBat.getBody().getPosition().y);
            }
        }
    }
}
