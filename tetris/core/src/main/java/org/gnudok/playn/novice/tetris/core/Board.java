package org.gnudok.playn.novice.tetris.core;

import static playn.core.PlayN.keyboard;

import java.awt.Point;
import java.util.Iterator;

import org.gnudok.playn.novice.tetris.game.Shape;
import org.gnudok.playn.novice.tetris.game.Tetrominoes;

import playn.core.Canvas;
import playn.core.Color;
import playn.core.Keyboard;

public class Board {

	final int BoardWidth = 10;
	final int BoardHeight = 22;
	/** Determines whether a new piece needs to be inserted */
	boolean isFallingFinished = false;
	/** Game was started */
	boolean isStarted = false;
	/** Games was paused */
	boolean isPaused = false;
	/** Stores the score */
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	/** The shape that is currently falling */
	Shape curPiece;
	/** The board */
	Tetrominoes[][] board;
	/** The keyboard controlling */
	public Keyboard.Listener listener = new TAdapter(this);
	Canvas canvas;

	public Board(Canvas canvas) {
		this.canvas = canvas;
		keyboard().setListener(listener);
		curPiece = new Shape();
		board = new Tetrominoes[BoardWidth][BoardHeight];
		clearBoard();
		
	}

	public void update() {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	int squareWidth() {
		return (int) canvas.width() / BoardWidth;
	}

	int squareHeight() {
		return (int) canvas.height() / BoardHeight;
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
		//timer.start();
	}

	void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			//timer.stop();
			//tetrisView.setStatusText("paused");
		} else {
			//timer.start();
			//tetrisView.setStatusText(String.valueOf(numLinesRemoved));
		}
		//tetrisView.onRepaint();
	}

	public void onPaint(Canvas g) {
		int boardTop = (int) g.height() - BoardHeight * squareHeight();

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

	void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	void oneLineDown() {
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
			isStarted = false;
			//tetrisView.setStatusText("game over");
		}
	}

	boolean tryMove(Shape newPiece, int newX, int newY) {
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
		//tetrisView.onRepaint();
		return true;
	}

	void removeFullLines() {
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
			//tetrisView.setStatusText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			//tetrisView.onRepaint();
		}
		
	}

	private void drawSquare(Canvas g, int x, int y, Tetrominoes shape) {
		int colors[] = { Color.rgb(0, 0, 0), Color.rgb(204, 102, 102),
				Color.rgb(102, 204, 102), Color.rgb(102, 102, 204),
				Color.rgb(204, 204, 102), Color.rgb(204, 102, 204),
				Color.rgb(102, 204, 204), Color.rgb(218, 170, 0) };

		int color = colors[shape.ordinal()];

		g.setFillColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		//g.setColor(color.brighter());
		g.setStrokeColor(color);
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		//g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y
				+ squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x
				+ squareWidth() - 1, y + 1);

	}

}
