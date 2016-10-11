package PhotoLibrary;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class ContainerNode extends Node{

	public ContainerNode(Node parent) {
		super(parent);
	}

	@Override
	public Rectangle getBoundsLocal() {
		return null;
	}

	@Override
	public void paintLocal(JComponent component, Graphics graphics) {
		
	}
	
}
