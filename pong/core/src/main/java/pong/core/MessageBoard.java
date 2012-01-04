package pong.core;

import static playn.core.PlayN.graphics;

import java.awt.Color;

import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class MessageBoard {

	private static final float FONTSIZE = 25;
	private CanvasLayer layer;
	private String message = "Press SPACE to start";
	private TextLayout layout;
	private TextLayout fixedLayout;

	public MessageBoard() {
		Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, FONTSIZE);
		TextFormat format = new TextFormat().withFont(font).withTextColor(0xFF006600);
		fixedLayout = graphics().layoutText(message, format);
		System.out.println(fixedLayout.width() + ", " + fixedLayout.height());
		init();
		setMessage(message);
	}
	
	public void init() {
		layer = createTextLayer();
	}
	
	public void setMessage(String message) {
		this.message = message;
		Font font = graphics().createFont("Helvetica", Font.Style.PLAIN, FONTSIZE);
		TextFormat format = new TextFormat().withFont(font).withTextColor(0xFF006600);
		layout = graphics().layoutText(message, format);
				
		layer.canvas().clear();
		layer.canvas().setFillColor(0xCCCCCC);
		layer.canvas().fillRect(0, 0, fixedLayout.width(),   fixedLayout.height());
		layer.canvas().drawText(layout, 0, 0);
		layer.setTranslation(18, 12);
		layer.setScale(PongWorld.physUnitPerScreenUnit);
	}
	
	public Layer getLayer() {
		return layer;
	}
	
	private CanvasLayer createTextLayer() {
		CanvasLayer layer = graphics().createCanvasLayer(
				(int) Math.ceil(fixedLayout.width()),
				(int) Math.ceil(fixedLayout.height()));
		return layer;
	}

}
