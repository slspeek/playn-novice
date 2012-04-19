package pong.android;

import playn.android.GameActivity;
import playn.core.Game;
import playn.core.PlayN;

import pong.core.PongGame;
import pong.core.PongGameFactory;

public class PongGameActivity extends GameActivity {

    @Override
    public void main() {
        platform().assetManager().setPathPrefix("pong/resources");
        Game game = (new PongGameFactory()).get();
        PlayN.run(game);
    }
}
