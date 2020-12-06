package interactivity.server.query;

import org.json.simple.JSONObject;

import interactivity.server.Member;
import interactivity.server.Room;

public class QuerySender
{
	private Room room;
	
	public QuerySender(Room room)
	{
		this.room = room;
	}
	
	@SuppressWarnings("unchecked")
	public void sendConnect(Member receiver, String username)
	{
		JSONObject json = new JSONObject();
		json.put("CONNECT", username);
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendDisconnected(Member receiver, String username)
	{
		JSONObject json = new JSONObject();
		json.put("DISCONNECT", username);
		
		receiver.send(json);
	}
	
	public void sendAllConnectedUsers(Member receiver)
	{
		room.getMembers().stream()
			.map(Member::getUsername)
			.forEach(u -> sendConnect(receiver, u));
	}
	
	@SuppressWarnings("unchecked")
	public void sendStreamingRequestAccepted(Member receiver)
	{
		JSONObject json = new JSONObject();
		json.put("STREAMING_REQUEST_ACCEPTED", "");
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendStartStreaming(Member receiver, String username)
	{
		JSONObject json = new JSONObject();
		json.put("START_STREAMING", username);
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendStopStreaming(Member receiver)
	{
		JSONObject json = new JSONObject();
		json.put("STOP_STREAMING", "");
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendControlRequest(Member receiver, Member requester)
	{
		JSONObject args = new JSONObject();
		args.put("USERNAME", requester.getUsername());
		args.put("KEY", requester.getKey());
		
		JSONObject json = new JSONObject();
		json.put("CONTROL_REQUEST", args);
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendControlRequestAccepted(Member receiver)
	{
		JSONObject json = new JSONObject();
		json.put("CONTROL_REQUEST_ACCEPTED", "");
		
		receiver.send(json);
	}
	
	@SuppressWarnings("unchecked")
	public void sendClick(Member receiver, int x, int y, int button)
	{
		JSONObject args = new JSONObject();
		args.put("X", Integer.toString(x));
		args.put("Y", Integer.toString(y));
		args.put("BUTTON", Integer.toString(button));
		
		JSONObject json = new JSONObject();
		json.put("CLICK", args);
		
		receiver.send(json);
	}
}
