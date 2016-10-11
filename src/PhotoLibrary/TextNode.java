package PhotoLibrary;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;


public class TextNode extends Node {
	private String text = "";
	
	TextNode(int x, int y){
		this.getTransform().setToTranslation(x, y);
	}
	TextNode(int x, int y, String text){
		this.getTransform().setToTranslation(x, y);
		this.text = text;
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
		return (int)this.getTransform().getTranslateX();
	}
	public int getY(){
		return (int)this.getTransform().getTranslateY();
	}
	
	@Override
	public void paintLocal(JComponent component, Graphics graphics) {
		// TODO Auto-generated method stub
		String text = this.getText();
		
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
						getParent().addChild(new TextNode(this.getX(), this.getY() + 20, text.substring(i, text.length())));
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
		// TODO Auto-generated method stub
		return null;
	}
}