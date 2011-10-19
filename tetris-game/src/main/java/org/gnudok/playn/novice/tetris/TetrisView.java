package org.gnudok.playn.novice.tetris;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyListener;

public interface TetrisView{

		void setStatusText(String text);
		
		void addKeyListener(KeyListener listener);

		Dimension getSize();

		void repaint();
		
		void setPaintable(Paintable p);
		
		Component asCompenent();
		
		
}
