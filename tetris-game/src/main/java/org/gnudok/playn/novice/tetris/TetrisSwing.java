package org.gnudok.playn.novice.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class TetrisSwing extends JFrame {

    JLabel statusbar;


    public TetrisSwing() {

    	TetrisView view = new TetrisViewSwingImpl();
        statusbar = new JLabel(" 0");
        getContentPane().add(statusbar, BorderLayout.SOUTH);
        Board board = new Board(view);
       addKeyListener(board.listener);
        getContentPane().add(view.asCompenent());
        board.start();

        setSize(200, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   public JLabel getStatusBar() {
       return statusbar;
   }

    public static void main(String[] args) {

        TetrisSwing game = new TetrisSwing();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    } 
}