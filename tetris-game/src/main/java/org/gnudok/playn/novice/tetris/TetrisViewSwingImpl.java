package org.gnudok.playn.novice.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("javadoc")
public class TetrisViewSwingImpl extends JPanel implements TetrisView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -110735668571632303L;
	Paintable p;
	JPanel innerBoard  = new JPanel() {
		public void paint(Graphics g) {
			super.paint(g);
			TetrisGraphics tg = new TetrisGraphicsAWTImpl(g);
			p.onPaint(tg);
		}
	};
	JLabel status = new JLabel("Kikeboe");
	
	public TetrisViewSwingImpl() {
		setBackground(Color.CYAN);
		setForeground(Color.yellow);
		this.status.setBackground(Color.blue);
		this.status.setSize(100,100);
		setLayout(new BorderLayout());
		add(this.innerBoard, BorderLayout.CENTER);
		add(this.status, BorderLayout.SOUTH);
	}
	
	@Override
	public void setStatusText(String text) {
		status.setText(text);
	}

	
	@Override
	public void addKeyListener(KeyListener listener) {
		System.out.println("D'r in");
		super.addKeyListener(listener);
	}

	@Override
	public Dimension getSize() {
		Dimension s = innerBoard.getSize();
		return new Dimension(s.width, s.height - 20);
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
	
	
	

}
