package pong.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import pong.core.PongGame;

public class PongGameJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("pong/resources");
    PlayN.run(new PongGameJavaExt());
  }
}
