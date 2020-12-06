package interactivity.client.query;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interactivity.client.Member;
import interactivity.client.Room;
import interactivity.gui.viewer.StreamingButton.State;
import interactivity.gui.viewer.ViewerPanel;
import interactivity.persistance.Configuration;

public class QueryParser
{	
	private ViewerPanel panel;
	private Room room;
	
	public QueryParser(ViewerPanel panel, Room room)
	{
		this.panel = panel;
		this.room = room;
	}

	public void parse(String json) throws ParseException
	{
		this.parse((JSONObject) new JSONParser().parse(json));
	}
	
	public void parse(JSONObject json)
	{
		for(Object key : json.keySet())
		{
			String stringKey = (String) key;
			System.out.println(stringKey);
			switch(stringKey)
			{
			case "CONNECT":
				connect((String) json.get(key));
				break;
			case "DISCONNECT":
				disconnect((String) json.get(key));
				break;
			case "SERVER_CLOSED":
				serverClosed();
				break;
			case "STREAMING_REQUEST_ACCEPTED":
				streamingRequestAccepted();
				break;
			case "START_STREAMING":
				startStreaming();
				break;
			case "STOP_STREAMING":
				stopStreaming();
				break;
			case "CONTROL_REQUEST":
				controlRequest((JSONObject) json.get(key));
				break;
			case "CONTROL_REQUEST_ACCEPTED":
				controlRequestAccepted();
				break;
			case "CLICK":
				click((JSONObject) json.get(key));
				break;
			}
		}
	}

	private void connect(String username)
	{
		Member member = new Member(username);
		panel.getMembersList().addMember(member);
		room.addMember(member);
	}
	
	private void disconnect(String username)
	{
		Member member = room.getMember(username);
		panel.getMembersList().removeMember(member);
		room.removeMember(member);
	}
	
	private void serverClosed()
	{
		JOptionPane.showMessageDialog(panel, "L'hôte de la session est parti. Retour au menu.");
		panel.disconnect();
	}
	
	private void streamingRequestAccepted()
	{
		panel.getControlPanel().getStreamingButton().setState(State.STOP_STREAMING);
		try
		{
			room.getStreamingManager().start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void startStreaming()
	{
		if(!room.getStreamingManager().isStreaming())
			panel.getControlPanel().getStreamingButton().setState(State.REQUEST_CONTROL);
		panel.getPlayer().mediaPlayer().media().play("rtsp://@" + Configuration.getInstance().getServerIP() + ":8554/mystream", "");
		panel.getPlayer().repaint();
	}
	
	private void stopStreaming()
	{
		panel.getControlPanel().getStreamingButton().setState(State.STREAM_SCREEN);
		panel.getPlayer().mediaPlayer().controls().stop();
		room.getControlManager().setControlling(false);
		
		if(room.getStreamingManager().isStreaming())
			room.getStreamingManager().stop();
	}
	
	private void controlRequest(JSONObject args)
	{
		String username = (String) args.get("USERNAME");
		String key = (String) args.get("KEY");
		
		Object[] options = {"Oui", "Non"};
		int response = JOptionPane.showOptionDialog(
				panel,
				"Autorisez-vous " + username + " à prendre le contrôle de votre ordinateur ?",
				"Demande de contrôle",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
		
		if(response == 0)
			room.getQueryManager().acceptControlRequest(key);
	}
	
	private void controlRequestAccepted()
	{
		panel.getControlPanel().getStreamingButton().setState(State.STOP_CONTROL);
		room.getControlManager().setControlling(true);
	}
	


	private void click(JSONObject json)
	{
		if(room.getStreamingManager().isStreaming())
		{
			int x = Integer.valueOf((String) json.get("X"));
			int y = Integer.valueOf((String) json.get("Y"));
			int button = Integer.valueOf((String) json.get("BUTTON"));
			room.getControlManager().click(x, y, button);
		}
	}
}
