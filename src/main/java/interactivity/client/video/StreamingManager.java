package interactivity.client.video;

import java.io.IOException;

public class StreamingManager
{
	private ProcessBuilder processBuilder;
	private Process process;
	
	public StreamingManager() throws IOException
	{
		processBuilder = new ProcessBuilder(getCommand());
	}
	
	public void start() throws IOException
	{
		process = processBuilder.start();
	}
	
	public void stop()
	{
		if(process != null && process.isAlive())
			process.destroy();
	}
	
	public boolean isStreaming()
	{
		return (process == null) ? false : process.isAlive();
	}
	
	private String getCommand()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("./stream.bat");
		
		return builder.toString();
	}
}
