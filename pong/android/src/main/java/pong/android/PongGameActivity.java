package pong.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import pong.core.PongGame;

public class PongGameActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("pong/resources");
    PlayN.run(new PongGame());
  }
}
