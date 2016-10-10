package PhotoLibrary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JToolBar toolbar = new JToolBar();
	private JToggleButton toggleButton = new JToggleButton("Category switcher");
	
	//private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	public PhotoLibrary(){
		this.setLayout(new BorderLayout());
		
		this.add(menuBar, BorderLayout.PAGE_START);
		
		toggleButton.addActionListener(event -> {
			setStatusBar("Used the toggle button!");
		});
		toolbar.add(toggleButton);
		this.add(toolbar, BorderLayout.LINE_START);

		//mainPanel.add(scrollPane, BorderLayout.CENTER);
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.add(statusBar, BorderLayout.PAGE_END);
		
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
}
