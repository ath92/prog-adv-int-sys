package PhotoLibrary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class Node {
	private Node parent;
	ArrayList<Node> children = new ArrayList<Node>();
	
	private Color fill, stroke;
	
	private AffineTransform transform = new AffineTransform();
	
	private boolean visible;
	
	private boolean selected = false;
	
	private boolean pickable = true;
	
	public Node(Node parent){
		if(parent == null) return;
		this.fill = parent.getFill();
		this.stroke = parent.getStroke();
	}
	
	public void addChild(Node node){
		node.setParent(this);
		children.add(node);
	}
	
	public boolean remove(Node n){
		boolean removed = false;
		for(Node child:children){
			if(child == n){
				children.remove(n);
				removed = true;
			} else {
				removed = child.remove(n);
			}
			if(removed) break;
		}
		return removed;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public abstract void paintLocal(JComponent component, Graphics graphics);
	
	public void paint(JComponent component, Graphics graphics){
		this.paintLocal(component, graphics);
		if(this.isSelected()){
			graphics.drawRect((int)this.getBounds().getMinX(), 
					(int)this.getBounds().getMinY(), 
					(int)this.getBounds().getWidth(), 
					(int)this.getBounds().getHeight());
		}
		if(children == null) return;
		for(Node child: (ArrayList<Node>) children.clone()){//loop through temporary clone in order to be able to change arraylist during paint
			child.paint(component, graphics);
		}
	}
	
	
	public abstract Rectangle getBoundsLocal();//transform positions by recursively 
	
	public Rectangle getBounds(){
		int minx = 0, maxx = 0, miny = 0, maxy = 0;
		Rectangle bounds = this.getBoundsLocal();
		if(bounds != null){
			minx = (int)this.getBoundsLocal().getMinX();
			maxx = (int)this.getBoundsLocal().getMaxX(); 
			miny = (int)this.getBoundsLocal().getMinY(); 
			maxy = (int)this.getBoundsLocal().getMaxY();
		} else {
			minx = Integer.MAX_VALUE;
			miny = minx;
		}
		
		for(Node node: children){
			Rectangle nodeBounds = node.getBounds();
			if(nodeBounds!=null){
				if(minx > nodeBounds.getMinX()) minx = (int)nodeBounds.getMinX();
				if(miny > nodeBounds.getMinY()) miny = (int)nodeBounds.getMinY();
				if(maxx < nodeBounds.getMaxX()) maxx = (int)nodeBounds.getMaxX();
				if(maxy < nodeBounds.getMaxY()) maxy = (int)nodeBounds.getMaxY();
			}
		}
		return new Rectangle(minx, miny, maxx-minx, maxy-miny);
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
	
	
	//picking
	public Node pick(MouseEvent e){
		Rectangle bounds = this.getBounds();
		
		System.out.println(this.getBounds().x + " " 
				+ this.getBounds().y + " "
				+ this.getBounds().getMaxX() + " "
				+ this.getBounds().getMaxY());
		
		if(this.pickable && 
				e.getX() > bounds.getMinX() && 
				e.getX() < bounds.getMaxX() && 
				e.getY() > bounds.getMinY() && 
				e.getY() < bounds.getMaxY()
				|| this.getParent() == null){//don't stop here if we're in the rootnode.
			//pick happened inside of bounds

			if(this.children.size() > 0){//not a leaf
				for(Node node:children){
					Node picked = node.pick(e);
					if(picked != null) return picked;
				}
			}
			
			//this is a leaf, or none of the children were picked and the click happened inside it -> this item should be picked.
			if(this.getParent() == null) return null; // return null if this is the rootNode, we don't really want to do anything there
			this.setSelected(true);
			return this;
		}
		return null;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isPickable() {
		return pickable;
	}

	public void setPickable(boolean pickable) {
		this.pickable = pickable;
	}
}
