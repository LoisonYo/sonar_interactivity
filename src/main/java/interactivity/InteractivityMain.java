package interactivity;

import interactivity.gui.InteractivityFrame;
import interactivity.persistance.Configuration;

public class InteractivityMain
{
	public static void main(String[] args)
	{
		Configuration.getInstance().load();
		
		InteractivityFrame frame = new InteractivityFrame();
		frame.setVisible(true);
	}
}
