package POS;
import javax.swing.*;

public class menubar_Panel extends JMenuBar{
	public JMenu menu = new JMenu("Menu");
	public JMenuItem login = new JMenuItem("Log in");
	//public JMenuItem logout = new JMenuItem("Log out");
	public JMenuItem open = new JMenuItem("Open");
	
	public menubar_Panel(Controller c){
		menu.add(open);
		menu.add(login);
		//menu.add(logout);
		this.add(menu);
		login.addActionListener(c);
		//logout.addActionListener(c);
		open.addActionListener(c);
	}
}
