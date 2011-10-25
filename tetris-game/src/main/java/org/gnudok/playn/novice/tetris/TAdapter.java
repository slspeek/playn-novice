package org.gnudok.playn.novice.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.gnudok.playn.novice.tetris.Shape.Tetrominoes;

class TAdapter extends KeyAdapter {

	/**
	 * 
	 */
	private final Board board;

	/**
	 * @param board
	 */
	TAdapter(Board board) {
		this.board = board;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!this.board.isStarted || this.board.curPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}

		int keycode = e.getKeyCode();

		if (keycode == 'p' || keycode == 'P') {
			this.board.pause();
			return;
		}

		if (this.board.isPaused)
			return;

		switch (keycode) {
		case KeyEvent.VK_LEFT:
			this.board.tryMove(this.board.curPiece, this.board.curX - 1, this.board.curY);
			break;
		case KeyEvent.VK_RIGHT:
			this.board.tryMove(this.board.curPiece, this.board.curX + 1, this.board.curY);
			break;
		case KeyEvent.VK_DOWN:
			this.board.tryMove(this.board.curPiece.rotateRight(), this.board.curX, this.board.curY);
			break;
		case KeyEvent.VK_UP:
			this.board.tryMove(this.board.curPiece.rotateLeft(), this.board.curX, this.board.curY);
			break;
		case KeyEvent.VK_SPACE:
			this.board.dropDown();
			break;
		case 'd':
			this.board.oneLineDown();
			break;
		case 'D':
			this.board.oneLineDown();
			break;
		}

	}

}