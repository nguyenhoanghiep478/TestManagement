package GUI;

import BUS.UserBUS;
import Entity.UserEntity;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;

public class UserForm extends JFrame {
    private JTextField txtEmail, txtFullName;
    private JComboBox<String> cbIsAdmin;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private JTextField UserName;
    private final UserBUS userBUS=new UserBUS();
    public UserForm() {
    	getContentPane().setBackground(new Color(255, 255, 255));
    	getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
        setTitle("Quản lý Người Dùng");
        setSize(842, 631);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 59, 798, 253);
        getContentPane().add(scrollPane);


        model = new DefaultTableModel(new String[]{"Id", "Name", "Email","FullName","Role"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        table.setRowHeight(20);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        
        JPanel panelForm = new JPanel();
        panelForm.setBackground(new Color(255, 255, 255));
        panelForm.setLayout(null);
        panelForm.setBounds(10, 322, 802, 262);
        getContentPane().add(panelForm);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblEmail.setBounds(10, 89, 51, 25);
        panelForm.add(lblEmail);
        
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtEmail.setBounds(71, 83, 234, 46);
        panelForm.add(txtEmail);
        

        
        txtFullName = new JTextField(20);
        txtFullName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtFullName.setBounds(419, 10, 234, 46);
        panelForm.add(txtFullName);
        
        JLabel lblIsAdmin = new JLabel("Role:");
        lblIsAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblIsAdmin.setBounds(329, 89, 43, 25);
        panelForm.add(lblIsAdmin);
        
        cbIsAdmin = new JComboBox<>(new String[]{"User", "Admin"});
        cbIsAdmin.setBackground(new Color(255, 255, 255));
        cbIsAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbIsAdmin.setBounds(419, 78, 112, 46);



        panelForm.add(cbIsAdmin);
        
        JLabel lblUsername = new JLabel("Name:");
        lblUsername.setBounds(10, 20, 74, 25);
        panelForm.add(lblUsername);
        lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        UserName = new JTextField(20);
        UserName.setBounds(71, 10, 234, 46);
        panelForm.add(UserName);
        UserName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(255, 255, 255));
        btnAdd.setBounds(677, 10, 121, 46);
        panelForm.add(btnAdd);
        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
        btnAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));
        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        btnUpdate = new JButton("Sửa");
        btnUpdate.setBackground(new Color(255, 255, 255));
        btnUpdate.setBounds(677, 81, 121, 46);
        panelForm.add(btnUpdate);
        btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
        btnUpdate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/edit.png")));
        btnUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(255, 255, 255));
        btnDelete.setBounds(677, 147, 121, 46);
        panelForm.add(btnDelete);
        btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
        btnDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/delete.png")));
        btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        JLabel lblFullName = new JLabel("FullName:");
        lblFullName.setBounds(329, 19, 80, 25);
        panelForm.add(lblFullName);
        lblFullName.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        btnDelete.addActionListener(e -> deleteUser());
        btnUpdate.addActionListener(e -> updateUser());
        addTableSelectionListener();
        btnAdd.addActionListener(e -> addUser());
        loadTableData();
        JLabel lblQunLNgi = new JLabel("Quản lí người dùng");
        lblQunLNgi.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblQunLNgi.setBounds(274, 10, 259, 40);
        getContentPane().add(lblQunLNgi);
    }
    
    private void addUser() {
        if (!validateInput()) return;

        try {
            if(userBUS.isUserExist(UserName.getText())){
                JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại");
                return;
            }
            UserEntity user = new UserEntity(0,UserName.getText(), txtEmail.getText(),"12345678", txtFullName.getText(), cbIsAdmin.getSelectedIndex());
            userBUS.createUser(user);
            loadTableData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi : " + e.getMessage());
        }
    }
    
    private void updateUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần sửa.");
            return;
        }
        if (!validateInput()) return;
        try {
            int userId = (int) model.getValueAt(selectedRow, 0);
            UserEntity user = new UserEntity(userId, UserName.getText(), txtEmail.getText(),"12345678", txtFullName.getText(),  cbIsAdmin.getSelectedIndex());
            userBUS.updateUser(user);
            loadTableData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }
    
    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần xóa.");
            return;
        }

        try {
            int userId = (int) model.getValueAt(selectedRow, 0);
                userBUS.deleteUser(userId);
                loadTableData();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }
    private boolean validateInput() {
        String userName = UserName.getText().trim();
        String email = txtEmail.getText().trim();

        String fullName = txtFullName.getText().trim();

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được để trống.");
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



        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống.");
            return false;
        }

        return true;
    }
    private void loadTableData() {
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (UserEntity user : userBUS.getAllUser()) {
            model.addRow(new Object[]{user.getUserID(), user.getUserName(),
                    user.getUserEmail(), user.getUserFullName(), user.getIsAdmin()==0?"User":"Admin"});
        }
    }
    private void clearFields() {
        UserName.setText("");
        txtEmail.setText("");
        txtFullName.setText("");
        cbIsAdmin.setSelectedIndex(0);
    }
    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Kiểm tra có hàng nào được chọn không
                UserName.setText(model.getValueAt(selectedRow, 1).toString());
                txtEmail.setText(model.getValueAt(selectedRow, 2).toString());
                txtFullName.setText(model.getValueAt(selectedRow, 3).toString());
                cbIsAdmin.setSelectedItem(model.getValueAt(selectedRow, 4).toString());
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserForm().setVisible(true));
    }
}
