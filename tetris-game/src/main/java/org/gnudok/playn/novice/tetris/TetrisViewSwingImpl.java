package org.gnudok.playn.novice.tetris;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class TetrisViewSwingImpl extends JPanel implements TetrisView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -110735668571632303L;
	Paintable p;
	@Override
	public void setStatusText(String text) {
		

	}

	
	@Override
	public void addKeyListener(KeyListener listener) {
		System.out.println("D'r in");
		super.addKeyListener(listener);
	}

	@Override
	public Dimension getSize() {
		return super.getSize();
	}

	@Override
	public void onRepaint() {
		repaint();
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
		super.repaint();
		TetrisGraphics tg = new TetrisGraphicsAWTImpl(g);
		p.onPaint(tg);
	}
	

}
