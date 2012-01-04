package org.gnudok.playn.novice.tetris.flash;

import playn.core.PlayN;
import playn.flash.FlashGame;
import playn.flash.FlashPlatform;

import org.gnudok.playn.novice.tetris.core.Tetris;

public class TetrisFlash extends FlashGame {

  @Override
  public void start() {
    FlashPlatform platform = FlashPlatform.register();
    platform.assetManager().setPathPrefix("tetrisflash/");
    PlayN.run(new Tetris());
  }
}
