package PhotoLibrary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class PhotoLibrary extends JFrame{
	private MenuBar menuBar = new MenuBar(this);
	private JLabel statusBar = new JLabel("hoi");
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JToolBar toolbar = new JToolBar(null, JToolBar.VERTICAL);
	private JToggleButton toggleButton1 = new JToggleButton("Category 1");
	private JToggleButton toggleButton2 = new JToggleButton("Category 2");
	
	private ArrayList<PhotoComponent> photos = new ArrayList<PhotoComponent>();
	
	private boolean browserView = true, photoView = false, splitView = false;
	
	private int currentPhotoId;
	
	//pseudo-categories.
	private boolean category1 = true, category2 = true;
	
	//private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	public PhotoLibrary(){
		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.PAGE_START);
		
		toggleButton1.addActionListener(event -> {
			setStatusBar("Used the first toggle button!");
			category1 = !category1;
			refreshCurrentView();
		});
		toggleButton2.addActionListener(event -> {
			setStatusBar("Used the second toggle button!");
			category2 = !category2;
			refreshCurrentView();
		});
		toolbar.add(toggleButton1);
		toolbar.add(toggleButton2);
		this.add(toolbar, BorderLayout.LINE_START);

		//mainPanel.add(scrollPane, BorderLayout.CENTER);
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.add(statusBar, BorderLayout.PAGE_END);
		
	}
	
	public void clear(){
		this.mainPanel.removeAll();
		this.repaint();
	}
	
	public void removePhoto(int id){
		if(id >= 0 && id < photos.size()){
			//photos.remove(id);
		}
	}
	
	public void switchToPhotoView(int id){
		if(id >= 0 && id < photos.size() && photos.size() > 0){
			currentPhotoId = id;
			photoView = true;
			browserView = false;
			splitView = false;
			clear();
			this.mainPanel.add(new JScrollPane(photos.get(id), 
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS ));
			this.revalidate();
			this.repaint();
		}
	}
	

	public void switchToSplitView(int id){
		if(id >= 0 && id < photos.size() && photos.size() > 0){
			currentPhotoId = id;
			photoView = false;
			browserView = false;
			splitView = true;
			clear();
			this.createBrowser(true);
			
			this.mainPanel.add(new JScrollPane(photos.get(id), 
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS ));
			this.revalidate();
			this.repaint();
		}
	}
	
	public void switchToBrowserView(){
		if(photos.size() > 0){
			System.out.println("browser");
			photoView = false;
			browserView = true;
			splitView = false;
			clear();
			
			createBrowser(false);
		}
	}
	
	private void createBrowser(boolean split){

		
		//add split view ui
		DefaultListModel<ImageIcon> listModel = new DefaultListModel<ImageIcon>();
		for(PhotoComponent photo:photos){
			if(category1 && photo.isCategory1() || category2 && photo.isCategory2()){
				Image image = photo.getPhoto();
				ImageIcon icon = new ImageIcon(image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH));
				listModel.addElement(icon);
			}
		}
		
		JList<ImageIcon> list = new JList<ImageIcon>(listModel);

		//handle mouse on list
		list.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evt) {
		        JList<PhotoComponent> list = (JList<PhotoComponent>)evt.getSource();
		        if (evt.getClickCount() == 2) { // double click only
		            int index = list.locationToIndex(evt.getPoint());
		            if(splitView){
		            	switchToSplitView(index);
		            } else {
		            	switchToPhotoView(index);
		            }
		        } else if(evt.getClickCount() == 1){
		        	currentPhotoId = list.locationToIndex(evt.getPoint());
		        }
		    }
		});
		
	    list.setVisibleRowCount(-1);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		if(!split){
			this.mainPanel.add(list);
		} else {
			this.mainPanel.add(list, BorderLayout.LINE_END);
		}
		this.revalidate();
		this.repaint();
	}
	
	private void refreshCurrentView(){
		if(splitView){
			switchToSplitView(currentPhotoId);
		} else if(photoView){
			switchToPhotoView(currentPhotoId);
		} else {
			switchToBrowserView();
		}
	}
	
	public void addPhoto(PhotoComponent photoComponent){
		this.photos.add(photoComponent);
	}
	
	public void removePhoto(PhotoComponent photoComponent){
		this.photos.remove(photoComponent);
	}
	
	public ArrayList<PhotoComponent> getPhotos(){
		return photos;
	}
	
	public void setStatusBar(String newValue){
		statusBar.setText(newValue);
	}
	
	public JPanel getMain(){
		return mainPanel;
	}


	public static void main(String[] args){		
		PhotoLibrary photoLibrary = new PhotoLibrary();
		photoLibrary.setSize(500,500);
		photoLibrary.setVisible(true);
	}

	public int getCurrentPhotoId() {
		return currentPhotoId;
	}
}
