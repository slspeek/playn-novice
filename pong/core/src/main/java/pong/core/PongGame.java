package pong.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.common.Vec2;

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
}
