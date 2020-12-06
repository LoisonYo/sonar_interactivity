package interactivity.gui.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import interactivity.client.Room;

public class StreamingButton extends JButton
{
	private static final long serialVersionUID = -5766330122116654051L;
	private static final String STREAMING_SCREEN_TEXT = "Partager l'écran";
	private static final String STOP_STREAMING_TEXT = "Arrêter le partage";
	private static final String REQUEST_CONTROL_TEXT = "Demander le contrôle";
	private static final String STOP_CONTROL_TEXT = "Arrêter le contrôle";
	
	public static enum State {STREAM_SCREEN, STOP_STREAMING, REQUEST_CONTROL, STOP_CONTROL};
	
	private State state;
	private Room room;
	
	public StreamingButton()
	{
		super();
		
		controls();
		setState(State.STREAM_SCREEN);
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public void setState(State state)
	{
		this.state = state;
		changeText();
	}
	
	private void changeText()
	{
		switch(state)
		{
		case STREAM_SCREEN:
			this.setText(STREAMING_SCREEN_TEXT);
			break;
		case STOP_STREAMING:
			this.setText(STOP_STREAMING_TEXT);
			break;
		case REQUEST_CONTROL:
			this.setText(REQUEST_CONTROL_TEXT);
			break;
		case STOP_CONTROL:
			this.setText(STOP_CONTROL_TEXT);
			break;
		}
	}
	
	private void controls()
	{
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				switch (state)
				{
				case STREAM_SCREEN:
					room.getQueryManager().startStreaming();
					break;
				case STOP_STREAMING:
					room.getQueryManager().stopStreaming();
					room.getStreamingManager().stop();
					break;
				case REQUEST_CONTROL:
					room.getQueryManager().takeControl();
					break;
				case STOP_CONTROL:
					room.getControlManager().setControlling(false);
					setState(State.REQUEST_CONTROL);
					break;
				}
			}
		});
	}
}
