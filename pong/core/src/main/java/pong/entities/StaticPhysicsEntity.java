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

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import pong.core.PongWorld;


public abstract class StaticPhysicsEntity extends Entity implements PhysicsEntity {
  private Body body;

  public StaticPhysicsEntity(final PongWorld pongWorld, World world, float x, float y, float angle) {
    super(pongWorld, x, y, angle);
    body = initPhysicsBody(world, x, y, angle);
  }

  abstract Body initPhysicsBody(World world, float x, float y, float angle);

  @Override
  public void paint(float alpha) {
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public void initPreLoad(final PongWorld PongWorld) {
  }

  @Override
  public void initPostLoad(final PongWorld PongWorld) {
    PongWorld.staticLayerBack.add(layer);
  }

  @Override
  public void setPos(float x, float y) {
    throw new RuntimeException("Error setting position on static entity");
  }

  @Override
  public void setAngle(float a) {
    throw new RuntimeException("Error setting angle on static entity");
  }

  @Override
  public Body getBody() {
    return body;
  }
}
