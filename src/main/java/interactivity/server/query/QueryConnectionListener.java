package interactivity.server.query;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import interactivity.server.Member;
import interactivity.server.Room;

public class QueryConnectionListener
{
	private static final int TIMEOUT = 1000;
	
	private boolean isRunning;
	private Room room;
	
	public QueryConnectionListener(Room room)
	{
		this.room = room;
		
		isRunning = false;
	}
	
	public void start()
	{
		if(isRunning)
			return;
		
		isRunning = true;
		new Thread(getRunnable()).start();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	private Runnable getRunnable()
	{
		return new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					ServerSocket server = new ServerSocket(25565);
					server.setSoTimeout(TIMEOUT);
					while(isRunning)
					{
						try
						{
							Socket newClient = server.accept();
							room.addMember(new Member(newClient));
						}
						catch(SocketTimeoutException e)
						{
							//Rien
						}
						
					}
					server.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
	}
}
