package org.gnudok.playn.novice.rgbgame.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import org.gnudok.playn.novice.rgbgame.core.rgbgame;

public class rgbgameJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("src/main/java/org/gnudok/playn/novice/rgbgame/resources");
    PlayN.run(new rgbgame());
  }
}
