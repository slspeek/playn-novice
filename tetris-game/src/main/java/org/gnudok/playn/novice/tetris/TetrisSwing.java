package org.gnudok.playn.novice.tetris;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;


public class TetrisSwing extends JFrame {

	public TetrisSwing() {
    	TetrisView view = new TetrisViewSwingImpl();
    	getContentPane().add(view.asCompenent(), BorderLayout.CENTER);
    //add(, BorderLayout.CENTER);
        Board board = new Board(view);
        addKeyListener(board.listener);
        board.start();

        setSize(200, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

      public static void main(String[] args) {
        TetrisSwing game = new TetrisSwing();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    } 
}