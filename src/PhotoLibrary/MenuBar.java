package PhotoLibrary;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
			    "Image files", ImageIO.getReaderFileSuffixes());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(imageFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = fileChooser.showOpenDialog(this);
		if(returnValue == JFileChooser.APPROVE_OPTION){
			 PhotoComponent photo = new PhotoComponent(fileChooser.getSelectedFile());
			 photoLibrary.addPhoto(photo);
			 photoLibrary.switchToPhotoView(photoLibrary.getPhotos().size() - 1);
		}
		photoLibrary.setStatusBar("Import item");
	}

	private void handleDeleteItem(ActionEvent event){
		photoLibrary.clear();
		photoLibrary.removePhoto(photoLibrary.getCurrentPhotoId());
		photoLibrary.setStatusBar("Delete item");
	}

	private void handleQuitItem(ActionEvent event){
		System.exit(0);
		photoLibrary.setStatusBar("Quit");
	}

	private void handlePhotoViewerItem(ActionEvent event){
		photoLibrary.switchToPhotoView(photoLibrary.getCurrentPhotoId());
		photoLibrary.setStatusBar("View Photo Viewer");
	}
	
	private void handleBrowserItem(ActionEvent event){
		photoLibrary.switchToBrowserView();
		photoLibrary.setStatusBar("View Photo Browser");
	}

	private void handleSplitViewItem(ActionEvent event){
		photoLibrary.switchToSplitView(photoLibrary.getCurrentPhotoId());
		photoLibrary.setStatusBar("Split View");
	}
}
