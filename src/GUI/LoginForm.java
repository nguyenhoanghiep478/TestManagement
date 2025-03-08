package GUI;
import BUS.UserBUS;
import Entity.UserEntity;

import java.awt.Color;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginForm extends JFrame{
	 private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JButton loginButton, registerButton;
		private UserBUS userBUS=new UserBUS();
	    public LoginForm() {
	        setTitle("Register Form");
	        setSize(423, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(255, 255, 255));
	        panel.setLayout(null);

	        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
	        titleLabel.setBounds(108, 10, 200, 30);
	        panel.add(titleLabel);

	        JLabel usernameLabel = new JLabel("Username:");
	        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        usernameLabel.setBounds(20, 50, 80, 25);
	        panel.add(usernameLabel);

	        usernameField = new JTextField();
	        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        usernameField.setBounds(20, 85, 371, 46);
	        panel.add(usernameField);

	        JLabel passwordLabel = new JLabel("Password:");
	        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        passwordLabel.setBounds(20, 141, 80, 25);
	        panel.add(passwordLabel);

	        passwordField = new JPasswordField();
	        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        passwordField.setBounds(20, 176, 371, 46);
	        panel.add(passwordField);

	        loginButton = new JButton("Login");
	        loginButton.setForeground(new Color(255, 255, 255));
	        loginButton.setBounds(20, 245, 371, 46);
	        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
	        loginButton.setBackground(new Color(0, 200, 0)); // Đặt màu nền xanh lá
	        loginButton.setOpaque(true);  // Cần thiết để hiển thị màu trên một số Look & Feel
	        loginButton.setBorderPainted(false); // Loại bỏ viền để nhìn đẹp hơn
	        loginButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					try {
						if(!userBUS.login(usernameField.getText(),new String(passwordField.getPassword()))){
							JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng!");
							return ;
						}
						JOptionPane.showMessageDialog(null, "Đăng nhập thành công ");
						dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Lỗi : " + ex.getMessage());
					}
	            }
	        });
	        panel.add(loginButton);

	        registerButton = new JButton("Register");
	        registerButton.setForeground(new Color(0, 200, 0));
	        registerButton.setHorizontalAlignment(SwingConstants.LEFT);
	        registerButton.setBounds(210, 305, 120, 31);
	        registerButton.setFont(new Font("Tahoma", Font.BOLD, 18));
	        registerButton.setContentAreaFilled(false);
			registerButton.setBorderPainted(false);
	        registerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new RegisterForm(); // Mở form đăng ký
					dispose(); // Đóng form đăng nhập hiện tại

				}

	        });
	        panel.add(registerButton);

	        getContentPane().add(panel);

	        JLabel lblAlreadyHaveAccount = new JLabel("Don't have account?");
	        lblAlreadyHaveAccount.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        lblAlreadyHaveAccount.setBounds(61, 308, 157, 25);
	        panel.add(lblAlreadyHaveAccount);
	        setVisible(true);
	    }

	    public static void main(String[] args) {
	        new LoginForm();
	    }
}
