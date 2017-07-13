package POS;
import javax.swing.*;

public class login_Frame {
	Controller c;
	public JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("이름");
	private JLabel idLabel = new JLabel("사원번호");
	public JTextField nameInput = new JTextField();
	public JPasswordField idInput = new JPasswordField();
	public JButton loginButton = new JButton("로그인");
	public JButton exitButton = new JButton("취소");
	
	public login_Frame(Controller c) {
		this.c = c;
		panel.setLayout(null);
		nameLabel.setBounds(20, 10, 60, 30);
		idLabel.setBounds(20, 50, 60, 30);
		nameInput.setBounds(100, 10, 80, 30);
		idInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 10, 80, 30);
		loginButton.addActionListener(c);
		exitButton.setBounds(200, 60, 80, 30);
		exitButton.addActionListener(c);
		
		panel.add(nameLabel);
		panel.add(idLabel);
		panel.add(nameInput);
		panel.add(idInput);
		panel.add(loginButton);
		panel.add(exitButton);

		frame.add(panel);
		frame.setTitle("사원 로그인");
		frame.setBounds(500,400,320, 130);
		frame.setVisible(true);
	}
}
