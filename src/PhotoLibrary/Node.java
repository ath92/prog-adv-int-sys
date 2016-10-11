package PhotoLibrary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class Node {
	private Node parent;
	ArrayList<Node> children = new ArrayList<Node>();
	
	private Color fill, stroke;
	
	private AffineTransform transform = new AffineTransform();
	
	boolean visible;
	
	public Node(Node parent){
		if(parent == null) return;
		this.fill = parent.getFill();
		this.stroke = parent.getStroke();
	}
	
	public void addChild(Node node){
		node.setParent(this);
		children.add(node);
	}
	
	public void removeChild(Node node){
		children.remove(node);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public abstract void paintLocal(JComponent component, Graphics graphics);
	
	public void paint(JComponent component, Graphics graphics){
		this.paintLocal(component, graphics);
		if(children == null) return;
		for(Node child: (ArrayList<Node>) children.clone()){//loop through temporary clone in order to be able to change arraylist during paint
			child.paint(component, graphics);
		}
	}
	
	
	public abstract Rectangle getBoundsLocal();//transform positions by recursively 
	
	public Rectangle getBounds(){
		int minx = 0, maxx = 0, miny = 0, maxy = 0;
		ArrayList<Node> childrenAndCurrent = (ArrayList<Node>)children.clone();
		childrenAndCurrent.add(this);
		for(Node node: childrenAndCurrent){
			Rectangle nodeBounds = node.getBounds();
			if(nodeBounds!=null){
				if(minx > nodeBounds.x) minx = nodeBounds.x;
				if(miny > nodeBounds.y) miny = nodeBounds.y;
				if(maxx > nodeBounds.x + nodeBounds.width) maxx = nodeBounds.x + nodeBounds.width;
				if(maxy > nodeBounds.y + nodeBounds.height) maxy = nodeBounds.y + nodeBounds.height;
			}
		}
		return new Rectangle(minx, miny, maxx, maxy);
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public Color getStroke() {
		return stroke;
	}

	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}

	public AffineTransform getFinalTransform() {
		if(this.parent != null){
			AffineTransform thisTransform = (AffineTransform)getParent().getFinalTransform().clone();
			thisTransform.concatenate(this.transform);
			return thisTransform;
		} else {
			return this.getTransform();
		}
	}
	
	public AffineTransform getTransform(){
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
