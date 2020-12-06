package interactivity.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interactivity.client.Room;
import interactivity.gui.InteractivityFrame;
import interactivity.gui.viewer.StreamingButton.State;
import interactivity.server.Server;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class ViewerPanel extends JPanel
{
	private static final long serialVersionUID = -2795999050459005356L;
	
	private InteractivityFrame frame;
	private EmbeddedMediaPlayerComponent player;
	private MembersList members;
	private ControlPanel control;
	private Room room;
	
	public ViewerPanel(InteractivityFrame frame)
	{
		super(new BorderLayout());
		this.frame = frame;
		
		visuals();
		components();
	}
	
	public void start()
	{
		room = new Room(this);
		
		try
		{
			room.getQueryManager().connect();
		}
		catch (UnknownHostException e)
		{
			JOptionPane.showMessageDialog(this, "Impossible de se connecter au serveur... Retour au menu.");
			disconnect();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage() + "\nRetour au menu.");
			disconnect();
		}

		control.setRoom(room);
		control.getStreamingButton().setState(State.STREAM_SCREEN);
		members.clearMembers();
	}
	
	public void disconnect()
	{
		if(room.getStreamingManager().isStreaming())
			room.getQueryManager().stopStreaming();
		
		try
		{
			room.getQueryManager().disconnect();
		}
		catch (NullPointerException e) {}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Server.getInstance().stop();
		frame.displayMenu();
	}

	public MembersList getMembersList()
	{
		return members;
	}
	
	public EmbeddedMediaPlayerComponent getPlayer()
	{
		return player;
	}
	
	public ControlPanel getControlPanel()
	{
		return control;
	}
	
	private void visuals()
	{
		this.setPreferredSize(new Dimension(1000, 500));
	}
	
	private void components()
	{
		player = new EmbeddedMediaPlayerComponent();
		members = new MembersList();
		control = new ControlPanel(this);
		
		this.add(player, BorderLayout.CENTER);
		this.add(members, BorderLayout.WEST);
		this.add(control, BorderLayout.SOUTH);
	}
}
