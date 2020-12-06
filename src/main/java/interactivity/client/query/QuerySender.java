package interactivity.client.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class QuerySender
{
	private PrintWriter writer;
	
	public void start(Socket socket) throws IOException
	{
		writer = new PrintWriter(socket.getOutputStream(), true);
	}
	
	@SuppressWarnings("unchecked")
	public void sendConnect(String username)
	{
		JSONObject json = new JSONObject();
		json.put("CONNECT", username);
	
		writer.println(json.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	public void sendDisconnect(String username)
	{
		JSONObject json = new JSONObject();
		json.put("DISCONNECT", username);
	
		writer.println(json.toJSONString());
	}

	@SuppressWarnings("unchecked")
	public void sendStreamingRequest()
	{
		JSONObject json = new JSONObject();
		json.put("STREAMING_REQUEST", "");
	
		writer.println(json.toJSONString());
	}

	@SuppressWarnings("unchecked")
	public void sendStopStreaming()
	{
		JSONObject json = new JSONObject();
		json.put("STOP_STREAMING", "");
	
		writer.println(json.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	public void sendControlRequest()
	{
		JSONObject json = new JSONObject();
		json.put("CONTROL_REQUEST", "");
	
		writer.println(json.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	public void sendAcceptControlRequest(String key)
	{
		JSONObject json = new JSONObject();
		json.put("ACCEPT_CONTROL_REQUEST", key);
	
		writer.println(json.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	public void sendClick(int x, int y, int button)
	{
		JSONObject args = new JSONObject();
		args.put("X", Integer.toString(x));
		args.put("Y", Integer.toString(y));
		args.put("BUTTON", Integer.toString(button));
		
		JSONObject json = new JSONObject();
		json.put("CLICK", args);
		
		writer.println(json.toJSONString());
	}
}
