package PhotoLibrary;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;

public class SceneGraph {
	private Node rootNode = new ContainerNode(null);
	private Node currentNode;//last added node for handling purposes. No idea if this is good design.

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(ContainerNode rootNode) {
		this.rootNode = rootNode;
	}
	
	public void paint(JComponent component, Graphics graphics){
		this.rootNode.paint(component, graphics);
	}
	
	public void addChild(Node node){
		rootNode.addChild(node);
		currentNode = node;
	}

	public Node getCurrentNode() {
		return currentNode;
	}
	
	public boolean pick(MouseEvent e){
		
		return false;
	}
}
