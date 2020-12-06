package interactivity.server;

import interactivity.server.query.QueryManager;
import interactivity.server.video.RTSPServer;

public class Server
{
	private static Server instance;
	
	private Room room;
	private RTSPServer rtspServer;
	private QueryManager query;
	
	public static Server getInstance()
	{
		if(instance == null)
			instance = new Server();
		return instance;
	}
	
	private Server()
	{
		room = new Room();
		rtspServer = new RTSPServer();
		query = new QueryManager(room);
	}
	
	public void start()
	{
		rtspServer.start();
		query.start();
	}
	
	public void stop()
	{
		room.disconnectAll();
		rtspServer.stop();
		query.stop();
		room.clearMembers();
	}
}
