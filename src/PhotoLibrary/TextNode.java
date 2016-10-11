package PhotoLibrary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;


public class TextNode extends Node {
	private String text = "";
	private int lineHeight = 20;
	private Graphics graphics;
	
	TextNode(Graphics g, int x, int y, Node parent){
		super(parent);
		this.getTransform().setToTranslation(x, y);
		this.graphics = g;
	}
	TextNode(Graphics g, int x, int y, String text, Node parent){
		super(parent);
		this.getTransform().setToTranslation(x, y);
		this.text = text;
		this.graphics = g;
	}
	
	public void addText(char text){
		this.text += text;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public int getX(){
		return (int)this.getFinalTransform().getTranslateX();
	}
	public int getY(){
		return (int)this.getFinalTransform().getTranslateY();
	}
	
	@Override
	public void paintLocal(JComponent component, Graphics graphics) {
		String text = this.getText();
		graphics.setColor(Color.black);
		if(graphics.getFontMetrics().stringWidth(text) < (int)component.getPreferredSize().getWidth() - this.getX()){
			//string small enough to draw on the screen.
			graphics.drawString(text, this.getX(), this.getY());
		} else {
			boolean wordWrap = true;
			for(int i = text.length() - 1; i > 0; i--){//loop through string backwards
				if(text.charAt(i) == ' ' || !wordWrap){
					String currentText = text.substring(0, i);
					if(graphics.getFontMetrics().stringWidth(currentText) < (int)component.getPreferredSize().getWidth() - this.getX()){
						//string small enough to draw on the screen.
						graphics.drawString(currentText, this.getX(), this.getY());
						this.setText(currentText);
						if(wordWrap) i++; // remove the space
						TextNode newTextNode = new TextNode(this.graphics, this.getX(), this.getY() + lineHeight, text.substring(i, text.length()), this.getParent());
						getParent().addChild(newTextNode);
						if(component instanceof PhotoComponent){
							((PhotoComponent)component).setCurrentTextNode(newTextNode);
						}
						break;
					}
				}
				if(wordWrap && i == 1){
					wordWrap = false;
					i = text.length()-1;//no appropriate spaces found, restart loop without word wrapping
				}
			}
			
		}
		
	}
	@Override
	public Rectangle getBoundsLocal() {
		return new Rectangle(this.getX(), this.getY(), this.getX() + this.graphics.getFontMetrics().stringWidth(text), this.getY() + lineHeight);
	}
}