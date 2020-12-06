package interactivity.server;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

public class Room
{
	private ConcurrentHashMap<String, Member> members;
	private Member streamer;
	
	public Room()
	{
		members = new ConcurrentHashMap<String, Member>();
		streamer = null;
	}
	
	public Collection<Member> getMembers()
	{
		return members.values();
	}
	
	public void addMember(Member member)
	{
		this.members.put(member.getKey(), member);
	}
	
	public void removeMember(Member member)
	{
		this.members.remove(member.getKey());
	}
	
	public Member getMember(String key)
	{
		return members.get(key);
	}
	
	public boolean isStreaming()
	{
		return streamer != null;
	}
	
	public void setStreamer(Member streamer)
	{
		this.streamer = streamer;
	}
	
	public Member getStreamer()
	{
		return streamer;
	}
	
	public void sendAll(JSONObject json)
	{
		for(Map.Entry<String, Member> entry : members.entrySet())
			entry.getValue().send(json);
	}

	@SuppressWarnings("unchecked")
	public void disconnectAll()
	{
		JSONObject json = new JSONObject();
		json.put("SERVER_CLOSED", "CLOSED");
		
		sendAll(json);
	}

	public void clearMembers()
	{
		members.clear();
	}
}
