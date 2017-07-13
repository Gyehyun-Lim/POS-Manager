package POS;
import javax.swing.*;


public class mainFrame {
	Controller c;
	public JFrame mainframe = new JFrame();
	public menubar_Panel menubar;
	public title_Panel title;
	public table_Panel table;
	public order_Panel order;
	public menu_Panel menu;
	public data_Panel data;
	public mainFrame(Controller c){
		this.c = c;
		mainframe.setLayout(null);

		menubar = new menubar_Panel(c);
		title = new title_Panel(c);
		table = new table_Panel(c);
		order = new order_Panel(c);
		menu = new menu_Panel(c);
		data = new data_Panel(c);
		
		mainframe.setJMenuBar(menubar);
		mainframe.add(title);
		mainframe.add(table);
		mainframe.add(order);
		mainframe.add(menu);
		mainframe.add(data);
		
		mainframe.setTitle("식당 관리 시스템");
		mainframe.setBounds(300, 0, 820, 900);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

