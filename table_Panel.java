package POS;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class table_Panel extends JPanel {
	
	Controller c;
	public JPanel table_Panel;
	public table tablelist[] = new table[20]; 
	
	public table_Panel(Controller c){
		this.c = c;
		this.setLayout(null);
		this.setBounds(0,130,400,300);
		this.setBorder(new TitledBorder("테이블 현황"));

		table_Panel = new JPanel();
		table_Panel.setBounds(25,25,350,250);
		table_Panel.setLayout(new GridLayout(4,5));
		for(int i = 0; i < 20; i++) {
			String name = Integer.toString(i + 1);
			tablelist[i] = new table(name);
			tablelist[i].tableButton.setBackground(Color.white);
			tablelist[i].currentColor = 0;
			tablelist[i].tableButton.addActionListener(c);
			table_Panel.add(tablelist[i].tableButton);
		}
		this.add(table_Panel);
	}

}
class table { 
	
	String orderList = "";
	int total_pay = 0;
	int Menu_Count[] = new int[20];
	
	public JButton tableButton;
	String name;
	int currentColor; 
	table(String name){
		this.name = name;
		tableButton = new JButton(name);
		for(int i = 0; i < 20; i++){
			Menu_Count[i] = 0;
		}
	}
	public void changeColor(){
		if(currentColor == 0) {
			this.tableButton.setBackground(Color.YELLOW);
			currentColor = 1;
		}
		else if(currentColor == 1) {
			this.tableButton.setBackground(Color.WHITE);
			currentColor = 0;
		}
	}
}





