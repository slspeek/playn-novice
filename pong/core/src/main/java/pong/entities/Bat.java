/**
 * Copyright 2011 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pong.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Canvas;
import static playn.core.PlayN.assetManager;
import playn.core.Sound;
import playn.core.Surface;
import pong.core.GameState;
import pong.core.PongGame;
import pong.core.PongWorld;
import pong.core.ScoreBoard;

/**
 * Our Bat who bounces the Ball.
 *
 */
public class Bat extends DynamicPhysicsEntity implements
        PhysicsEntity.HasContactListener {

    public static String TYPE = "Bat";
    Sound ding, Playerwinssnd;
    PongWorld pongWorld;
    ScoreBoard scoreBoard;

    public Bat(final PongWorld pongWorld, World world, float x, float y,
            float angle) {
        super(pongWorld, world, x, y, angle);
        this.pongWorld = pongWorld;
        // load a sound that we'll play when placing sprites
        ding = assetManager().getSound("images/Pong-Bathit"); // ball hits bat
        Playerwinssnd = assetManager().getSound("images/Pong-Playerwin"); // added JT for Player wins sound
    }
    
    public void setScoreBoard(ScoreBoard board) {
        this.scoreBoard = board;
    }

    @Override
    Body initPhysicsBody(World world, float x, float y, float angle) {
        FixtureDef fixtureDef = new FixtureDef() {
        };
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        Vec2[] polygon = new Vec2[4];
        polygon[0] = new Vec2(-getWidth() / 2f, -getHeight() / 2f
                + getTopOffset());
        polygon[1] = new Vec2(getWidth() / 2f, -getHeight() / 2f
                + getTopOffset());
        polygon[2] = new Vec2(getWidth() / 2f, getHeight() / 2f);
        polygon[3] = new Vec2(-getWidth() / 2f, getHeight() / 2f);
        polygonShape.set(polygon, polygon.length);
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 5.1f;
        fixtureDef.restitution = 0.8f;
        fixtureDef.density = 100f;
        body.createFixture(fixtureDef);
        body.setTransform(new Vec2(x, y), angle);
        return body;
    }

    @Override
    float getWidth() {
        return 5.0f;
    }

    @Override
    float getHeight() {
        return 1f;
    }

    /**
     * Return the size of the offset where the block is slightly lower than
     * where the image is drawn for a depth effect
     */
    public float getTopOffset() {
        return 2.0f / 8f;
    }

    public void paint(float alpha) {
        canvas.setFillColor(0xFFFF0000);
        canvas.fillRect(-getWidth()/2, 0, getWidth(), getHeight());
        super.paint(alpha);
    }

    @Override
    public void contact(PhysicsEntity other) {
        ding.play();
        // added JT 
        //scoreBoard.increaseScore();
        // do somethings when predef winning score reached....
        if (pongWorld.botScoreBoard.getScore()    == PongGame.WINNING_SCORE ||
            pongWorld.playerScoreBoard.getScore() == PongGame.WINNING_SCORE   ) {
            //System.out.println("WINNING SCORE REACHED <---------" + PongGame.WINNING_SCORE);
            pongWorld.getGame().stopMovingParts();
            if (pongWorld.playerScoreBoard.getScore() == PongGame.WINNING_SCORE) {
                Playerwinssnd.play();
                pongWorld.getGame().setGameState(GameState.GameOver);
                return;
            }
            //else {// Play bot wins sound
            //}
            // pongWorld.getGame().setState() = GameState.BeforeStart;
            // also needs resetting gameState
        }
        
        scoreBoard.increaseScore();

        Vec2 velocity = other.getBody().getLinearVelocity();
        Vec2 newSpeed = newSpeed(velocity);
        other.getBody().setLinearVelocity(newSpeed);
    }

    private Vec2 newSpeed(Vec2 velocity) {
        float vx = velocity.x;
        float vy = velocity.y;
        if (vy < 0) {
            vy -= 1f;
        } else {
            vy += 1f;
        }
        if (vx < 0) {
            vx -= 1f;
        } else {
            vx += 1f;
        }
        Vec2 newSpeed = new Vec2(vx, vy);
        return newSpeed;
    }
}
