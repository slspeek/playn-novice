package pong.core;

import org.jbox2d.common.Vec2;
import playn.core.Font;

/**
 * Shows all the text we have for the game-over and the Press space key
 *
 * @author youssef
 *
 */
public class MessageBoard extends TextField {

    public MessageBoard(Font font, Vec2 position, float width, float height, int color) {
        super(font, position, width, height, color);
    }
}
