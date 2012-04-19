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
package pong.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.DebugDrawBox2D;
import playn.core.GroupLayer;
import static playn.core.PlayN.graphics;
import pong.entities.*;

/**
 * Creates the world we play in.
 *
 * @author youssef
 *
 */
public class PongWorld implements ContactListener, Paintable {

    public GroupLayer staticLayerBack;
    public GroupLayer dynamicLayer;
    public static final float physUnitPerScreenUnit = (float) (1.0f / (640.0f / 40.0f));
    static public final int WIDTH = 40;
    static public final int HEIGHT = 30;
    final private World world;

    public World getWorld() {
        return world;
    }

    private List<Entity> entities = new ArrayList<Entity>(0);
    private HashMap<Body, PhysicsEntity> bodyEntityLUT = new HashMap<Body, PhysicsEntity>();
    private Stack<Contact> contacts = new Stack<Contact>();
    private static boolean showDebugDraw = false;
    private DebugDrawBox2D debugDraw;
    

    public PongWorld(GroupLayer scaledLayer, int screenWidth, int screenHeight) {
        staticLayerBack = graphics().createGroupLayer();
        scaledLayer.add(staticLayerBack);
        dynamicLayer = graphics().createGroupLayer();
        scaledLayer.add(dynamicLayer);

        // create the physics world
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        world = new World(gravity, true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        world.setContactListener(this);
    }

    @Override
    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        // the step delta is fixed so box2d isn't affected by framerate
        world.step(0.033f, 10, 10);
        processContacts();
    }

    @Override
    public void paint(float delta) {
        for (Entity e : entities) {
            e.paint(delta);
        }
    }

    public void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof PhysicsEntity) {
            PhysicsEntity physicsEntity = (PhysicsEntity) entity;
            bodyEntityLUT.put(physicsEntity.getBody(), physicsEntity);
        }
    }

    public void processContacts() {
        while (!contacts.isEmpty()) {
            Contact contact = contacts.pop();

            // handle collision
            PhysicsEntity entityA = bodyEntityLUT.get(contact.m_fixtureA.m_body);
            PhysicsEntity entityB = bodyEntityLUT.get(contact.m_fixtureB.m_body);

            if (entityA != null && entityB != null) {
                if (entityA instanceof PhysicsEntity.HasContactListener) {
                    ((PhysicsEntity.HasContactListener) entityA).contact(entityB);
                }
                if (entityB instanceof PhysicsEntity.HasContactListener) {
                    ((PhysicsEntity.HasContactListener) entityB).contact(entityA);
                }
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        contacts.push(contact);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
