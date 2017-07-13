package POS;

import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.*;

public class Controller implements ActionListener {
	String DB_username;
	String DB_password;
	DB_Connect DB;
	
	mainFrame main;
	addCustomer_Frame addCustomer;
	addStaff_Frame addStaff;
	addMenu_Frame addMenu;
	login_Frame login;
	
	table current_Table;
	String staff_name = "";
	String staff_position = "";
	
	int first = 0;
	
	String OrderList = "";
	int pay = 0;
	int Menu_Count[] = new int[20];
	
	public int Menu_Num = 0;
	
	public Controller(String DB_username, String DB_password) {
		this.DB_username = DB_username;
		this.DB_password = DB_password;
		DB = new DB_Connect(DB_username, DB_password);
		login = new login_Frame(this);
		main = new mainFrame(this);
		main.mainframe.setVisible(false);
		addCustomer = new addCustomer_Frame(this);
		addStaff = new addStaff_Frame(this);
		addMenu = new addMenu_Frame(this);
	}
	
	public void restart() {
		DB = new DB_Connect(DB_username, DB_password);
		main = new mainFrame(this);
		login = new login_Frame(this);
		addCustomer = new addCustomer_Frame(this);
		addStaff = new addStaff_Frame(this);
		addMenu = new addMenu_Frame(this);
	}
	
