package interactivity.server.video;

import java.io.IOException;

public class RTSPServer
{
	private static final String APPLICATION_PATH = "D:\\Utilisateurs\\Yohann\\Bureau\\HE-Arc\\Projet P3\\RTSP Server\\rtsp-simple-server.exe";
	
	private Process process;
	
	public void start()
	{
		try
		{
			process = Runtime.getRuntime().exec(APPLICATION_PATH);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		if(process != null && process.isAlive())
			process.destroy();
	}
}
