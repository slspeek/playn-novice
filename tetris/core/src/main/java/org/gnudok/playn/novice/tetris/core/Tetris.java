package org.gnudok.playn.novice.tetris.core;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasLayer;
import playn.core.Game;

public class Tetris implements Game {
	
	Board board;
	Canvas canvas;
	
  @Override
  public void init() {
    CanvasLayer canvasLayer = graphics().createCanvasLayer(200, 400);
    canvasLayer.setTranslation(50,50);
    canvas = canvasLayer.canvas();
    board = new Board(canvas);
    board.start();
    graphics().rootLayer().add(canvasLayer);
    
    
  }

  @Override
  public void paint(float alpha) {
	  board.onPaint(canvas);
  }

  @Override
  public void update(float delta) {
	  board.update();
  }

  @Override
  public int updateRate() {
    return 500;
  }
}
