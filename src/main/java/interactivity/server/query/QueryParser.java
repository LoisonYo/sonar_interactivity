package interactivity.server.query;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interactivity.server.Member;
import interactivity.server.Room;

public class QueryParser
{
	private Room room;
	private QuerySender querySender;
	
	public QueryParser(Room room)
	{
		this.room = room;
		this.querySender = new QuerySender(room);
	}
	
	public void parse(Member sender, String json) throws ParseException
	{
		this.parse(sender, (JSONObject) new JSONParser().parse(json));
	}

	public void parse(Member sender, JSONObject json)
	{
		for(Object key : json.keySet())
		{
			String stringKey = (String) key;
			switch(stringKey)
			{
			case "CONNECT":
				connect(sender, (String) json.get(key));
				break;
			case "DISCONNECT":
				disconnect(sender);
				break;
			case "STREAMING_REQUEST":
				streamingRequest(sender);
				break;
			case "STOP_STREAMING":
				stopStreaming(sender);
				break;
			case "CONTROL_REQUEST":
				controlRequest(sender);
				break;
			case "ACCEPT_CONTROL_REQUEST":
				acceptControlRequest((String) json.get(key));
				break;
			case "CLICK":
				click((JSONObject) json.get(key));
			}
		}
	}

	public void connect(Member sender, String username)
	{
		sender.setUsername(username);
		
		room.getMembers().stream()
			.filter(m -> m != sender)
			.forEach(m -> querySender.sendConnect(m, username));
		
		querySender.sendAllConnectedUsers(sender);
		
		if(room.isStreaming())
			querySender.sendStartStreaming(sender, room.getStreamer().getUsername());
	}
	
	public void disconnect(Member sender)
	{
		room.removeMember(sender);
		room.getMembers().stream()
			.forEach(m -> querySender.sendDisconnected(m, sender.getUsername()));
		
		if(sender.isStreaming())
			stopStreaming(sender);
	}
	
	private void streamingRequest(Member sender)
	{
		if(!room.isStreaming())
		{
			querySender.sendStreamingRequestAccepted(sender);
			room.setStreamer(sender);
			sender.setStreaming(true);
			
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			room.getMembers().stream()
				.forEach(m -> querySender.sendStartStreaming(m, sender.getUsername()));
		}
	}
	
	private void stopStreaming(Member sender)
	{
		room.setStreamer(null);
		sender.setStreaming(false);
		room.getMembers().stream()
			.forEach(m -> querySender.sendStopStreaming(m));
	}
	
	private void controlRequest(Member sender)
	{
		querySender.sendControlRequest(room.getStreamer(), sender);
	}
	
	private void acceptControlRequest(String key)
	{
		querySender.sendControlRequestAccepted(room.getMember(key));
	}
	
	private void click(JSONObject args)
	{
		int x = Integer.parseInt((String) args.get("X"));
		int y = Integer.parseInt((String) args.get("Y"));
		int button = Integer.parseInt((String) args.get("BUTTON"));
		querySender.sendClick(room.getStreamer(), x, y, button);
	}
}
