package pong.core;

import static playn.core.PlayN.graphics;
import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class ScoreBoard {

        private CanvasLayer layer;
        private String scoreString = "000";
        private TextLayout layout;
        private int score = 0;

        public ScoreBoard() {
                init();
                setScore(String.valueOf(score));
        }
        
        public void init() {
                layer = createTextLayer();
        }
        
        public void increaseScore() {
                setScore(String.valueOf(++score));
                
        }
        
        private void setScore(String score) {
                this.scoreString = score;
                Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 32);
                TextFormat format = new TextFormat().withFont(font).withTextColor(0xFF006600);
                layout = graphics().layoutText(score, format);
                
                layer.canvas().clear();
                layer.canvas().drawText(layout, 0, 0);
                
                float textWidth = layout.width() * PongWorld.physUnitPerScreenUnit;                
                float mainWidth = PongWorld.WIDTH;
                float textXOffset = (mainWidth - textWidth) / 2.0f -2.0f;  // --2.0f to center aprox... (JT)
                
                layer.setOrigin(0,00);
                layer.setScale(0.1f);
                
                layer.setTranslation(textXOffset, 0);
        }
        
        public Layer getLayer() {
                return layer;
        }
        
        protected static CanvasLayer createTextLayer() {
                CanvasLayer layer = graphics().createCanvasLayer(
                                (int) Math.ceil(PongWorld.WIDTH)+18, // Added by to fit text  JT
                                (int) Math.ceil(PongWorld.HEIGHT));
                return layer;
        }

}
