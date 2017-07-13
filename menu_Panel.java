package POS;
import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class menu_Panel extends JPanel{
	menu menu_B[] = new menu[20];
	Controller c;
	public menu_Panel(Controller c) {
		this.c = c;
		this.setLayout(new GridLayout(10,2));
		this.setBounds(0,430,400,400);
		this.setBorder(new TitledBorder("메뉴"));
		try {
			c.DB.creatMenu(this, c);
		} 
		catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null,"정보가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);       
		    try {
		    	c.login.frame.setVisible(false);
	            c.DB.createTable(c.filechoose());
				c.DB.creatMenu(this, c);
			    c.restart();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,"메뉴 SQL", "경고", JOptionPane.WARNING_MESSAGE);
				System.out.println(e1);
			} catch (IOException e2){
				JOptionPane.showMessageDialog(null,"IOException!", "경고", JOptionPane.WARNING_MESSAGE);
			} catch (NullPointerException e3) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않아 프로그램을 종료합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		}
	}
}
class menu {
	String menu_Name;
	int menu_Price;
	JButton menu_Button;
	menu(String name) {
		menu_Button = new JButton(name);
	}
}
