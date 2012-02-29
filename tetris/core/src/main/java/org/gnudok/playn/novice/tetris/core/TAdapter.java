package org.gnudok.playn.novice.tetris.core;






import playn.core.Key;
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
		
		Key keycode = event.key();
		
		if (keycode.equals(Key.P)) {
			this.board.pause();
			return;
		}

		if (this.board.isPaused)
			return;

		switch (keycode) {
		case LEFT:
			System.out.println("Try move left");
			this.board.tryMove(this.board.curPiece, this.board.curX - 1, this.board.curY);
			break;
		case RIGHT:
			this.board.tryMove(this.board.curPiece, this.board.curX + 1, this.board.curY);
			break;
		case DOWN:
			this.board.tryMove(this.board.curPiece.rotateRight(), this.board.curX, this.board.curY);
			break;
		case UP:
			this.board.tryMove(this.board.curPiece.rotateLeft(), this.board.curX, this.board.curY);
			break;
		case SPACE:
			this.board.dropDown();
			break;
		case D:
			this.board.oneLineDown();
			break;
                }
		super.onKeyDown(event);
	}

}