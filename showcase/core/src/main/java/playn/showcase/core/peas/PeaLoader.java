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

import playn.core.AssetWatcher;
import playn.core.PlayN;
import playn.core.GroupLayer;
import playn.core.Json;
import playn.core.ResourceCallback;

import playn.showcase.core.peas.entities.Block;
import playn.showcase.core.peas.entities.BlockGel;
import playn.showcase.core.peas.entities.BlockLeftRamp;
import playn.showcase.core.peas.entities.BlockRightRamp;
import playn.showcase.core.peas.entities.BlockSpring;
import playn.showcase.core.peas.entities.Cloud1;
import playn.showcase.core.peas.entities.Cloud3;
import playn.showcase.core.peas.entities.Entity;
import playn.showcase.core.peas.entities.FakeBlock;
import playn.showcase.core.peas.entities.Ball;
import playn.showcase.core.peas.entities.Portal;

public class PeaLoader {

  public static void CreateWorld(String level, final GroupLayer worldLayer,
      final ResourceCallback<PongWorld> callback) {
    final PongWorld peaWorld = new PongWorld(worldLayer);

    // load the level
    PlayN.assetManager().getText(level, new ResourceCallback<String>() {
      @Override
      public void done(String resource) {
        // create an asset watcher that will call our callback when all assets
        // are loaded
        AssetWatcher assetWatcher = new AssetWatcher(new AssetWatcher.Listener() {
          @Override
          public void done() {
            callback.done(peaWorld);
          }

          @Override
          public void error(Throwable e) {
            callback.error(e);
          }
        });

       
        // start the watcher (it will call the callback when everything is
        // loaded)
        assetWatcher.start();
      }

      @Override
      public void error(Throwable err) {
        callback.error(err);
      }
    });
  }

}
