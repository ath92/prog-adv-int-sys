package PhotoLibrary;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class PhotoComponent extends JComponent implements MouseListener, MouseMotionListener, KeyListener{
	Image photo;
	private boolean flipped = false;
	//size
	Dimension preferredSize = new Dimension(300,300);
	
	int oldX, oldY;
	
	ArrayList<ArrayList<Line2D.Float>> strokes = new ArrayList<ArrayList<Line2D.Float>>();
	
	private class DrawString{
		private int x, y;
		private String text = "";
		
		DrawString(int x, int y){
			this.x = x;
			this.y = y;
		}
		DrawString(int x, int y, String text){
			this.x = x;
			this.y = y;
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
			return x;
		}
		public int getY(){
			return y;
		}
	}
	
	ArrayList<DrawString> drawStrings = new ArrayList<DrawString>();
	
	public PhotoComponent(File file){
		super();
		this.setImage(file);
		this.setPreferredSize(preferredSize);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	

    public void mouseClicked(MouseEvent e) {
    	if(e.getClickCount()==1 && flipped){
    		drawStrings.add(new DrawString(e.getX(), e.getY()));
    	}
        if(e.getClickCount()==2){
            flipped = !flipped;
        }
    }
    
    public void mousePressed(MouseEvent e) {
    	requestFocus();
    	if(strokes.isEmpty() || strokes.get(strokes.size()-1).size() != 0){
            oldX = e.getX();
            oldY = e.getY();
    		strokes.add(new ArrayList<Line2D.Float>());
    	}
    }
    
    public void mouseMoved(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
    }
    
    public void mouseDragged(MouseEvent e) {
    	if(flipped){
	        int currentX = e.getX();
	        int currentY = e.getY();
	        ArrayList<Line2D.Float> currentStroke = strokes.get(strokes.size()-1);
	        currentStroke.add(new Line2D.Float(oldX, oldY, currentX, currentY));
            oldX = currentX;
            oldY = currentY;
    	}
    }
    @Override
	public void keyPressed(KeyEvent e){
		if(flipped){
			if(!drawStrings.isEmpty()){
				drawStrings.get(drawStrings.size() -1 ).addText(e.getKeyChar());
				System.out.println(e.getKeyChar());
			}
		}
	}
	

    public Dimension getPreferredSize() {
        if (photo == null) {
            return new Dimension(100, 100);
        } else {
            return new Dimension(photo.getWidth(null), photo.getHeight(null));
        }
    }
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, (int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight());
		if(!flipped){
			g.drawImage(this.photo,  0, 0, null);
		} else {
			//do drawing stuff
			g.setColor(Color.black);
			for(ArrayList<Line2D.Float> stroke: strokes){
				for(Line2D.Float strokeSegment: stroke){
					g.drawLine((int)strokeSegment.x1, (int)strokeSegment.y1, (int)strokeSegment.x2, (int)strokeSegment.y2);
				}
				
			}
			
			//text
			ArrayList<DrawString> currentDrawStrings = (ArrayList<DrawString>)drawStrings.clone();
			for(DrawString drawString: currentDrawStrings){
				String text = drawString.getText();
				
				if(g.getFontMetrics().stringWidth(text) < (int)this.getPreferredSize().getWidth() - drawString.getX()){
					//string small enough to draw on the screen.
					g.drawString(text, drawString.getX(), drawString.getY());
				} else {
					boolean wordWrap = true;
					for(int i = text.length() - 1; i > 0; i--){//loop through string backwards
						if(text.charAt(i) == ' ' || !wordWrap){
							String currentText = text.substring(0, i);
							if(g.getFontMetrics().stringWidth(currentText) < (int)this.getPreferredSize().getWidth() - drawString.getX()){
								//string small enough to draw on the screen.
								g.drawString(currentText, drawString.getX(), drawString.getY());
								drawString.setText(currentText);
								if(wordWrap) i++; // remove the space
								drawStrings.add(new DrawString(drawString.getX(), drawString.getY() + 20, text.substring(i, text.length())));
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
			
		}
		revalidate();
		repaint();
	}
	
	private void setImage(File file){
		try{
            photo = ImageIO.read(file);
		} catch (IOException e){
			System.out.println(e);
		}
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
