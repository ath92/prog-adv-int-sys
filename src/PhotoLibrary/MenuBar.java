package PhotoLibrary;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{

	private JMenu fileMenu, viewMenu;
	private PhotoLibrary photoLibrary;
	
	public MenuBar(PhotoLibrary p){
		super();
		photoLibrary = p;
		
		//file menu
		fileMenu = new JMenu("file");
		
		JMenuItem importItem = new JMenuItem("Import");
		importItem.addActionListener( event -> handleImportItem(event) );
		fileMenu.add(importItem);

		JMenuItem deletItem = new JMenuItem("Delete");
		deletItem.addActionListener( event -> handleDeleteItem(event) );
		fileMenu.add(deletItem);

		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener( event -> handleQuitItem(event) );
		fileMenu.add(quitItem);
		
		this.add(fileMenu);
		
		//view menu item
		viewMenu = new JMenu("view");
		
		JMenuItem photoViewerItem = new JMenuItem("Photo Viewer");
		photoViewerItem.addActionListener( event -> handlePhotoViewerItem(event) );
		viewMenu.add(photoViewerItem);

		JMenuItem browserItem = new JMenuItem("Browser");
		browserItem.addActionListener( event -> handleBrowserItem(event) );
		viewMenu.add(browserItem);

		JMenuItem splitViewItem = new JMenuItem("Split View");
		splitViewItem.addActionListener( event -> handleSplitViewItem(event) );
		viewMenu.add(splitViewItem);
		
		this.add(viewMenu);
	}
	
	private void handleImportItem(ActionEvent event){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		photoLibrary.setStatusBar("Import item");
	}

	private void handleDeleteItem(ActionEvent event){
		photoLibrary.setStatusBar("Delete item");
	}

	private void handleQuitItem(ActionEvent event){
		photoLibrary.setStatusBar("Quit");
	}

	private void handlePhotoViewerItem(ActionEvent event){
		photoLibrary.setStatusBar("View Photo Viewer");
	}
	
	private void handleBrowserItem(ActionEvent event){
		photoLibrary.setStatusBar("View Photo Browser");
	}

	private void handleSplitViewItem(ActionEvent event){
		photoLibrary.setStatusBar("Split View");
	}
}
