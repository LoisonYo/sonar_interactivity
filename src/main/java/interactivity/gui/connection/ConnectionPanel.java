package interactivity.gui.connection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import interactivity.gui.InteractivityFrame;
import interactivity.persistance.Configuration;

public class ConnectionPanel extends JPanel
{
	private static final long serialVersionUID = 6343313814109004027L;
	
	private InteractivityFrame frame;
	private JTextField username;
	private JTextField serverIP;
	private JTextField queryPort;
	private JTextField videoPort;
	private JButton connect;
	private JButton back;

	public ConnectionPanel(InteractivityFrame frame)
	{
		super(new GridBagLayout());
		this.frame = frame;
		
		components();
		controls();
	}
	
	private void components()
	{		
		username = new JTextField(Configuration.getInstance().getUsername());
		serverIP = new JTextField(Configuration.getInstance().getServerIP());
		queryPort = new JTextField(Integer.toString(Configuration.getInstance().getQueryPort()));
		videoPort = new JTextField(Integer.toString(Configuration.getInstance().getVideoPort()));
		connect = new JButton("Se connecter");
		back = new JButton("Retour");
		
		queryPort.setEditable(false);
		videoPort.setEditable(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		JPanel user = new JPanel(new GridLayout(0, 2));
		user.setBorder(new TitledBorder("Utilisateur"));
		user.add(new JLabel("Nom d'utilisateur : "), 0);
		user.add(username, 1);
		this.add(user, c);
		
		c.gridy = 1;
		JPanel server = new JPanel(new GridLayout(0, 2));
		server.setBorder(new TitledBorder("Serveur"));
		server.add(new JLabel("Addresse IP : "), 0);
		server.add(serverIP, 1);
		server.add(new JLabel("Port requêtes : "), 2);
		server.add(queryPort, 3);
		server.add(new JLabel("Port vidéo : "), 4);
		server.add(videoPort, 5);
		this.add(server, c);
		
		c.gridy = 2;
		JPanel controls = new JPanel(new GridLayout(0, 2));
		controls.add(back);
		controls.add(connect);
		this.add(controls, c);
	}
	
	private void controls()
	{
		back.addActionListener(e -> frame.displayMenu());
		connect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Configuration.getInstance().setUsername(username.getText());
				Configuration.getInstance().setServerIP(serverIP.getText());
				Configuration.getInstance().save();
				frame.displayViewer();
			}
		});
	}
}

