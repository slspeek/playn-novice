package org.gnudok.playn.novice.tetris.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import org.gnudok.playn.novice.tetris.core.Tetris;

public class TetrisJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("src/main/java/org/gnudok/playn/novice/tetris/resources");
    PlayN.run(new Tetris());
  }
}
