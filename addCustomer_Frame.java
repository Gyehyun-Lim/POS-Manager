package POS;
import javax.swing.*;

public class addCustomer_Frame {
	Controller c;
	public JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("고객명");
	private JLabel birthLabel = new JLabel("생일(4자리)");
	private JLabel phoneLabel = new JLabel("연락처");
	public JTextField nameInput = new JTextField();
	public JTextField birthInput = new JTextField();
	public JTextField phoneInput = new JTextField();
	public JButton addButton = new JButton("가입신청");
	public JButton exitButton = new JButton("취소");
	
	public addCustomer_Frame(Controller c) {
		this.c = c;
		panel.setLayout(null);

		nameLabel.setBounds(20, 10, 100, 30);
		birthLabel.setBounds(20, 50, 100, 30);
		phoneLabel.setBounds(20, 90, 100, 30);
		
		nameInput.setBounds(150, 10, 80, 30);
		birthInput.setBounds(150, 50, 80, 30);
		phoneInput.setBounds(150, 90, 80, 30);
		
		addButton.setBounds(20, 130, 100, 30);
		exitButton.setBounds(150, 130, 100, 30);
		addButton.addActionListener(c);
		exitButton.addActionListener(c);
		
		panel.add(nameLabel);
		panel.add(birthLabel);
		panel.add(phoneLabel);
		panel.add(nameInput);
		panel.add(birthInput);
		panel.add(phoneInput);
		panel.add(addButton);
		panel.add(exitButton);

		frame.add(panel);		
		frame.setTitle("회원등록");
		frame.setBounds(500,400,300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
