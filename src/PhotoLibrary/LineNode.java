package PhotoLibrary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class LineNode extends Node{
	
	private int x1, y1, x2, y2;

	public LineNode(Node parent, int x1, int y1, int x2, int y2) {
		super(parent);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.setPickable(false);
	}

	@Override
	public void paintLocal(JComponent component, Graphics graphics) {
		graphics.setColor(this.getStroke());
		if(isSelected()){
			graphics.setColor(Color.red);
		}
		AffineTransform transform = this.getFinalTransform();
		graphics.drawLine(
				(int)transform.getTranslateX() + x1,
				(int)transform.getTranslateY() + y1,
				(int)transform.getTranslateX() + x2,
				(int)transform.getTranslateY() + y2
				);
		

	}

	@Override
	public Rectangle getBoundsLocal() {
		AffineTransform transform = this.getFinalTransform();
		
		return new Rectangle(
				(int)transform.getTranslateX() + x1,
				(int)transform.getTranslateY() + y1,
				(int) x2 - x1,
				(int) y2 - y1);
	}

}
