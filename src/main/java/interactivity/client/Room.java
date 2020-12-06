package interactivity.client;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import interactivity.client.control.ControlManager;
import interactivity.client.query.QueryManager;
import interactivity.client.video.StreamingManager;
import interactivity.gui.viewer.ViewerPanel;

public class Room
{
	private QueryManager query;
	private StreamingManager streaming;
	private ConcurrentHashMap<String, Member> members;
	private ControlManager control;
	
	public Room(ViewerPanel panel)
	{
		query = new QueryManager(panel, this);	
		members = new ConcurrentHashMap<String, Member>();
		
		try
		{
			control = new ControlManager(panel, this);
			streaming = new StreamingManager();
		}
		catch (IOException | AWTException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addMember(Member member)
	{
		members.put(member.getUsername(), member);
	}
	
	public void removeMember(Member member)
	{
		members.remove(member.getUsername());
	}
	
	public Member getMember(String username)
	{
		return members.get(username);
	}
	
	public Collection<Member> getMembers()
	{
		return members.values();
	}
	
	public QueryManager getQueryManager()
	{
		return query;
	}
	
	public StreamingManager getStreamingManager()
	{
		return streaming;
	}
	
	public ControlManager getControlManager()
	{
		return control;
	}
}
