package POS;
import javax.swing.*;

public class addStaff_Frame {
	Controller c;
	public JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("직원명");
	private JLabel positionLabel = new JLabel("직금");
	public JTextField nameInput = new JTextField();
	public JComboBox<String> position_Combobox = new JComboBox<String>();
	public JButton addButton = new JButton("등록");
	public JButton exitButton = new JButton("취소");
	
	public addStaff_Frame(Controller c) {
		this.c = c;
		panel.setLayout(null);

		position_Combobox.addItem("Supervisor");
		position_Combobox.addItem("Staff");

		nameLabel.setBounds(20, 10, 100, 30);
		positionLabel.setBounds(20, 50, 100, 30);
		nameInput.setBounds(150, 10, 120, 30);
		position_Combobox.setBounds(150, 50, 120, 30);
		addButton.setBounds(20, 90, 100, 30);
		exitButton.setBounds(150, 90, 100, 30);

		addButton.addActionListener(c);
		exitButton.addActionListener(c);
		position_Combobox.addActionListener(c);
		
		panel.add(nameLabel);
		panel.add(positionLabel);
		panel.add(nameInput);
		panel.add(position_Combobox);
		panel.add(addButton);
		panel.add(exitButton);
		
		frame.add(panel);
		frame.setTitle("직원등록");
		frame.setBounds(500,400,300, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
