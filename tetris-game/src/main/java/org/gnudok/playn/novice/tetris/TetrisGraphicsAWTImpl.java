package org.gnudok.playn.novice.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class TetrisGraphicsAWTImpl implements TetrisGraphics {

	private final Graphics delegate;

	public TetrisGraphicsAWTImpl(Graphics delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void setColor(Color color) {
		delegate.setColor(color);
	}

	@Override
	public void fillRect(int i, int j, int k, int l) {
		delegate.fillRect(i, j, k, l);
	}

	@Override
	public void drawLine(int x, int i, int x2, int y) {
		delegate.drawLine(x, i, x2, y);
	}
	
}
