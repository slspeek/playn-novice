package pong.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import playn.core.Canvas;
import playn.core.Surface;
import pong.core.IPongGame;
import pong.core.PongGame;

import pong.core.PongWorld;

/**
 * Resets or destroys the ball if hit
 *
 */
public class Ceiling extends StaticPhysicsEntity implements
        PhysicsEntity.HasContactListener {

    public Ceiling(PongWorld pongWorld, World world, float x, float y,
            float angle) {
        super(pongWorld, world, x, y, angle);
         postConstructionInit(pongWorld);
        //postPhysicsContructorInit(world, x, y, angle);
    }

    private IPongGame game;
    
    public void setGame(IPongGame game) {
        this.game = game;
    }
    
    
    @Override
    Body initPhysicsBody(World world, float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        Body ground = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        Vec2[] polygon = new Vec2[4];
        polygon[0] = new Vec2(x - getWidth() / 2, y - getHeight() / 2);
        polygon[1] = new Vec2(x + getWidth() / 2, y - getHeight() / 2);
        polygon[2] = new Vec2(x + getWidth() / 2, y + getHeight() / 2);
        polygon[3] = new Vec2(x - getWidth() / 2, y + getHeight() / 2);
        polygonShape.set(polygon, polygon.length);
        ground.createFixture(polygonShape, 0.0f);
        System.out.println("In init of ceiling");
        return ground;
    }

    @Override
    float getWidth() {
        return PongWorld.WIDTH;
    }

    @Override
    float getHeight() {
        return 1f;
    }

    @Override
    public void paint(float alpha) {
        canvas.setFillColor(0xFF00FFFF);
        canvas.fillRect(0, 0, 10, 10);
        super.paint(alpha);
    }

    @Override
    public void contact(PhysicsEntity other) {
        game.increasePlayerScore();
        game.autoServe();
    }
}
