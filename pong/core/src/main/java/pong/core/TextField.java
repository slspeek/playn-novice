package pong.core;

import static playn.core.PlayN.graphics;

import java.awt.Color;
import org.jbox2d.common.Vec2;

import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

/**
 * Displays message in CanvasLayer
 *
 */
public class TextField {

    private final Font font;
    private final Vec2 position;
    private final float width;
    private final float height;
    private final int color;
    private CanvasLayer layer;
    private String message;
    private TextLayout layout;

    public TextField(Font font, Vec2 position, float width, float height, int color) {
        this.font = font;
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
        init();
    }

    private void init() {
        this.layer = createTextLayer();
    }

    public void setMessage(String message) {
        this.message = message;
        TextFormat format = new TextFormat().withFont(font).withTextColor(color)
                .withWrapping(width/PongWorld.physUnitPerScreenUnit, TextFormat.Alignment.CENTER);
        layout = graphics().layoutText(message, format);
        layer.canvas().clear();
        layer.canvas().setFillColor(0xFFFFFFFF);
        //layer.canvas().fillRect(0, 0, width/PongWorld.physUnitPerScreenUnit, height/PongWorld.physUnitPerScreenUnit);
        layer.canvas().drawText(layout, 0, 0);
    }

    public Layer getLayer() {
        return layer;
    }

    private CanvasLayer createTextLayer() {
        layer = graphics().createCanvasLayer(
                (int) Math.ceil(width/PongWorld.physUnitPerScreenUnit),
                (int) Math.ceil(height/PongWorld.physUnitPerScreenUnit));
        layer.setTranslation(position.x, position.y);
        
        layer.setScale(PongWorld.physUnitPerScreenUnit);
        return layer;
    }
}
