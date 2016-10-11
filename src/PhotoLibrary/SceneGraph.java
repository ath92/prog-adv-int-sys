package PhotoLibrary;

import java.awt.Graphics;
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
	
	public void removeNode(Node node){
		removeHierarchical(rootNode, node);
	}
	
	private void removeHierarchical(Node start, Node toBeRemoved){
		ArrayList<Node> children = start.getChildren();
		for(Node child: children){//first loop through children to check if node is part of children
			if(child == toBeRemoved) {
				start.removeChild(toBeRemoved);
				return;
			}
		}
		//if node not found, loop through children again to check their children. Double loop to prevent going unnecessarily deep.
		for(Node child: children){
			this.removeHierarchical(child, toBeRemoved);
		}
		
	}
	
}
