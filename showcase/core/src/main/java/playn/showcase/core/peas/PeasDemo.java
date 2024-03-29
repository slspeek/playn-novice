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
package playn.showcase.core.peas;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.ResourceCallback;
import playn.showcase.core.Demo;
import playn.showcase.core.peas.entities.Bat;
import playn.showcase.core.peas.entities.Ball;

public class PeasDemo extends Demo {

	// scale difference between screen space (pixels) and world space (physics).
	public static float physUnitPerScreenUnit = 1 / 26.666667f;

	int DELTA = 1;

	ImageLayer bgLayer;

	// main layer that holds the world. note: this gets scaled to world space
	GroupLayer worldLayer;

	// main world
	PongWorld world = null;
	boolean worldLoaded = false;

	Bat bat;
	org.jbox2d.dynamics.joints.PrismaticJoint joint;

	@Override
	public String name() {
		return "Pea Physics";
	}

	@Override
	public void init() {

		graphics().setSize( (int) (PongWorld.width/physUnitPerScreenUnit), (int) (PongWorld.height/physUnitPerScreenUnit));
		// load and show our background image
		Image bgImage = assetManager().getImage("peas/images/Zwart.png");
		bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);

		// create our world layer (scaled to "world space")
		worldLayer = graphics().createGroupLayer();
		worldLayer.setScale(1f / physUnitPerScreenUnit);
		graphics().rootLayer().add(worldLayer);

		PeaLoader.CreateWorld("peas/levels/level1.json", worldLayer,
				new ResourceCallback<PongWorld>() {
					@Override
					public void done(PongWorld resource) {
						world = resource;
						worldLoaded = true;
						bat = new Bat(world, world.world, PongWorld.width/2, PongWorld.height -2, 0);
						world.add(bat);

					}

					@Override
					public void error(Throwable err) {
						PlayN.log().error(
								"Error loading pea world: " + err.getMessage());
					}
				});

		// hook up our pointer listener
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				if (worldLoaded) {
					Ball pea = new Ball(world, world.world, physUnitPerScreenUnit
							* event.x(), physUnitPerScreenUnit * event.y(), 0);
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
					bat.setPos(Math.min(PongWorld.width - 3, oldPos.x + DELTA),
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
	public void shutdown() {
		bgLayer.destroy();
		bgLayer = null;
		worldLayer.destroy();
		worldLayer = null;
		world = null;
		worldLoaded = false;
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
}
