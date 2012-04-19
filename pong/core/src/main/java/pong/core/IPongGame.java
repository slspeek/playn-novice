/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.core;

/**
 *
 * @author steven
 */
public interface IPongGame {

    void autoServe();

    void gameOver();

    void pauseGame();

    void resetBatPos();

    void setGameState(GameState state);

    void stopMovingParts();
    
    void increaseBotScore();
    
    void increasePlayerScore();
    
    void resetScoreBoards();
    
    void setMessage(String message);
    
}
