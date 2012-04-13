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

    ScoreBoard playerScoreBoard;
    ScoreBoard botScoreBoard;
    PongGame game;
    Sound playerWins;

    Arbiter() {
        playerWins = assetManager().getSound("images/Pong-Playerwin"); // Player wins sound
    }

    public boolean checkForGameEnding() {
        // added JT: stop score counting and set GameState op GameOver
        if (botScoreBoard.getScore() >= PongGame.WINNING_SCORE
                || playerScoreBoard.getScore() >= PongGame.WINNING_SCORE) {
            game.stopMovingParts();
            game.setGameState(GameState.GameOver);
            game.resetBatPos();

            if (playerScoreBoard.getScore() == PongGame.WINNING_SCORE) {
                playerWins.play();
            }
            return true;
        } else {
            return false;
        }
    }
}
