package POS;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
public class order_Panel extends JPanel{
	Controller c;
	public JTextArea order_area = new JTextArea();
	public JTextField customer_nameInput = new JTextField();
	public JLabel customer_name = new JLabel("고객명");
	public JLabel table_number = new JLabel("테이블명");
	public JButton order = new JButton("주문");
	public JButton cancel = new JButton("취소");
	public JButton pay = new JButton("걸제");
	public JComboBox<String> table_Num = new JComboBox<String>();
	
	public order_Panel(Controller c) {
		this.c = c;
		this.setLayout(null);
		this.setBounds(400,130,400,300);
		this.setBorder(new TitledBorder("주문내역"));
		
		order_area.setBorder(new LineBorder(Color.BLACK, 1));
		order_area.setEditable(false);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(order_area);
		scroll.setBounds(10,30,280,250);
		this.add(scroll);

		customer_name.setBounds(300, 30,100,30);
		this.add(customer_name);
		
		customer_nameInput.setBounds(300, 60, 90, 30);
		this.add(customer_nameInput);

		table_number.setBounds(300, 100, 100, 30);
		this.add(table_number);

		table_Num.setBounds(300, 130, 90, 30);
		for(int i = 0; i < 20; i++)
			table_Num.addItem(Integer.toString(i+1));

		this.add(table_Num);
		table_Num.addActionListener(c);

		order.setBounds(300, 170, 90, 30);
		this.add(order);
		order.addActionListener(c);
		
		cancel.setBounds(300, 210, 90, 30);
		this.add(cancel);
		cancel.addActionListener(c);
		
		pay.setBounds(300, 250, 90, 30);
		this.add(pay);
		pay.addActionListener(c);
	}
}
