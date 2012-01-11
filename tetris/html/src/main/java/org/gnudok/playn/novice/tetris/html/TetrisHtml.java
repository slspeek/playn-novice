package org.gnudok.playn.novice.tetris.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import org.gnudok.playn.novice.tetris.core.Tetris;

public class TetrisHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assetManager().setPathPrefix("tetris/");
    PlayN.run(new Tetris());
  }
}
