package org.gnudok.playn.novice.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.Timer;

import org.gnudok.playn.novice.tetris.Shape.Tetrominoes;

public class Board implements ActionListener, Paintable {

	final int BoardWidth = 10;
	final int BoardHeight = 22;

	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	Shape curPiece;
	Tetrominoes[][] board;
	private TetrisView tetrisView;
	public KeyListener listener = new TAdapter();

	public Board(TetrisView tetrisView) {
		this.tetrisView = tetrisView;
		curPiece = new Shape();
		timer = new Timer(400, this);
		timer.start();
		board = new Tetrominoes[BoardWidth][BoardHeight];
		//tetrisView.addKeyListener(new TAdapter());
		tetrisView.setPaintable(this);
		clearBoard();
		
	}

	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	int squareWidth() {
		return (int) tetrisView.getSize().getWidth() / BoardWidth;
	}

	int squareHeight() {
		return (int) tetrisView.getSize().getHeight() / BoardHeight;
	}

	Tetrominoes shapeAt(int x, int y) {
		return board[x][y];
	}

	public void start() {
		if (isPaused)
			return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}

	private void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			tetrisView.setStatusText("paused");
		} else {
			timer.start();
			tetrisView.setStatusText(String.valueOf(numLinesRemoved));
		}
		tetrisView.onRepaint();
	}

	public void onPaint(TetrisGraphics g) {
		// super.paint(g);

		Dimension size = tetrisView.getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);

				drawSquare(g, 0 + j * squareWidth(), boardTop + i
						* squareHeight(), shape);

			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			Iterator<Point> it = curPiece.shapeIterator();
			while (it.hasNext()) {
				Point curPoint = it.next();
				int x = curX + curPoint.x;
				int y = curY - curPoint.y;
				drawSquare(g, 0 + x * squareWidth(), boardTop
						+ (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}
	}

	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	private void clearBoard() {
		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				board[j][i] = Tetrominoes.NoShape;
			}
		}
	}

	private void pieceDropped() {
		Iterator<Point> it = curPiece.shapeIterator();
		while (it.hasNext()) {
			Point curPoint = it.next();
			int x = curX + curPoint.x;
			int y = curY - curPoint.y;
			board[x][y] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece() {
		curPiece.setRandomShape();
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			tetrisView.setStatusText("game over");
		}
	}

	private boolean tryMove(Shape newPiece, int newX, int newY) {
		Iterator<Point> it = newPiece.shapeIterator();
		while (it.hasNext()) {
			Point curPoint = it.next();
			int x = newX + curPoint.x;
			int y = newY - curPoint.y;
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}

		curPiece = newPiece;
		curX = newX;
		curY = newY;
		tetrisView.onRepaint();
		return true;
	}

	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[j][k] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			tetrisView.setStatusText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			tetrisView.onRepaint();
		}
	}

	private void drawSquare(TetrisGraphics g, int x, int y, Tetrominoes shape) {
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102),
				new Color(102, 204, 102), new Color(102, 102, 204),
				new Color(204, 204, 102), new Color(204, 102, 204),
				new Color(102, 204, 204), new Color(218, 170, 0) };

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y
				+ squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x
				+ squareWidth() - 1, y + 1);

	}

	class TAdapter implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Huh");
			if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {
				return;
			}

			int keycode = e.getKeyCode();

			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}

			if (isPaused)
				return;

			switch (keycode) {
			case KeyEvent.VK_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KeyEvent.VK_DOWN:
				tryMove(curPiece.rotateRight(), curX, curY);
				break;
			case KeyEvent.VK_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
				break;
			case KeyEvent.VK_SPACE:
				dropDown();
				break;
			case 'd':
				oneLineDown();
				break;
			case 'D':
				oneLineDown();
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Huh");
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Huh");
			
		}
	}
}
