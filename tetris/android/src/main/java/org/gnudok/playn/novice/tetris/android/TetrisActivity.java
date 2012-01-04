package org.gnudok.playn.novice.tetris.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import org.gnudok.playn.novice.tetris.core.Tetris;

public class TetrisActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("org/gnudok/playn/novice/tetris/resources");
    PlayN.run(new Tetris());
  }
}
