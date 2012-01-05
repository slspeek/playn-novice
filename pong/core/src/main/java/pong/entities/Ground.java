package pong.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import pong.core.PongWorld;

public class Ground extends StaticPhysicsEntity implements
		PhysicsEntity.HasContactListener {

	public Ground(PongWorld pongWorld, World world, float x, float y,
			float angle) {
		super(pongWorld, world, x, y, angle);
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
		System.out.println("In init");
		return ground;
	}

	@Override
	float getWidth() {
		return PongWorld.WIDTH;
	}

	@Override
	float getHeight() {
		return 0.3f;
	}

	@Override
	String getImageName() {
		return "line.png";
	}

	@Override
	public void contact(PhysicsEntity other) {
		System.out.println("Hit the ground");
	}

}
