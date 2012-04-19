/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

import playn.core.Sound;
import static playn.core.PlayN.assetManager;

/**
 *
 * @author steven
 */
public class Arbiter {

    private final ScoreBoard playerScoreBoard;
    private final ScoreBoard botScoreBoard;
    private final Sound playerWins;
    private final int WINNING_SCORE;
    private IPongGame game;

    public Arbiter(ScoreBoard playerScoreBoard, ScoreBoard botScoreBoard, int WINNING_SCORE) {
        playerWins = assetManager().getSound("images/Pong-Playerwin"); // Player wins sound
        this.playerScoreBoard = playerScoreBoard;
        this.botScoreBoard = botScoreBoard;
        this.WINNING_SCORE = WINNING_SCORE;
    }

    public void setGame(IPongGame game) {
        this.game = game;
    }
    
    public boolean checkForGameEnding() {
        if (botScoreBoard.getScore() >= WINNING_SCORE
                || playerScoreBoard.getScore() >= WINNING_SCORE) {
            game.stopMovingParts();
            game.setGameState(GameState.GameOver);
            game.resetBatPos();

            if (playerScoreBoard.getScore() == WINNING_SCORE) {
                playerWins.play();
            }
            return true;
        } else {
            return false;
        }
    }
    
     public void increaseBotScore() {
         botScoreBoard.increaseScore();
     }
    
    public void increasePlayerScore() {
        playerScoreBoard.increaseScore();
    }
    
    public void resetScoreBoards() {
        playerScoreBoard.resetScore();
        botScoreBoard.resetScore();
    }
    
}
