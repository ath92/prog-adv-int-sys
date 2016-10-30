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
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class PhotoComponent extends JComponent implements MouseListener, MouseMotionListener, KeyListener{
	Image photo;
	private boolean flipped = false;
	//size
	Dimension preferredSize = new Dimension(300,300);
	
	int oldX, oldY;
	
	ArrayList<ArrayList<Line2D.Float>> strokes = new ArrayList<ArrayList<Line2D.Float>>();
	
	ContainerNode sceneGraph = new ContainerNode(null);
	
	TextNode currentTextNode;
	StrokeNode currentStrokeNode;
	
	Node picked = null;
	
	private boolean category1, category2;
	
	public PhotoComponent(File file){
		super();
		this.setImage(file);
		this.setPreferredSize(preferredSize);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		Random rand = new Random();
		//set pseudo-categories. Let's just have them both be mutually exclusive.
		if(rand.nextInt()%2 == 0)category1 = true;
		category2 = !category1;
	}
	

    public void mouseClicked(MouseEvent e) {
    	if(e.getClickCount()==1 && flipped && picked == null){
    		//drawStrings.add(new DrawString(e.getX(), e.getY()));
    		ContainerNode textBox = new ContainerNode(sceneGraph);
    		currentTextNode = new TextNode(this.getGraphics(), e.getX(), e.getY(), textBox);
    		textBox.addChild(currentTextNode);
    		sceneGraph.addChild(textBox);
    	}
        if(e.getClickCount()==2){
            flipped = !flipped;
        }
    }
    
    public void mousePressed(MouseEvent e) {
    	picked = sceneGraph.pick(e);
    	requestFocus();
    	if(flipped && picked == null){
	        oldX = e.getX();
	        oldY = e.getY();
	    	currentStrokeNode = new StrokeNode(sceneGraph, oldX, oldY);
	    	currentStrokeNode.setStroke(Color.BLACK);
	    	sceneGraph.addChild(currentStrokeNode);
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
	        
	        if(picked!=null){
	        	AffineTransform currentPosition = (AffineTransform)picked.getTransform().clone();
	        	currentPosition.translate(currentX - oldX, currentY - oldY);
	        	picked.setTransform(currentPosition);
	        } else {
		        AffineTransform strokeTransform = currentStrokeNode.getTransform();
		        currentStrokeNode.addLine(new LineNode(currentStrokeNode, 
		        		oldX - (int)strokeTransform.getTranslateX(), 
		        		oldY - (int)strokeTransform.getTranslateY(), 
		        		currentX - (int)strokeTransform.getTranslateX(), 
		        		currentY - (int)strokeTransform.getTranslateY()));
	        }
	        
	        
	        
            oldX = currentX;
            oldY = currentY;
    	}
    }
    @Override
	public void keyPressed(KeyEvent e){
		if(flipped && currentTextNode != null){
			currentTextNode.addText(e.getKeyChar());
			System.out.println(e.getKeyChar());
		}
	}
	

    public Dimension getPreferredSize() {
        if (photo == null) {
            return new Dimension(100, 100);
        } else {
            return new Dimension(photo.getWidth(null), photo.getHeight(null));
        }
    }
    
    public void setCurrentTextNode(TextNode textNode){
    	currentTextNode = textNode;
    }
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, (int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight());
		if(!flipped){
			g.drawImage(this.photo,  0, 0, null);
		} else {
			sceneGraph.paint(this, g);
			
			
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
	
	public Image getPhoto(){
		return photo;
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
	
	public boolean isCategory1(){
		return category1;
	}
	

	public boolean isCategory2(){
		return category2;
	}

}
