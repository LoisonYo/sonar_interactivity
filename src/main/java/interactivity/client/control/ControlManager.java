package interactivity.client.control;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;

import interactivity.client.Room;
import interactivity.gui.viewer.ViewerPanel;

public class ControlManager
{
	private ViewerPanel panel;
	private Room room;
	private boolean isControlling;
	private Robot robot;
	
	public ControlManager(ViewerPanel panel, Room room) throws AWTException
	{
		this.panel = panel;
		this.room = room;
		
		robot = new Robot();
		
		controls();
	}
	
	public boolean isControlling()
	{
		return isControlling;
	}
	
	public void setControlling(boolean isControlling)
	{
		this.isControlling = isControlling;
	}

	public void click(int x, int y, int button)
	{
		int mask;
		switch(button)
		{
		case 1:
			mask = InputEvent.BUTTON1_DOWN_MASK;
			break;
		case 3:
			mask = InputEvent.BUTTON3_DOWN_MASK;
			break;
		default:
			mask = -1;
		}
		
		System.out.println(mask);
		if(mask > 0)
		{
			System.out.println("salut");
			robot.mouseMove(x, y);
			robot.mousePress(mask);
			robot.mouseRelease(mask);
		}
	}
	
	private void controls()
	{
		panel.getPlayer().videoSurfaceComponent().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				onClick(e);		
			}
		});
	}
	
	private void onClick(MouseEvent e)
	{		
		if(isControlling)
		{
			Dimension remoteScreenDimension = panel.getPlayer().mediaPlayer().video().videoDimension();
			float horizontalRatio = (float) panel.getPlayer().getWidth() / remoteScreenDimension.width;
			float verticalRatio = (float)  panel.getPlayer().getHeight() / remoteScreenDimension.height;
			
			float ratio = (horizontalRatio < verticalRatio) ? horizontalRatio : verticalRatio;
			Rectangle rect = new Rectangle();
			rect.width = (int) (remoteScreenDimension.width * ratio);
			rect.height = (int) (remoteScreenDimension.height * ratio);
			rect.x = (panel.getPlayer().getWidth() / 2) - (rect.width / 2);
			rect.y = (panel.getPlayer().getHeight() / 2) - (rect.height / 2);
			
			if(rect.contains(e.getPoint()))
			{
				int x = (int) ((e.getX() - rect.x) / ratio);
				int y = (int) ((e.getY() - rect.y) / ratio);
				room.getQueryManager().click(x, y, e.getButton());
			}
		}
	}
}
