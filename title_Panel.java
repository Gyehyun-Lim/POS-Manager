package POS;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class title_Panel extends JPanel{
	private JLabel titleLabel;

	public JTextField Table;
	public JTextField Staff;
	Controller c;
	public title_Panel(Controller c){
		this.c = c;
		this.setLayout(null);
		titleLabel = new JLabel("식당 주문관리", SwingConstants.CENTER);
		Table = new JTextField();
		Staff = new JTextField();
		Table.setEditable(false);
		Staff.setEditable(false);
		titleLabel.setBounds(50,20,600,50);
		Table.setBounds(80, 80, 250, 30);
		Staff.setBounds(350, 80, 250, 30);

		titleLabel.setFont(new Font("SansSerif", 2, 40));
		this.setBounds(50,0,700,120);
		this.setBorder(new LineBorder(Color.BLACK, 2));
		this.add(titleLabel);
		this.add(Table);
		this.add(Staff);
	}
	
	

}