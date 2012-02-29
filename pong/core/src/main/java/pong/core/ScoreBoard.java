package pong.core;

import org.jbox2d.common.Vec2;
import playn.core.Font;

/**
 * Calculates and holds the score information
 *
 * @author youssef
 *
 */
public class ScoreBoard extends TextField {

    private int score = 0;

    public ScoreBoard(Font font, Vec2 position, float width, float height, int color) {
        super(font, position, width, height, color);
        resetScore();
    }

    public void increaseScore() {
        setMessage(String.valueOf(++score));
    }
    
    public void resetScore() {
        this.score = 0;
        setMessage(String.valueOf(score));
    }
    
    public int getScore() {  // added JT
        return this.score;
    }
}
