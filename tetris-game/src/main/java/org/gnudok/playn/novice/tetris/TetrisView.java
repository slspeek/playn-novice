package org.gnudok.playn.novice.tetris;

import java.awt.event.KeyListener;

public interface TetrisView {

		void setStatusText(String text);
		
		TetrisGraphics getGraphics();
		
		void addKeyListener(KeyListener listener);
}
