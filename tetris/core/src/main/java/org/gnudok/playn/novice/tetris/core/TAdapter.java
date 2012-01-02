package org.gnudok.playn.novice.tetris.core;






import playn.core.Keyboard;
import playn.core.Keyboard.Event;


class TAdapter extends Keyboard.Adapter {

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
	public void onKeyDown(Event event) {
		if (!this.board.isStarted || this.board.curPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}
		System.out.println("aFTER PRE CONTDION");
		
		int keycode = event.keyCode();
		
		if (keycode == 'p' || keycode == 'P') {
			this.board.pause();
			return;
		}

		if (this.board.isPaused)
			return;

		switch (keycode) {
		case Keyboard.KEY_LEFT:
			System.out.println("Try move left");
			this.board.tryMove(this.board.curPiece, this.board.curX - 1, this.board.curY);
			break;
		case Keyboard.KEY_RIGHT:
			this.board.tryMove(this.board.curPiece, this.board.curX + 1, this.board.curY);
			break;
		case Keyboard.KEY_DOWN:
			this.board.tryMove(this.board.curPiece.rotateRight(), this.board.curX, this.board.curY);
			break;
		case Keyboard.KEY_UP:
			this.board.tryMove(this.board.curPiece.rotateLeft(), this.board.curX, this.board.curY);
			break;
		case Keyboard.KEY_SPACE:
			this.board.dropDown();
			break;
		case 'd':
			this.board.oneLineDown();
			break;
		case 'D':
			this.board.oneLineDown();
			break;
		}
		super.onKeyDown(event);
	}

}