package interactivity.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import interactivity.client.Member;

public class MembersList extends JPanel
{
	private static final long serialVersionUID = -7594313536892786825L;
	
	private DefaultListModel<String> listModel;
	private JList<String> list;
	
	public MembersList()
	{
		super(new BorderLayout());
		
		visuals();
		components();
	}
	
	public void addMember(Member member)
	{
		listModel.addElement(member.getUsername());
	}
	
	public void removeMember(Member member)
	{
		listModel.removeElement(member.getUsername());
	}
	
	public void clearMembers()
	{
		listModel.clear();
	}
	
	private void visuals()
	{
		this.setPreferredSize(new Dimension(100, 100));
	}
	
	private void components()
	{
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		this.add(list);
	}
}
