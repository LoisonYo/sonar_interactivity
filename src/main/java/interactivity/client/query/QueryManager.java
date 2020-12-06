package interactivity.client.query;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import interactivity.client.Room;
import interactivity.gui.viewer.ViewerPanel;
import interactivity.persistance.Configuration;

public class QueryManager
{
	private Socket socket;
	private QuerySender sender;
	private QueryReader reader;
	
	public QueryManager(ViewerPanel panel, Room room)
	{
		reader = new QueryReader(panel, room);
		sender = new QuerySender();
	}
	
	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(Configuration.getInstance().getServerIP(), 25565);
		reader.start(socket);
		sender.start(socket);
		
		sender.sendConnect(Configuration.getInstance().getUsername());
	}
	
	public void disconnect() throws IOException
	{
		sender.sendDisconnect(Configuration.getInstance().getUsername());
		reader.stop();
		socket.close();
	}

	public void startStreaming()
	{
		sender.sendStreamingRequest();
	}
	
	public void stopStreaming()
	{
		sender.sendStopStreaming();
	}
	
	public void takeControl()
	{
		sender.sendControlRequest();
	}

	public void acceptControlRequest(String key)
	{
		sender.sendAcceptControlRequest(key);
	}
	
	public void click(int x, int y, int button)
	{
		sender.sendClick(x, y, button);
	}
}
