package interactivity.client.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import interactivity.client.Room;
import interactivity.gui.viewer.ViewerPanel;

public class QueryReader
{
	private boolean isRunning;
	private QueryParser parser;
	
	public QueryReader(ViewerPanel panel, Room room)
	{
		isRunning = false;
		parser = new QueryParser(panel, room);
	}
	
	public void start(Socket socket)
	{
		if(isRunning)
			return;
		
		isRunning = true;
		new Thread(getRunnable(socket)).start();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	private Runnable getRunnable(Socket socket)
	{
		return new Runnable()
		{	
			@Override
			public void run()
			{
				try
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while(isRunning)
					{
						try
						{
							if(reader.ready())
							{
								String jsonString = reader.readLine();
								parser.parse(jsonString);
							}
							else
							{
								try
								{
									Thread.sleep(100);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
							}
						}
						catch (ParseException e)
						{
							e.printStackTrace();
						}
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
	}
}
