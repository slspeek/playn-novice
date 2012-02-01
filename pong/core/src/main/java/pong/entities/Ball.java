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

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import playn.core.Canvas;
import playn.core.Surface;
import playn.core.Sound;    // added JT
import static playn.core.PlayN.assetManager; // added JT

import pong.core.PongWorld;

/**
 * Moves around and bounces on everything.
 *
 */
public class Ball extends DynamicPhysicsEntity implements
        PhysicsEntity.HasContactListener {

    public static String TYPE = "Ball";
    Sound Ballsidesnd;  // added JT for side-edge sound
    
    public Ball(PongWorld pongWorld, World world, float x, float y, float angle) {
        super(pongWorld, world, x, y, angle);
        Ballsidesnd = assetManager().getSound("images/Pong-Ballside"); // added JT for side-edge sound
        
    }

    @Override
    Body initPhysicsBody(World world, float x, float y, float angle) {
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = getRadius()*PongWorld.physUnitPerScreenUnit;
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1f;
        //circleShape.m_p.set(getWidth()/2*PongWorld.physUnitPerScreenUnit, getHeight()/2*PongWorld.physUnitPerScreenUnit);
        circleShape.m_p.set(0,0);
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.0f);
        body.setTransform(new Vec2(x, y), angle);
        return body;
    }

    public void paint(float alpha) {
       
        canvas.setFillColor(0xFF00FF00);
        canvas.fillCircle(getWidth()/2, getHeight()/2, getRadius());
        layer.setScale(PongWorld.physUnitPerScreenUnit);
        
        // added JT for side-edge sound (next 3 code lines)
        float ballX = this.getBody().getPosition().x;
        if (ballX == 0.629375f || (ballX <= 39.07626f && ballX >= 38.852028f)) {    
        //if (ballX == (float) this.getBody().getWidth()/2) {    
            Ballsidesnd.play();
            // System.out.println("Ball hits edge: " + this.getBody().getPosition().x);
        }    


        super.paint(alpha);
    }

    @Override
    float getWidth() {
        return 2 * getRadius();
    }

    @Override
    float getHeight() {
        return 2 * getRadius();
    }

    float getRadius() {
        return 10f;
    }

    @Override
    public void contact(PhysicsEntity other) {
        
    }
}
