package pong.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.LineJoint;
import org.jbox2d.dynamics.joints.LineJointDef;
import org.jbox2d.dynamics.joints.PrismaticJoint;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Sound;
import pong.entities.Ball;
import pong.entities.Bat;

public class PongGame implements Game, ContactListener {

	int DELTA = 1;
	ImageLayer bgLayer;

	// main layer that holds the world. note: this gets scaled to world space
	GroupLayer worldLayer;

	// main world
	PongWorld world = null;
	boolean worldLoaded = false;

	Bat bat;
	
	LineJoint joint;
	Sound ding;

	@Override
	public void init() {
		// load a sound that we'll play when placing sprites
	    ding = assetManager().getSound("images/ding");

		graphics().setSize(
				(int) (PongWorld.WIDTH / PongWorld.physUnitPerScreenUnit),
				(int) (PongWorld.HEIGHT / PongWorld.physUnitPerScreenUnit));
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
		world.world.setContactListener(this);
		
		bat = new Bat(world, world.world, PongWorld.WIDTH/2, PongWorld.HEIGHT -2, 0);
		world.add(bat);
		
		// hook up our pointer listener
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				if (worldLoaded) {
					Ball pea = new Ball(world, world.world,
							PongWorld.physUnitPerScreenUnit * event.x(),
							PongWorld.physUnitPerScreenUnit * event.y(), 0);
					Random rnd = new Random();
					pea.setLinearVelocity(10 * rnd.nextFloat() - 5,
							rnd.nextFloat() * 10 - 5);
					world.add(pea);
				}
			}
		});

		PlayN.keyboard().setListener(new Keyboard.Adapter() {
			@Override
			public void onKeyDown(Keyboard.Event event) {
				switch (event.key()) {
				case LEFT:
					Vec2 oldPos = bat.getBody().getPosition();
					System.out.println("Bat old position: "
							+ bat.getBody().getPosition());
					bat.setPos(Math.max(0, oldPos.x - DELTA), oldPos.y);
					System.out.println("Bat position: "
							+ bat.getBody().getPosition());
					break;
				case RIGHT:
					oldPos = bat.getBody().getPosition();
					System.out.println("Bat old position: "
							+ bat.getBody().getPosition());
					bat.setPos(Math.min(PongWorld.WIDTH - 3, oldPos.x + DELTA),
							oldPos.y);
					System.out.println("Bat position: "
							+ bat.getBody().getPosition());
					break;

				}

			}

			@Override
			public void onKeyUp(Keyboard.Event event) {
				System.out.println("Up Hello " + event.key());
			}

		});

		initJoint();
	}

	private void initJoint() {
		LineJointDef jd = new LineJointDef();
		
		// Bouncy limit
		Vec2 axis = new Vec2(1.0f, 0.0f);
		axis.normalize();
		jd.initialize(world.ground, bat.getBody(), new Vec2(0.0f, 8.5f), axis);
		
		jd.motorSpeed = 0.0f;
		jd.maxMotorForce = 100.0f;
		jd.enableMotor = true;
		jd.lowerTranslation = -4.0f;
		jd.upperTranslation = 4.0f;
		jd.enableLimit = true;
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
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
	
	// Box2d's begin contact
	@Override
	public void beginContact(Contact contact) {
		//System.out.println("Begin contact");
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureA();
		
		Body bB = fB.getBody();
		Body bA = fA.getBody();
		Body bBat = bat.getBody();
		if (bB.equals(bBat) || bA.equals(bBat)) {
			System.out.println("Bat hit");
			ding.play();
		}
		//contacts.push(contact);
	}

	// Box2d's end contact
	@Override
	public void endContact(Contact contact) {
	}

	// Box2d's pre solve
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	// Box2d's post solve
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
	
}
