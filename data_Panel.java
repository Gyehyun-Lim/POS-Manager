package POS;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;

import java.awt.*;
import java.sql.SQLException;

public class data_Panel extends JPanel{
	Controller c;
	int date_count = 0;
	int errorFind = 0; 
	
	public JPanel customer = new JPanel();
	public JPanel sale = new JPanel();
	public JPanel staff = new JPanel();
	public JPanel menu = new JPanel(); 
	public JTabbedPane tab = new JTabbedPane();

	public JLabel customer_name = new JLabel("고객명");
	public JTextField customer_nameInput = new JTextField();
	public JTextArea customer_data = new JTextArea();
	public JButton add_Customer = new JButton("가입");
	public JButton search_Customer = new JButton("조회");
	
	public JLabel sale_date = new JLabel("기간");
	public JComboBox<String> date_box = new JComboBox<String>();
	public JTextArea sale_data = new JTextArea();

	public JLabel staff_name = new JLabel("직원명");
	public JTextField staff_nameInput = new JTextField();
	public JTextArea staff_data = new JTextArea();
	public JButton add_Staff = new JButton("직원등록");
	public JButton search_Staff = new JButton("조회");
	
	public JLabel menu_name = new JLabel("메뉴명");
	public JTextField menu_nameInput = new JTextField();
	public JTextArea menu_data = new JTextArea();
	public JButton add_Menu = new JButton("메뉴등록");
	public JButton search_Menu = new JButton("조회");

	public data_Panel(Controller c){
		this.c = c;
		
		customer.setLayout(null);
		customer_name.setBounds(30, 30, 100, 20);
		customer_nameInput.setBounds(30,50, 100, 30);
		add_Customer.setBounds(150,50,100,30);
		search_Customer.setBounds(270, 50, 100, 30);
		customer_data.setBounds(10,100,370, 230);
		customer_data.setBorder(new LineBorder(Color.BLACK, 1));
		customer_data.setEditable(false);
		
		add_Customer.addActionListener(c); 
		search_Customer.addActionListener(c);
		customer_nameInput.addActionListener(c);

		customer.add(customer_name);
		customer.add(customer_nameInput);
		customer.add(add_Customer);
		customer.add(search_Customer);
		customer.add(customer_data);
		
		sale.setLayout(null);
		sale_date.setBounds(30, 30, 100, 20);
		date_box.setBounds(30,50, 100, 30);
		sale_data.setBounds(10,100,370, 230);
		sale_data.setBorder(new LineBorder(Color.BLACK, 1));
		sale_data.setEditable(false);
		sale.add(sale_date);
		sale.add(date_box);
		sale.add(sale_data);
		
		create_Date();
		date_box.addActionListener(c);
		
		staff.setLayout(null);
		staff_name.setBounds(30, 30, 100, 20);
		staff_nameInput.setBounds(30,50, 100, 30);
		add_Staff.setBounds(150,50,100,30);
		search_Staff.setBounds(270, 50, 100, 30);
		staff_data.setBounds(10,100,370, 230);
		staff_data.setBorder(new LineBorder(Color.BLACK, 1));
		staff_data.setEditable(false);
		
		add_Staff.addActionListener(c);
		search_Staff.addActionListener(c);
		staff_nameInput.addActionListener(c);
		
		staff.add(staff_name);
		staff.add(staff_nameInput);
		staff.add(add_Staff);
		staff.add(search_Staff);
		staff.add(staff_data);
		
		menu.setLayout(null);
		menu_name.setBounds(30, 30, 100, 20);
		menu_nameInput.setBounds(30,50, 100, 30);
		add_Menu.setBounds(150,50,100,30);
		search_Menu.setBounds(270, 50, 100, 30);
		menu_data.setBounds(10,100,370, 230);
		menu_data.setBorder(new LineBorder(Color.BLACK, 1));
		menu_data.setEditable(false);
		
		add_Menu.addActionListener(c);
		search_Menu.addActionListener(c);
		menu_nameInput.addActionListener(c);
		
		menu.add(menu_name);
		menu.add(menu_nameInput);
		menu.add(add_Menu);
		menu.add(search_Menu);
		menu.add(menu_data);
		
		this.setLayout(new BorderLayout());
		tab.setBounds(400,430,400,400);
		customer.setBounds(400,430,400,400);
		sale.setBounds(400,430,400,400);
		staff.setBounds(400,430,400,400);
		menu.setBounds(400,430,400,400);
				
		tab.addTab("고객",customer);
		tab.addTab("매출",sale);
		tab.addTab("직원",staff);
		tab.addTab("메뉴",menu);
		this.add(tab);
		this.setBounds(400,430,400,400);
		this.setBorder(new TitledBorder("등록/조회"));
	}
	public void create_Date(){
		try {
			int size = c.DB.getDate(c).size(); 
			for(int i = date_count; i < size; i++){
				date_box.addItem((String)c.DB.getDate(c).get(i));
				System.out.println(" : "+(String)c.DB.getDate(c).get(i));
				date_count++;
			}
		} catch (SQLException e) { 
			JOptionPane.showMessageDialog(null,"", "", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
		}
	}
}
