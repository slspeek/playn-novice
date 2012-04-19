package pong.html;

import playn.core.Game;
import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import pong.core.PongGame;
import pong.core.PongGameFactory;

public class PongGameHtml extends HtmlGame {

    @Override
    public void start() {
        HtmlPlatform platform = HtmlPlatform.register();
        platform.assetManager().setPathPrefix("pong/");
        Game game = (new PongGameFactory()).get();
        PlayN.run(game);
    }
}
