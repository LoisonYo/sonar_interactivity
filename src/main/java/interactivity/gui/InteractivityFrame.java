package interactivity.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import interactivity.gui.connection.ConnectionPanel;
import interactivity.gui.menu.MenuPanel;
import interactivity.gui.room.RoomPanel;
import interactivity.gui.viewer.ViewerPanel;

public class InteractivityFrame extends JFrame
{
	private static final long serialVersionUID = 1343677045006877281L;
	private static final String FRAME_TEXT = "Interactivity 2.0";
	
	private JPanel panel;
	private MenuPanel menu;
	private ConnectionPanel connection;
	private RoomPanel room;
	private ViewerPanel viewer;

	public InteractivityFrame()
	{
		super(FRAME_TEXT);
		
		menu = new MenuPanel(this);
		connection = new ConnectionPanel(this);
		room = new RoomPanel(this);
		viewer = new ViewerPanel(this);
		
		visuals();
		components();
		displayMenu();
	}
	
	public void displayMenu()
	{
		changePanel(menu);
	}
	
	public void displayConnection()
	{
		changePanel(connection);
	}
	
	public void displayRoom()
	{
		changePanel(room);
	}
	
	public void displayViewer()
	{
		changePanel(viewer);
		viewer.start();
	}
	
	private void visuals()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void components()
	{
		panel = new JPanel(new BorderLayout());
		
		this.add(panel);
	}

	private void changePanel(JPanel panel2)
	{
		panel.removeAll();
		
		panel.add(panel2);
		
		this.pack();
		this.repaint();
	}
}
