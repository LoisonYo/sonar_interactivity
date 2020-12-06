package interactivity.server.query;

import interactivity.server.Room;

public class QueryManager
{
	private QueryConnectionListener listener;
	private QueryReader reader;
	
	public QueryManager(Room room)
	{		
		listener = new QueryConnectionListener(room);
		reader = new QueryReader(room);
	}
	
	public void start()
	{
		listener.start();
		reader.start();
	}
	
	public void stop()
	{
		listener.stop();
		reader.stop();
	}	
}
