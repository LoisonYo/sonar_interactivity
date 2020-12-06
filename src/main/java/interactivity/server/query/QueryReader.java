package interactivity.server.query;

import java.io.IOException;

import org.json.simple.JSONObject;

import interactivity.server.Member;
import interactivity.server.Room;

public class QueryReader
{
	private Room room;
	private boolean isRunning;
	private QueryParser parser;
	
	public QueryReader(Room room)
	{
		this.room = room;
		
		isRunning = false;
		parser = new QueryParser(room);
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
				while(isRunning)
				{
					for(Member member : room.getMembers())
					{
						try
						{
							JSONObject json = member.readInput();
							if(!json.isEmpty())
							{
								parser.parse(member, json);
							}
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						catch (org.json.simple.parser.ParseException e)
						{
							e.printStackTrace();
						}
					}
					
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
		};
	}
}
