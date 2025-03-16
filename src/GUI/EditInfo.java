package GUI;

import BUS.UserBUS;
import Entity.UserEntity;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditInfo extends JPanel {
    private JTextField emailField, fullnameField;
    private UserEntity userEntity;
    private UserBUS userBUS=new UserBUS();
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public EditInfo(UserEntity userEntity) {
        this.userEntity=userEntity;
		setBackground(new Color(255, 255, 255));
        setLayout(null);

        JLabel titleLabel = new JLabel("EditInfo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        titleLabel.setBounds(484, 33, 200, 30);
        add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        emailLabel.setBounds(10, 138, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        emailField.setBounds(72, 128, 371, 46);
        add(emailField);

        JLabel fullnameLabel = new JLabel("Full Name:");
        fullnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fullnameLabel.setBounds(651, 138, 95, 25);
        add(fullnameLabel);

        fullnameField = new JTextField();
        fullnameField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fullnameField.setBounds(756, 128, 371, 46);
        add(fullnameField);
        setVisible(true);
        setSize(1150,363);
        
        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                if (!validateInput()) return;
                try {
                    userEntity.setUserFullName(fullnameField.getText());
                    userEntity.setUserEmail(emailField.getText());
                    userBUS.updateUser(userEntity);
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thành công!");
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi : " + ex.getMessage());
                }
        	}
        });
        btnSave.setBackground(new Color(255, 255, 255));
        btnSave.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnSave.setHorizontalAlignment(SwingConstants.LEFT);
        btnSave.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/save.png")));
        btnSave.setBounds(1024, 243, 101, 46);
        add(btnSave);
	}
    private boolean validateInput() {
        String fullName =fullnameField.getText().trim();
        String email = emailField.getText().trim();
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống.");
            return false;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống.");
            return false;
        }

        // Kiểm tra định dạng email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập lại.");
            return false;
        }
        return true;
    }
    private void clearFields() {
        fullnameField.setText("");
        emailField.setText("");
    }
}
