package pong.core;

import org.jbox2d.common.Vec2;

/**
 * Calculates the intersection between the path the ball and a horizontal line
 * (y = C)
 *
 */
public class InterSector {

    private final float width;
    private final float height;
    private final float ballRadius;

    public InterSector(float width, float height, float ballRadius) {
        this.width = width - 2 * ballRadius;
        this.height = height - 2 * ballRadius;
        this.ballRadius = ballRadius;
    }

    public Collision translateOut(Collision coll) {
        return new Collision(new Vec2(coll.getPosition().x + ballRadius, coll.getPosition().y + ballRadius), coll.getTime());
    }

    public Vec2 translateIn(Vec2 pos) {
        return new Vec2(pos.x - ballRadius, pos.y - ballRadius);
    }

    public Collision getPrediction(Vec2 pos, Vec2 vel, float c) {
        return getPredictionImpl(translateIn(pos), vel, c);
    }

    private Collision getPredictionImpl(Vec2 pos, Vec2 vel, float c) {
        if (vel.y > 0) {
            Collision firstGuess = getCollisionOnHorizontal(pos, vel, height);
            float xc = firstGuess.getPosition().x;
            Collision bounceBottom;
            Vec2 newSpeed;
            if (xc < 0) {
                Collision secondGuess = getCollisionOnVertical(pos, vel, 0);
                float firstAmountOfTime = secondGuess.getTime();
                Vec2 newPosition = secondGuess.getPosition();
                newSpeed = new Vec2(-vel.x, vel.y);
                Collision collision = getPredictionForBallMovingDown(newPosition, newSpeed, height);
                bounceBottom = new Collision(collision.getPosition(), firstAmountOfTime
                        + collision.getTime());
            } else if (0 <= xc && xc < width) {

                bounceBottom = firstGuess;
                newSpeed = vel;
            } else {
                Collision secondGuess = getCollisionOnVertical(pos, vel, width);
                float firstAmountOfTime = secondGuess.getTime();
                Vec2 newPosition = secondGuess.getPosition();
                newSpeed = new Vec2(-vel.x, vel.y);
                Collision collision = getPredictionForBallMovingDown(newPosition, newSpeed, height);
                bounceBottom = new Collision(collision.getPosition(), firstAmountOfTime
                        + collision.getTime());
            }
            Vec2 newVel = new Vec2(newSpeed.x, -newSpeed.y);
            System.out.println("New vel" + newVel + " pos " + bounceBottom.getPosition());
            final Collision predictionForBallMovingUp = getPredictionForBallMovingUp(bounceBottom.getPosition(), newVel, c);

            return translateOut(new Collision(predictionForBallMovingUp.getPosition(), bounceBottom.getTime() + predictionForBallMovingUp.getTime()));

        } else {
            return translateOut(getPredictionForBallMovingUp(pos, vel, c));
        }
    }

    /**
     * Calculates the intersection of the path of ball and the horizontal line
     * y=c
     *
     * @param pos The position of the ball
     * @param vel The velocity of the ball
     * @param c The horizontal line y=c
     * @return The time and place of intersection
     */
    public Collision getPredictionForBallMovingUp(Vec2 pos, Vec2 vel, float c) {
        if (vel.y > 0) {
            throw new IllegalStateException("Moving in the wrong direction");
        }
        Collision firstGuess = getCollisionOnHorizontal(pos, vel, c);
        if (firstGuess.getTime() > 0) {
            float xc = firstGuess.getPosition().x;
            if (xc < 0) {
                Collision secondGuess = getCollisionOnVertical(pos, vel, c);
                float firstAmountOfTime = secondGuess.getTime();
                if (firstAmountOfTime > 0) {
                    Vec2 newPosition = secondGuess.getPosition();
                    Vec2 newSpeed = new Vec2(-vel.x, vel.y);
                    Collision collision = getPredictionForBallMovingUp(newPosition, newSpeed, c);
                    return new Collision(collision.getPosition(), firstAmountOfTime
                            + collision.getTime());
                } else {
                    throw new IllegalStateException("Cannot predict");
                }
            } else if (0 <= xc && xc < width) {
                return firstGuess;
            } else {
                Collision secondGuess = getCollisionOnVertical(pos, vel, width);
                float firstAmountOfTime = secondGuess.getTime();
                Vec2 newPosition = secondGuess.getPosition();
                Vec2 newSpeed = new Vec2(-vel.x, vel.y);
                Collision collision = getPredictionForBallMovingUp(newPosition, newSpeed, c);
                return new Collision(collision.getPosition(), firstAmountOfTime
                        + collision.getTime());
            }
        } else {
            throw new IllegalStateException("Cannot predict");

        }
    }

    public Collision getPredictionForBallMovingDown(Vec2 pos, Vec2 vel, float c) {
        if (vel.y < 0) {
            throw new IllegalStateException("Moving in the wrong direction");
        }
        Collision firstGuess = getCollisionOnHorizontal(pos, vel, c);
        float xc = firstGuess.getPosition().x;
        if (xc < 0) {
            Collision secondGuess = getCollisionOnVertical(pos, vel, c);
            float firstAmountOfTime = secondGuess.getTime();
            Vec2 newPosition = secondGuess.getPosition();
            Vec2 newSpeed = new Vec2(-vel.x, vel.y);
            Collision collision = getPredictionForBallMovingDown(newPosition, newSpeed, c);
            return new Collision(collision.getPosition(), firstAmountOfTime
                    + collision.getTime());
        } else if (0 <= xc && xc < width) {
            return firstGuess;
        } else {
            Collision secondGuess = getCollisionOnVertical(pos, vel, width);
            float firstAmountOfTime = secondGuess.getTime();
            Vec2 newPosition = secondGuess.getPosition();
            Vec2 newSpeed = new Vec2(-vel.x, vel.y);
            Collision collision = getPredictionForBallMovingDown(newPosition, newSpeed, c);
            return new Collision(collision.getPosition(), firstAmountOfTime
                    + collision.getTime());
        }
    }

    Collision getCollisionOnHorizontal(Vec2 ballPos, Vec2 ballVel, float c) {
        float x = ballPos.x + (((c - ballPos.y) / ballVel.y) * ballVel.x);
        Vec2 returnValue = new Vec2(x, c);
        float time = (c - ballPos.y) / ballVel.y;
        if (time < 0) {
            throw new IllegalStateException("Moving in the wrong direction");
        }
        Collision col = new Collision(returnValue, time);
        return col;
    }

    Collision getCollisionOnVertical(Vec2 ballPos, Vec2 ballVel, float c) {
        float time = (c - ballPos.x) / ballVel.x;
        if (time < 0) {
            throw new IllegalStateException("Moving in the wrong direction");
        }
        float y = ballPos.y + time * ballVel.y;
        Vec2 returnValue = new Vec2(c, y);
        Collision col = new Collision(returnValue, time);
        return col;
    }
}
