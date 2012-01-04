package pong.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.common.Vec2;
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

public class PongGame implements Game {

	int DELTA = 1;
	ImageLayer bgLayer;

	// main layer that holds the world. note: this gets scaled to world space
	GroupLayer worldLayer;

	// main world
	PongWorld world = null;
	boolean worldLoaded = false;
	Bat bat;
	LineJoint joint;
	protected boolean ballLoaded;
	int score = 0;

	@Override
	public void init() {

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

		bat = new Bat(world, world.world, PongWorld.WIDTH / 2,
				PongWorld.HEIGHT - 2, 0);
		world.add(bat);

		// hook up our pointer listener
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerDrag(Pointer.Event event) {

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
					if (!ballLoaded && worldLoaded) {
						Ball ball = new Ball(world, world.world,
								PongWorld.WIDTH / 2,
								PongWorld.HEIGHT / 2, (float)Math.PI/4);
						ball.setLinearVelocity(1, 10);
						world.add(ball);
						ballLoaded = true;
					}

					break;
				case LEFT:
					Vec2 oldPos = bat.getBody().getPosition();
					bat.setPos(Math.max(1, oldPos.x - DELTA), oldPos.y);
					break;
				case RIGHT:
					oldPos = bat.getBody().getPosition();

					bat.setPos(Math.min(PongWorld.WIDTH - 1, oldPos.x + DELTA),
							oldPos.y);
					break;
				case Q:
					quit();
					break;

				}

			}

			@Override
			public void onKeyUp(Keyboard.Event event) {
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

	protected void quit() {
		System.out.println("EXIT GAME, not yet working");
	}
}
