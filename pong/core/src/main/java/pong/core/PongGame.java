package pong.core;

import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.common.Vec2;

import playn.core.Game;
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
public class PongGame implements Game, IPongGame {

    private static final float MAXBATSPEED = 12;
    private static final int DELTA = 6;
    private final float INITIAL_BALL_SPEED = 10f;
    private final static int WINNING_SCORE = 2;    
    private final Paintable world;
    private final Bat playerBat;
    private final Bat botBat;  
    private final Ball ball;
    private final int BAT_MARGIN = 1;
    private final DealWithAiBot aiBot;
    private final Arbiter arbiter;
    private Vec2 ballSpeedOrig, batSpeedOrig, botBatSpeedOrig;
    private boolean ballLoaded;
    private GameState state = GameState.BeforeStart;
    private final MessageBoard messageBoard;
    
    public PongGame(DealWithAiBot aiBot,
            Arbiter arbiter,
            Bat botBat, 
            Bat playerBat,
            Ball ball,
            Paintable pongWorld,
            MessageBoard messageBoard) {
        this.arbiter = arbiter;
        this.aiBot = aiBot;
        this.botBat = botBat;
        this.playerBat = playerBat;
        this.ball = ball;
        this.world = pongWorld;
        this.messageBoard = messageBoard;
    }

    private void startGame() {
        Random r = new Random();
        float alfa = (float) (r.nextFloat() * Math.PI / 2);
        float vx = (float) (INITIAL_BALL_SPEED * Math.cos(Math.PI / 4 + alfa));
        float vy = (float) (INITIAL_BALL_SPEED * Math.sin(Math.PI / 4 + alfa));

        ball.setLinearVelocity(vx, vy);
        setMessage("                  ");
        state = GameState.Running;
    }

    private void reset() {
        arbiter.resetScoreBoards();
        ball.setPos(PongWorld.WIDTH / 2, PongWorld.HEIGHT / 2);
        setMessage("Press space to begin");
        state = GameState.BeforeStart;
    }

    @Override
    public void autoServe() {
        boolean ended = false;
        try {
            ended = arbiter.checkForGameEnding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ball.setPos(PongWorld.WIDTH / 2, PongWorld.HEIGHT / 2);
        resetBatPos();
        if (ended) {
            reset();
        } else {
            startGame();
        }
    }

    @Override
    public void init() {
        System.out.println("Hello");
        try {
            // hook up our pointer listener
            pointer().setListener(new Pointer.Adapter() {

                public void onPointerStart(Pointer.Event event) {
                    if (state == GameState.BeforeStart) {
                        startGame();
                    } else if (state == GameState.GameOver) {
                        reset();
                    }
                    float x = event.x();
                    Vec2 oldPos = playerBat.getBody().getPosition();
                    Vec2 newPos = new Vec2(Math.max(0, x), oldPos.y);
                    playerBat.setPos(newPos.x * PongWorld.physUnitPerScreenUnit, newPos.y);
                }

                @Override
                public void onPointerDrag(Pointer.Event event) {
                    float x = event.x();
                    Vec2 oldPos = playerBat.getBody().getPosition();
                    Vec2 newPos = new Vec2(Math.max(0, x), oldPos.y);
                    playerBat.setPos(newPos.x * PongWorld.physUnitPerScreenUnit, newPos.y);

                }
            });

            PlayN.keyboard().setListener(new Keyboard.Adapter() {

                @Override
                public void onKeyDown(Keyboard.Event event) {
                    switch (event.key()) {
                        case SPACE:
                            if (state == GameState.BeforeStart) {
                                startGame();
                            } else if (state == GameState.GameOver) {
                                reset();
                                startGame();
                            }
                            break;
                        case LEFT:
                            increaseSpeed(false, playerBat);
                            break;
                        case RIGHT:
                            increaseSpeed(true, playerBat);
                            break;
                        case X:
                            playerBat.getBody().setLinearVelocity(new Vec2(0, 0));
                            break;
                        case Q:
                            quit();
                            break;
                        case PAUSE:
                        case P:
                            pauseGame();
                            break;
                    }

                }

                @Override
                public void onKeyUp(Keyboard.Event event) {
                }
            });
            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increaseSpeed(boolean right, Bat bat) {
        Vec2 oldSpeed = bat.getBody().getLinearVelocity();
        bat.getBody().setLinearVelocity(getIncreasedSpeed(right, oldSpeed));
    }

    public Vec2 getIncreasedSpeed(boolean right, Vec2 oldSpeed) {
        float vx = oldSpeed.x;
        float newVx = vx + (right ? DELTA : -DELTA);
        if (newVx < 0) {
            newVx = Math.max(-PongGame.MAXBATSPEED, newVx);
        } else {
            newVx = Math.min(PongGame.MAXBATSPEED, newVx);
        }
        return new Vec2(newVx, oldSpeed.y);
    }

    @Override
    public void pauseGame() {

        stopMovingParts();
        if (state == GameState.Paused) {
            setMessage("Paused");
        } else {
            setMessage("       ");
        }
    }

    @Override
    public void stopMovingParts() {
        if (state == GameState.GameOver || state == GameState.BeforeStart) {
            return;
        }
        if (state != GameState.Paused) {
            batSpeedOrig = new Vec2(playerBat.getBody().getLinearVelocity());
            System.out.println("Before speeed");
            ballSpeedOrig = new Vec2(ball.getBody().getLinearVelocity());
            botBatSpeedOrig = new Vec2(botBat.getBody().getLinearVelocity());

            playerBat.setLinearVelocity(0, 0);
            ball.setLinearVelocity(0, 0);
            botBat.setLinearVelocity(0, 0);
            System.out.println("gamePaused true: " + ballSpeedOrig);
            //world.messageBoard.setMessage("Paused");
            state = GameState.Paused;
        } else {
            playerBat.setLinearVelocity(batSpeedOrig.x, batSpeedOrig.y);
            ball.setLinearVelocity(ballSpeedOrig.x, ballSpeedOrig.y);
            botBat.setLinearVelocity(botBatSpeedOrig.x, botBatSpeedOrig.y);
            System.out.println("gamePaused false: " + ballSpeedOrig);
            //world.messageBoard.setMessage("        ");
            state = GameState.Running;  // not always...
        }
    }

    @Override
    public void resetBatPos() {
        playerBat.setPos(PongWorld.WIDTH / 2, PongWorld.HEIGHT - BAT_MARGIN);
        botBat.setPos(PongWorld.WIDTH / 2, BAT_MARGIN);
    }

    @Override
    public void setGameState(GameState state) {
        this.state = state;
    }

    public int getBatMargin() {
        return this.BAT_MARGIN;
    }

    @Override
    public void paint(float alpha) {
            world.paint(alpha);
    }

    @Override
    public void update(float delta) {
            world.update(delta);
    }

    @Override
    public int updateRate() {
        return 25;
    }

    protected void quit() {
    }

    @Override
    public void gameOver() {
        setMessage("Game over Insert coin");
        stopMovingParts();
        state = GameState.GameOver;
    }

    @Override
    public void increaseBotScore() {
        arbiter.increaseBotScore();
    }

    @Override
    public void increasePlayerScore() {
        arbiter.increasePlayerScore();
    }

    @Override
    public void resetScoreBoards() {
        arbiter.resetScoreBoards();
    }

    @Override
    public void setMessage(String message) {
        messageBoard.setMessage(message);
    }
}
