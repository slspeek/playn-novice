package org.gnudok.playn.novice.tetris;

import java.awt.Color;

public interface TetrisGraphics {

	void setColor(Color color);

	void fillRect(int i, int j, int k, int l);

	void drawLine(int x, int i, int x2, int y);

}