	public void restart1() {
		DB = new DB_Connect(DB_username, DB_password);
		main.mainframe.setVisible(true);
		main = new mainFrame(this);
		login = new login_Frame(this);
		addCustomer = new addCustomer_Frame(this);
		addStaff = new addStaff_Frame(this);
		addMenu = new addMenu_Frame(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == (Object)main.menubar.open) {
			try {
	            JOptionPane.showMessageDialog(null,"���̺��� �����մϴ�."); 
				DB.dropTable();
				first = 0;
			} catch (SQLException e4) { 
	            JOptionPane.showMessageDialog(null,"", "", JOptionPane.WARNING_MESSAGE);       
			} 
			try {
				DB.createTable(filechoose());
				main.mainframe.dispose();
			} catch (SQLException e1) {
	            JOptionPane.showMessageDialog(null,"SQL Exception", "���", JOptionPane.WARNING_MESSAGE);       
			} catch (IOException e2){
				JOptionPane.showMessageDialog(null,"IOException!", "���", JOptionPane.WARNING_MESSAGE);
			} catch (NullPointerException e3){
	            JOptionPane.showMessageDialog(null,"������ �������� �ʾҽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE); 
			} catch (NumberFormatException e4){
	            JOptionPane.showMessageDialog(null,"Number Format", "���", JOptionPane.WARNING_MESSAGE); 
	            System.exit(0);
			} catch (NoSuchElementException e5){
	            JOptionPane.showMessageDialog(null,"No Element", "���", JOptionPane.WARNING_MESSAGE); 
	            System.exit(0);
			}
			restart();
		}
		else if(e.getSource() == (Object)main.menubar.login) {
			login = new login_Frame(this);
			login.frame.setVisible(true);
			login.frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					login.frame.dispose();
				}
			});
		}
		else if(e.getSource() == (Object)login.loginButton) {
			String name = login.nameInput.getText();
			String id = new String(login.idInput.getPassword());
			if(name.equals("") || id.equals(""))
				JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");
			else {
				try {
					DB.login(name, id, this);
					if(!staff_name.equals("")) {
						if(first == 0) {
							main.mainframe.setVisible(true);
							first = 1;
						}
						login.frame.dispose();
					}
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "������ ���̽� ���ῡ �����߽��ϴ�.");
				}
			}
			
		}
		else if(e.getSource() == (Object)login.exitButton) {
			if(first == 0)
				System.exit(0);
			else
				login.frame.dispose();
		}
		
		//order
		else if(e.getSource() == (Object)main.order.table_Num) {
			String num = (String) main.order.table_Num.getSelectedItem();
			current_Table = main.table.tablelist[Integer.parseInt(num) - 1];
			if(current_Table.total_pay != 0)
				main.order.order_area.setText(current_Table.orderList + "\n�� �հ� : " + current_Table.total_pay + "��");
			else
				main.order.order_area.setText("");
			main.title.Table.setText("���� ���̺� �� : " + current_Table.name + "");
		}
		else if(e.getSource() == (Object)main.order.order) {
			try {
				if(pay == 0) {
					JOptionPane.showMessageDialog(null,"�޴��� �������ּ���.", "���", JOptionPane.WARNING_MESSAGE);
				}
				else {
					current_Table.changeColor();
					current_Table.orderList += OrderList;
					current_Table.total_pay += pay;
					OrderList = "";
					pay = 0;
					for(int i = 0; i < 20; i++) {
						current_Table.Menu_Count[i] += Menu_Count[i];
						Menu_Count[i] = 0;
					}
					main.order.order_area.setText(current_Table.orderList + "\n�� �հ� : " + current_Table.total_pay + "��");
					JOptionPane.showMessageDialog(null, "�ֹ��� �Ϸ�Ǿ����ϴ�.");
				}
			}
			catch(NullPointerException n) {
				JOptionPane.showMessageDialog(null, "���̺��� �������ּ���.");
			}
		}
		else if(e.getSource() == (Object)main.order.cancel) {
			try {
				if(current_Table.currentColor == 1) {
					if(current_Table.total_pay != 0) {
						current_Table.changeColor();
						current_Table.orderList = "";
						current_Table.total_pay = 0;
						OrderList = "";
						pay = 0;
						for(int i = 0; i < 20; i++) {
							current_Table.Menu_Count[i] = 0;
							Menu_Count[i] = 0;
						}
						main.order.order_area.setText("");
						JOptionPane.showMessageDialog(null, "�ֹ��� ��ҵǾ����ϴ�.");
					}
				}
				else
					JOptionPane.showMessageDialog(null, "�ֹ��� ���� ���ּ���.");
			}
			catch(NullPointerException n) {
				JOptionPane.showMessageDialog(null, "���̺��� �������ּ���.");
			}
		}
		else if(e.getSource() == (Object)main.order.pay) {
			try{
				if(current_Table.currentColor == 1){
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				    String date = fm.format(new Date());
					String customer_name = main.order.customer_nameInput.getText();
					int total_pay = current_Table.total_pay;
					String Rank;
					
					try {
						if(!customer_name.equals("") && DB.search_cus(customer_name) == false)
							JOptionPane.showMessageDialog(null, "�ش� ȸ�������� �����ϴ�.");
						else {
							Rank = DB.search_Rank(customer_name);
							if(Rank.equals("Bronze")){
								total_pay = (int)(total_pay * 0.9);
							} else if(Rank.equals("Silver")){
								total_pay = (int)(total_pay * 0.8);
							} else if(Rank.equals("Gold")){
								total_pay = (int)(total_pay * 0.7);
							}
							
							DB.UpdateDB1(staff_name, customer_name, total_pay, this);
							System.out.println("a");
							for(int i = 0; i < 20; i++){
								for(int j = 0; j < current_Table.Menu_Count[i]; j++){
									DB.UpdateDB2(date, main.menu.menu_B[i].menu_Name, main.menu.menu_B[i].menu_Price, this);
									System.out.println(i);
								}
								current_Table.Menu_Count[i] = 0;
							}
							OrderList = "";
							pay = 0;
							current_Table.orderList = "";
							current_Table.total_pay = 0;
							main.order.order_area.setText("");
							main.data.create_Date();
							current_Table.changeColor();
							main.order.customer_nameInput.setText("");
							JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
						}
					} catch (SQLException e1) {
			            JOptionPane.showMessageDialog(null,"������ �����߽��ϴ�."); 
			            System.out.println(e1);
					}
				}
				else 
					 JOptionPane.showMessageDialog(null,"�ֹ��� ���� ���ּ���.");
			} catch (NullPointerException n){
	            JOptionPane.showMessageDialog(null,"���̺��� �������ּ���.");       	
			}
		}
		
		else if(e.getSource() == (Object)main.data.add_Customer) {
			if(staff_position.equals("Supervisor")) {
				addCustomer = new addCustomer_Frame(this);
				addCustomer.frame.setVisible(true);
				addCustomer.frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						addCustomer.frame.dispose();
					}
				});
			}
			else
				JOptionPane.showMessageDialog(null,"Supervisor�� �����մϴ�.");  
		}
		else if(e.getSource() == (Object)addCustomer.addButton) {
			String name = addCustomer.nameInput.getText();
			String birth = addCustomer.birthInput.getText();
			String phone = addCustomer.phoneInput.getText();
			if(name.equals("") || birth.equals("") || phone.equals(""))
				JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");
			else if(birth.length() != 4)
				JOptionPane.showMessageDialog(null, "������ 4�ڸ��� �Է����ּ���.");
			else {
				try {
					int cus_birth = Integer.parseInt(birth);
					int int_phone = Integer.parseInt(phone);
					DB.add_Customer(name, cus_birth, phone);
				}catch(NumberFormatException e0){
					JOptionPane.showMessageDialog(null, "���ϰ� ����ó�� ���ڸ� �Է����ּ���.");
				}
				catch(SQLException e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, "�� ������ �����߽��ϴ�.");
				}
				addCustomer.frame.dispose();
			}
		}
		else if(e.getSource() == (Object)addCustomer.exitButton) {
			addCustomer.frame.dispose();
		}
		else if(e.getSource() == (Object)main.data.search_Customer) {
			String name = main.data.customer_nameInput.getText();
			main.data.customer_nameInput.setText("");
			try {
				DB.search_Customer(name, main.data);
			}
			catch(SQLException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(null, "�� �˻��� �����߽��ϴ�.");
			}
		}
		
		else if(e.getSource() == (Object)main.data.add_Staff) {
			if(staff_position.equals("Supervisor")) {
				addStaff = new addStaff_Frame(this);
				addStaff.frame.setVisible(true);
				addStaff.frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						addStaff.frame.dispose();
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(null,"Supervisor�� �����մϴ�.");  
			}
		}
		else if(e.getSource() == (Object)addStaff.addButton) {
			String name = addStaff.nameInput.getText();
			String position = (String)addStaff.position_Combobox.getSelectedItem();
			if(name.equals(""))
				JOptionPane.showMessageDialog(null, "���� �̸��� �Է����ּ���.");
			else {
				try {
					DB.add_Staff(name, position);
				} 
				catch(SQLException e1) {
					JOptionPane.showConfirmDialog(null, "���� ����� �����߽��ϴ�.");
				}
				addStaff.frame.dispose();
			}
		}
		else if(e.getSource() == (Object)addStaff.exitButton) {
			addStaff.frame.dispose();
		}
		else if(e.getSource() == (Object)main.data.search_Staff) {
			String name = main.data.staff_nameInput.getText();
			main.data.staff_nameInput.setText("");
			try {
				DB.search_Staff(name, main.data);
			}
			catch(SQLException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(null, "���� �˻��� �����߽��ϴ�.");
			}
		}
		
		else if(e.getSource() == (Object)main.data.add_Menu) {
			if(staff_position.equals("Supervisor")) {
				addMenu = new addMenu_Frame(this);
				addMenu.frame.setVisible(true);
				addMenu.frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						addMenu.frame.dispose();
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(null,"Supervisor�� �����մϴ�.");  
			}
		}
		else if(e.getSource() == (Object)addMenu.addButton) {
			String name = addMenu.nameInput.getText();
			String price = addMenu.priceInput.getText();
			if(name.equals("") || price.equals("")) 
				JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");
			else {
				try {
					int menu_price = Integer.parseInt(price);
					DB.add_Menu(name, menu_price, this, Menu_Num);
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "�޴� ����� �����߽��ϴ�.");
				}
				try {
					DB.creatMenu(main.menu, this);
					main.menu.revalidate();
					main.menu.repaint();
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "�޴� ��ư �߰��� �����߽��ϴ�.");
					
				}
				addMenu.frame.dispose();
			}
		}
		else if(e.getSource() == (Object)addMenu.exitButton) {
			addMenu.frame.dispose();
		}
		else if(e.getSource() == (Object)main.data.search_Menu) {
			String name = main.data.menu_nameInput.getText();
			main.data.menu_nameInput.setText("");
			try {
				DB.search_Menu(name, main.data);
			}
			catch(SQLException e1) {
				JOptionPane.showMessageDialog(null,"�޴� �˻��� �����߽��ϴ�.");  
			}
		}
		else if(e.getSource() == (Object)main.data.date_box) {
			if(staff_position.equals("Supervisor")) {
				String date = (String) main.data.date_box.getSelectedItem();
				System.out.println("date : " + date);
				try {
					DB.show_Sale(date, main.data);
					System.out.println("a");
				}
				catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "���� ��Ȳ �ҷ����⸦ �����߽��ϴ�.");
					System.out.println(e1);
				}
			}
			else
				JOptionPane.showMessageDialog(null, "Supervisor�� �����մϴ�.");
		}
		
		for(int i = 0; i < 20; i++) {
			if(e.getSource() == (Object)main.table.tablelist[i].tableButton) {
				OrderList = "";
				main.order.table_Num.setSelectedIndex(i);
				for(int j = 0; j < 20; j++) {
					Menu_Count[j] = 0;
				}
				current_Table = main.table.tablelist[i];
				if(current_Table.total_pay != 0)
					main.order.order_area.setText(current_Table.orderList + "\n�� �հ� : " + current_Table.total_pay + "��");
				else
					main.order.order_area.setText("");
				main.title.Table.setText("���� ���̺�� : " + current_Table.name + "��");
			}
		}
		
		for(int i = 0; i < Menu_Num; i++) {
			if(e.getSource() == (Object)main.menu.menu_B[i].menu_Button) {
				try {
					OrderList = "";
					OrderList += main.menu.menu_B[i].menu_Name + " : " + main.menu.menu_B[i].menu_Price + "\n";
					pay += main.menu.menu_B[i].menu_Price;
					Menu_Count[i]++;
					main.order.order_area.setText(current_Table.orderList + "\n============�ӽ� �ֹ�����=============\n" + OrderList + "�� �հ� : " + (current_Table.total_pay + pay) + "��" );
				}
				catch(NullPointerException n1) {
					JOptionPane.showMessageDialog(null, "���̺��� �������ּ���.");
				}
			}
		}
	}
	
	public File filechoose() {
		File file = null;
		JFileChooser c = new JFileChooser();
		//c.setFileFilter(f);
		int temp = c.showOpenDialog(null);
		if(temp == JFileChooser.APPROVE_OPTION)
			file = c.getSelectedFile();
		return file;
	}
	
	public static void main(String[] args) {
		new Controller("system", "system");
	}
}
