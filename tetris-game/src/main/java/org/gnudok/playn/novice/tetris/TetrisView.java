package org.gnudok.playn.novice.tetris;

import java.awt.Graphics2D;
import java.awt.event.KeyListener;

public interface TetrisView {

		void setStatusText(String text);
		
		Graphics2D getGraphics2d();
		
		void addKeyListener(KeyListener listener);
}
