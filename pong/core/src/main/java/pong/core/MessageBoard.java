package pong.core;

import static playn.core.PlayN.graphics;
import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class MessageBoard {

	private CanvasLayer layer;
	private String message = "Press SPACE to start";
	private TextLayout layout;

	public MessageBoard() {
		init();
		setMessage(message);
	}
	
	public void init() {
		layer = createTextLayer();
	}
	
	public void setMessage(String message) {
		this.message = message;
		Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, 32);
		TextFormat format = new TextFormat().withFont(font).withTextColor(0xFF006600).withWrapping(200, TextFormat.Alignment.CENTER);
		layout = graphics().layoutText(message, format);
		
		float textWidth = layout.width();//* PongWorld.physUnitPerScreenUnit;
		float mainWidth = PongWorld.WIDTH;// * PongWorld.physUnitPerScreenUnit;
		float textXOffset = (mainWidth - textWidth) / 2.0f;
		layer.setScale(0.1f);
		
		layer.canvas().clear();
		layer.canvas().drawText(layout, textXOffset, PongWorld.HEIGHT/2);
	}
	
	public Layer getLayer() {
		return layer;
	}
	
	private CanvasLayer createTextLayer() {
		CanvasLayer layer = graphics().createCanvasLayer(
				(int) Math.ceil(PongWorld.WIDTH),
				(int) Math.ceil(PongWorld.HEIGHT));
		return layer;
	}

}
