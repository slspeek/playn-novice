package org.gnudok.playn.novice.rgbgame.flash;

import playn.core.PlayN;
import playn.flash.FlashGame;
import playn.flash.FlashPlatform;

import org.gnudok.playn.novice.rgbgame.core.rgbgame;

public class rgbgameFlash extends FlashGame {

  @Override
  public void start() {
    FlashPlatform platform = FlashPlatform.register();
    platform.assetManager().setPathPrefix("rgbgameflash/");
    PlayN.run(new rgbgame());
  }
}
