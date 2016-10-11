package PhotoLibrary;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class StrokeNode extends Node{
	
	public StrokeNode(Node parent, int x, int y) {
		super(parent);
		this.getTransform().setToTranslation(x, y);
	}

	@Override //Only add lines here.
	public void addChild(Node node){
		if(node instanceof LineNode){
			this.addLine((LineNode)node);
		}
	}
	
	public void addLine(LineNode line){
		line.setParent(this);
		this.children.add(line);
	}

	@Override
	public void paintLocal(JComponent component, Graphics graphics) {
		//just draw all children.
		for(Node line: children){
			line.paint(component, graphics);
		}
		
	}

	@Override
	public Rectangle getBoundsLocal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
