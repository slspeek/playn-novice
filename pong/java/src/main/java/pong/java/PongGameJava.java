package pong.java;

import playn.core.Game;
import playn.core.PlayN;
import playn.java.JavaPlatform;

import pong.core.PongGameFactory;

public class PongGameJava {
    
    public static void main(String[] args) {
        try {
            JavaPlatform platform = JavaPlatform.register();
            platform.assetManager().setPathPrefix("pong/resources");
            Game game = (new PongGameFactory()).get();
            PlayN.run(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
