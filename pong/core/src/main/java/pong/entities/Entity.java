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

import playn.core.*;
import static playn.core.PlayN.graphics;
import pong.core.PongWorld;

public abstract class Entity {

    ImageLayer layer;
    Canvas canvas;
    float x, y, angle;
    private final PongWorld pongWorld;

    public Entity(final PongWorld pongWorld, float px, float py, float pangle) {
        this.x = px;
        this.y = py;
        this.angle = pangle;
        this.pongWorld = pongWorld;
        //postConstructionInit(pongWorld);
    }

    protected void postConstructionInit(final PongWorld pongWorld) {
        System.out.println("Grootes: " + getWidth() + "  " + getHeight());
        CanvasImage image = graphics().createImage((int)getWidth(), (int)getHeight());
        canvas = image.canvas();
        layer = graphics().createImageLayer(image);
        initPreLoad(pongWorld);
                
        canvas.setFillColor(0xFFE00EEE);
        canvas.fillRect(0, 0, canvas.width(), canvas.height());
        layer.setOrigin(getWidth()/2, getHeight()/2);
    }
   
  /**
   * Perform pre-image load initialization (e.g., attaching to PeaWorld layers).
   *
   * @param peaWorld
   */
  public abstract void initPreLoad(final PongWorld peaWorld);

  /**
   * Perform post-image load initialization (e.g., attaching to PeaWorld layers).
   *
   * @param peaWorld
   */
  public abstract void initPostLoad(final PongWorld peaWorld);

  public void paint(float alpha) {
  }

  public void update(float delta) {
  }

  public void setPos(float x, float y) {
    layer.setTranslation(x, y);
  }

  public void setAngle(float a) {
    layer.setRotation(a);
  }

  abstract float getWidth();

  abstract float getHeight();

}
