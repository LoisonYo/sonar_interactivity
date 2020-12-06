package interactivity.gui.viewer;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import interactivity.client.Room;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = 2072665177740813849L;
	
	private ViewerPanel panel;
	private JButton disconnect;
	private StreamingButton stream;
	
	public ControlPanel(ViewerPanel panel)
	{
		super(new GridLayout(1, 0));
		this.panel = panel;
		
		components();
		controls();
	}
	
	public void setRoom(Room room)
	{
		stream.setRoom(room);
	}
	
	public void components()
	{
		disconnect = new JButton("Quitter");
		stream = new StreamingButton();
		
		this.add(disconnect);
		this.add(stream);
	}
	
	public void controls()
	{
		disconnect.addActionListener((e) -> panel.disconnect());
	}
	
	public StreamingButton getStreamingButton()
	{
		return stream;
	}
}
