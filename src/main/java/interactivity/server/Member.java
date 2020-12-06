package interactivity.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Member
{
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private String username;
	private boolean isStreaming;
	
	public Member(Socket socket) throws IOException
	{
		this.socket = socket;
		
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public JSONObject readInput() throws IOException, ParseException
	{
		return (reader.ready()) ? (JSONObject) new JSONParser().parse(reader.readLine()) : new JSONObject();
	}

	public void send(JSONObject json)
	{
		writer.println(json.toJSONString());
	}
	
	public boolean isStreaming()
	{
		return isStreaming;
	}
	
	public void setStreaming(boolean isStreaming)
	{
		this.isStreaming = isStreaming;
	}
	
	public String getKey()
	{
		return socket.getLocalAddress().getHostAddress() + ":" + socket.getPort();
	}
}
