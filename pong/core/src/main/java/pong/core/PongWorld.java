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
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.CanvasLayer;
import playn.core.DebugDrawBox2D;
import playn.core.Font;
import playn.core.GroupLayer;
import static playn.core.PlayN.graphics;
import pong.entities.*;

/**
 * Creates the world we play in.
 *
 * @author youssef
 *
 */
public class PongWorld implements ContactListener {

    public GroupLayer staticLayerBack;
    public GroupLayer dynamicLayer;
    // scale difference between screen space (pixels) and world space (physics).
    public static final float physUnitPerScreenUnit = (float)1.0/(640.0f/40.0f);
    // size of world
    static public final int WIDTH = 40;
    static public final int HEIGHT = 30;
    // box2d object containing physics world
    protected World world;
    public Ground  ground;
    public Ceiling ceiling;
    public ScoreBoard playerScoreBoard;
    public ScoreBoard botScoreBoard;
    public MessageBoard messageBoard;
    private List<Entity> entities = new ArrayList<Entity>(0);
    private HashMap<Body, PhysicsEntity> bodyEntityLUT = new HashMap<Body, PhysicsEntity>();
    private Stack<Contact> contacts = new Stack<Contact>();
    private static boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;
    private final PongGame game;

    public PongWorld(PongGame game, GroupLayer scaledLayer) {
        this.game = game;
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

        // create the ground
        ground = new Ground(this, world, WIDTH/2 , HEIGHT, 0);
        add(ground);
        ground.setGame(game);
        
        
        //create the ceiling
        ceiling = new Ceiling(this, world, WIDTH/2, 0f, 0f);
        add(ceiling);
        ceiling.setGame(game);
        // create the ceil
//        Body ceiling = world.createBody(new BodyDef());
//        PolygonShape ceilingShape = new PolygonShape();
//        ceilingShape.setAsEdge(new Vec2(0, 1), new Vec2(WIDTH, 1));
//        ceiling.createFixture(ceilingShape, 0.0f);

        // create the walls
        Body wallLeft = world.createBody(new BodyDef());
        PolygonShape wallLeftShape = new PolygonShape();
        wallLeftShape.setAsEdge(new Vec2(0, 0), new Vec2(0, HEIGHT));
        wallLeft.createFixture(wallLeftShape, 0.0f);
        Body wallRight = world.createBody(new BodyDef());
        PolygonShape wallRightShape = new PolygonShape();
        wallRightShape.setAsEdge(new Vec2(WIDTH, 0), new Vec2(WIDTH, HEIGHT));
        wallRight.createFixture(wallRightShape, 0.0f);

        if (showDebugDraw) {
            CanvasLayer canvasLayer = graphics().createCanvasLayer(
                    (int) (WIDTH / physUnitPerScreenUnit),
                    (int) (HEIGHT / physUnitPerScreenUnit));
            graphics().rootLayer().add(canvasLayer);
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(canvasLayer);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit
                    | DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / physUnitPerScreenUnit);
            world.setDebugDraw(debugDraw);
        }
        System.out.println("End of init");
        initBoards();
    }
  
    private void initBoards() {
        Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 64);
        messageBoard = new MessageBoard(font, new Vec2(14,7), 15f, 20f, 0xFFCCCCCC);
        font = graphics().createFont("Helvetica", Font.Style.PLAIN, 36);
        playerScoreBoard = new ScoreBoard(font, new Vec2(17, 2), 2f, 3f, 0xFFCCCCCC);
        botScoreBoard = new ScoreBoard(font, new Vec2(21, 2), 2f, 3f, 0xFFCCCCCC);
        dynamicLayer.add(playerScoreBoard.getLayer());
        dynamicLayer.add(messageBoard.getLayer());
        dynamicLayer.add(botScoreBoard.getLayer());
        //messageBoard.setMessage("Press space to begin");
        
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        // the step delta is fixed so box2d isn't affected by framerate
        world.step(0.033f, 10, 10);
        processContacts();
    }

    public void paint(float delta) {
        if (showDebugDraw) {
            debugDraw.getCanvas().canvas().clear();
            world.drawDebugData();
        }
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

    // handle contacts out of physics loop
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

    // Box2d's begin contact
    @Override
    public void beginContact(Contact contact) {
        contacts.push(contact);
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