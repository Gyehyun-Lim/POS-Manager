package POS;
import javax.swing.*;

public class addMenu_Frame {
	Controller c;
	public JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("�޴���");
	private JLabel priceLabel = new JLabel("����");
	public JTextField nameInput = new JTextField();
	public JTextField priceInput = new JTextField();
	public JButton addButton = new JButton("���");
	public JButton exitButton = new JButton("���");
	
	public addMenu_Frame(Controller c) {
		this.c = c;
		panel.setLayout(null);
		nameLabel.setBounds(20, 10, 100, 30);
		priceLabel.setBounds(20, 50, 100, 30);
		nameInput.setBounds(150, 10, 120, 30);
		priceInput.setBounds(150, 50, 120, 30);
		
		addButton.setBounds(20, 90, 100, 30);
		exitButton.setBounds(150, 90, 100, 30);

		addButton.addActionListener(c);
		exitButton.addActionListener(c);
		
		panel.add(nameLabel);
		panel.add(priceLabel);
		panel.add(nameInput);
		panel.add(priceInput);
		panel.add(addButton);
		panel.add(exitButton);

		frame.add(panel);		
		frame.setTitle("�޴����");
		frame.setBounds(500,400,300, 180);
		//frame.setDefaultCloseOperation(JFrame.);
	}
}
