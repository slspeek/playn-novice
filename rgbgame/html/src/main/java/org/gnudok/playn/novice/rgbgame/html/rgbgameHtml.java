package org.gnudok.playn.novice.rgbgame.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import org.gnudok.playn.novice.rgbgame.core.rgbgame;

public class rgbgameHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assetManager().setPathPrefix("rgbgame/");
    PlayN.run(new rgbgame());
  }
}
