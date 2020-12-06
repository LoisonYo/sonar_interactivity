package interactivity.gui.menu;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import interactivity.gui.InteractivityFrame;

public class MenuPanel extends JPanel
{
	private static final long serialVersionUID = 449462393919426144L;
	
	private InteractivityFrame frame;
	private JButton connectButton;
	private JButton roomButton;
	private JButton quitButton;
	
	public MenuPanel(InteractivityFrame frame)
	{
		super(new GridLayout(0, 1));
		this.frame = frame;
		
		components();
		controls();
	}
	
	private void components()
	{
		connectButton = new JButton("Connexion");
		roomButton = new JButton("Créer un salon");
		quitButton = new JButton("Quitter");
		
		this.add(connectButton);
		this.add(roomButton);
		this.add(quitButton);
	}

	private void controls()
	{
		connectButton.addActionListener(e -> frame.displayConnection());
		roomButton.addActionListener(e -> frame.displayRoom());
		quitButton.addActionListener(e -> System.exit(0));
	}
	
}
