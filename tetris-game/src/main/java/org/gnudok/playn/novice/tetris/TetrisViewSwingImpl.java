package org.gnudok.playn.novice.tetris;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class TetrisViewSwingImpl extends JPanel implements TetrisView {

	Paintable p;
	@Override
	public void setStatusText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addKeyListener(KeyListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub

	}

	
	@Override
	public Component asCompenent() {
		return this;
	}

	@Override
	public void setPaintable(Paintable p) {
		this.p = p;
		
	}
	
	public void paint(Graphics g) {
		TetrisGraphics tg = new TetrisGraphicsAWTImpl(g);
		p.onPaint(tg);
	}
	

}
