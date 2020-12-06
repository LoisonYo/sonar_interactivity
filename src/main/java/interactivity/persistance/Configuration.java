package interactivity.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Configuration
{
	private static final String CONFIG_FILE_PATH = "./config.json";
	private static final String FFMPEG_PATH = "D:\\Utilisateurs\\Yohann\\Bureau\\HE-Arc\\Projet P3\\Workspace\\interactivity-2.0\\ffmpeg\\bin\\ffmpeg.exe";
	
	private static Configuration instance;
	
	private String username;
	private String serverIP;
	private int queryPort;
	private int videoPort;
	
	public static Configuration getInstance()
	{
		if(instance == null)
			instance = new Configuration();
		return instance;
	}
	
	private Configuration()
	{
		username = "";
		serverIP = "127.0.0.1";
		queryPort = 25565;
		videoPort = 8554;
	}
	
	public void load()
	{
		File file = new File(CONFIG_FILE_PATH);
		if(file.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String jsonString = reader.readLine();
				reader.close();
				JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
				username = (String) json.get("USERNAME");
				serverIP = (String) json.get("SERVER_IP");
				queryPort = Integer.valueOf(((String) json.get("QUERY_PORT")));
				videoPort = Integer.valueOf(((String) json.get("VIDEO_PORT")));
			}
			catch (IOException | ParseException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void save()
	{
		try
		{
			JSONObject json = new JSONObject();
			json.put("USERNAME", username);
			json.put("SERVER_IP", serverIP);
			json.put("QUERY_PORT", Integer.toString(queryPort));
			json.put("VIDEO_PORT", Integer.toString(videoPort));
			
			File file = new File(CONFIG_FILE_PATH);
			if(!file.exists())
				file.createNewFile();
			
			PrintWriter printWriter = new PrintWriter(new FileWriter(file));
			printWriter.println(json.toJSONString());
			printWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getServerIP()
	{
		return serverIP;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}
	
	public int getQueryPort()
	{
		return queryPort;
	}
	
	public void setQueryPort(int queryPort)
	{
		this.queryPort = queryPort;
	}
	
	public int getVideoPort()
	{
		return videoPort;
	}
	
	public void setVideoPort(int videoPort)
	{
		this.videoPort = videoPort;
	}
	
	public String getFFmpegPath()
	{
		return FFMPEG_PATH;
	}
}
