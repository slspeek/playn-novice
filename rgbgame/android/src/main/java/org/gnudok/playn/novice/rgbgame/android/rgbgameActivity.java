package org.gnudok.playn.novice.rgbgame.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import org.gnudok.playn.novice.rgbgame.core.rgbgame;

public class rgbgameActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("org/gnudok/playn/novice/rgbgame/resources");
    PlayN.run(new rgbgame());
  }
}
