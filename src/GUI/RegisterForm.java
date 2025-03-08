package GUI;

import BUS.UserBUS;
import Entity.UserEntity;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class RegisterForm extends JFrame {

    private JTextField usernameField, emailField, fullnameField;
    private JPasswordField passwordField;
    private JButton registerButton, loginButton;
    private UserBUS userBUS=new UserBUS();
    public RegisterForm() {
        setTitle("Register Form");
        setSize(432, 569);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(null);
        
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        titleLabel.setBounds(111, 10, 200, 30);
        panel.add(titleLabel);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        usernameLabel.setBounds(20, 50, 114, 25);
        panel.add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        usernameField.setBounds(20, 85, 371, 46);
        panel.add(usernameField);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        emailLabel.setBounds(20, 138, 80, 25);
        panel.add(emailLabel);
        
        emailField = new JTextField();
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        emailField.setBounds(20, 173, 371, 46);
        panel.add(emailField);
        
        JLabel fullnameLabel = new JLabel("Full Name:");
        fullnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fullnameLabel.setBounds(20, 229, 95, 25);
        panel.add(fullnameLabel);
        
        fullnameField = new JTextField();
        fullnameField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fullnameField.setBounds(20, 260, 371, 46);
        panel.add(fullnameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        passwordLabel.setBounds(20, 316, 80, 25);
        panel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        passwordField.setBounds(20, 346, 371, 46);
        panel.add(passwordField);
        
        registerButton = new JButton("Register");
        registerButton.setForeground(Color.WHITE);
        registerButton.setBounds(20, 421, 371, 40);
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        registerButton.setBackground(new Color(0, 200, 0));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateInput()) return;
                try {
                    if(userBUS.isUserExist(usernameField.getText())){
                        JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại");
                        return;
                    }
                    UserEntity user = new UserEntity(0,usernameField.getText(), usernameField.getText(),"12345678", fullnameField.getText(), 0);
                    userBUS.createUser(user);
                    JOptionPane.showMessageDialog(null, "Tạo tài khoản thành công!");
                    new LoginForm();
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi : " + ex.getMessage());
                }
            }
        });
        panel.add(registerButton);
        
        loginButton = new JButton("Login ");
        loginButton.setForeground(new Color(0, 200, 0));
        loginButton.setHorizontalAlignment(SwingConstants.LEFT);
        loginButton.setBounds(255, 471, 83, 31);
        loginButton.setContentAreaFilled(false);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm();
                dispose();
            }
        });
        panel.add(loginButton);
        
        JLabel lblAlreadyHaveAccount = new JLabel("Already have an account?");
        lblAlreadyHaveAccount.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblAlreadyHaveAccount.setBounds(65, 474, 200, 25);
        panel.add(lblAlreadyHaveAccount);
        
        getContentPane().add(panel);
        setVisible(true);
    }
    private boolean validateInput() {
        String userName =usernameField.getText().trim();
        String email = emailField.getText().trim();
        String fullName = fullnameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được để trống.");
            return false;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống.");
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập lại.");
            return false;
        }
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống.");
            return false;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(RegisterForm.this, "Password must be at least 6 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    public static void main(String[] args) {
        new RegisterForm();
    }

}
