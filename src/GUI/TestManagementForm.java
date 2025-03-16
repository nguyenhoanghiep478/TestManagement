package GUI;

import BUS.TopicBUS;
import DAO.impl.ExamDAO;
import DAO.impl.TestDAO;
import Entity.ExamEntity;
import Entity.TestEntity;
import Entity.TopicEntity;
import Service.IExamService;
import Service.ITestService;
import Service.impl.ExamService;
import Service.impl.TestService;
import Utils.Mapper.impl.ExamMapper;
import Utils.Mapper.impl.TestMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import java.sql.Date;
import java.util.List;

public class TestManagementForm extends JPanel {
    private JTextField txtEmail, txtFullName;
    private JComboBox<String> cbIsAdmin;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private JTextField UserName,limitField,easyField,medField,diffField;
    private TopicBUS topicBUS = new TopicBUS();
    private List<TopicEntity> topics;
    private final ITestService testService =new TestService(new TestDAO(new TestMapper()));
    public TestManagementForm() {
        setBackground(new Color(255, 255, 255));
        setFont(new Font("Tahoma", Font.PLAIN, 14));

        setSize(1150, 631);

        setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 59, 798, 253);
        add(scrollPane);

        this.topics = topicBUS.getAllTopic();

        model = new DefaultTableModel(new String[]{"Id", "Code", "title","Topic","time","limit","status","Easy","Medium","Difficult"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.setSize(1100,500);
        table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        scrollPane.setViewportView(table);

        JPanel panelForm = new JPanel();
        panelForm.setBackground(new Color(255, 255, 255));
        panelForm.setLayout(null);
        panelForm.setBounds(10, 322, 802, 400);
        add(panelForm);

        JLabel lblEmail = new JLabel("Title:");
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

        JLabel lblIsAdmin = new JLabel("Topic:");
        lblIsAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblIsAdmin.setBounds(329, 89, 60, 25);
        panelForm.add(lblIsAdmin);

        cbIsAdmin = new JComboBox<>(topics.stream().map(TopicEntity::getTpTitle).toArray(String[]::new));
        cbIsAdmin.setBackground(new Color(255, 255, 255));
        cbIsAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbIsAdmin.setBounds(419, 78, 112, 46);



        panelForm.add(cbIsAdmin);

        JLabel lblUsername = new JLabel("Code:");
        lblUsername.setBounds(10, 20, 74, 25);
        panelForm.add(lblUsername);
        lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        UserName = new JTextField(20);
        UserName.setBounds(71, 10, 234, 46);
        panelForm.add(UserName);
        UserName.setFont(new Font("Times New Roman", Font.PLAIN, 18));


        JLabel lblLimit = new JLabel("Limit:");
        lblLimit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblLimit.setBounds(10, 160, 74, 25);
        panelForm.add(lblLimit);

        limitField = new JTextField(20);
        limitField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        limitField.setBounds(71, 160, 234, 46);
        panelForm.add(limitField);

        JLabel lblEasy = new JLabel("Easy:");
        lblEasy.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblEasy.setBounds(329, 160, 140, 25);
        panelForm.add(lblEasy);

        easyField = new JTextField(20);
        easyField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        easyField.setBounds(419, 160, 234, 46);
        panelForm.add(easyField);

        JLabel lblMed = new JLabel("Medium:");
        lblMed.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblMed.setBounds(0, 240, 140, 25);
        panelForm.add(lblMed);

        medField = new JTextField(20);
        medField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        medField.setBounds(71, 240, 234, 46);
        panelForm.add(medField);

        JLabel lblDiff = new JLabel("Difficult:");
        lblDiff.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblDiff.setBounds(329, 240, 140, 25);
        panelForm.add(lblDiff);

        diffField = new JTextField(20);
        diffField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        diffField.setBounds(419, 240, 234, 46);
        panelForm.add(diffField);

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

        JLabel lblFullName = new JLabel("Time:");
        lblFullName.setBounds(329, 19, 80, 25);
        panelForm.add(lblFullName);
        lblFullName.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        btnDelete.addActionListener(e -> deleteUser());
        btnUpdate.addActionListener(e -> updateUser());
        addTableSelectionListener();
        btnAdd.addActionListener(e -> addUser());
        loadTableData();
        JLabel lblQunLNgi = new JLabel("Quản lí bài thi");
        lblQunLNgi.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblQunLNgi.setBounds(274, 10, 259, 40);
        add(lblQunLNgi);
    }

    private void addUser() {
//        if (!validateInput()) return;
//
        try {
            int tpId = topics.stream().filter(topic -> topic.getTpTitle().equals(cbIsAdmin.getSelectedItem())).findFirst().get().getTpID();
            if(!testService.isExistTest(UserName.getText(),tpId)){
                JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại");
                return;
            }
            TestEntity test = new TestEntity(0,UserName.getText(),txtEmail.getText(),
                    Integer.parseInt(txtFullName.getText()),
                    tpId,Integer.parseInt(easyField.getText()),
                    Integer.parseInt(medField.getText()),
                    Integer.parseInt(diffField.getText()),
                    Integer.parseInt(limitField.getText()),
                    new Date(System.currentTimeMillis()),1);
            testService.createTest(test);
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
            int testId = (int) model.getValueAt(selectedRow, 0);
            int tpId = topics.stream().filter(topic -> topic.getTpTitle().equals(cbIsAdmin.getSelectedItem())).findFirst().get().getTpID();
            TestEntity test = new TestEntity(testId,UserName.getText(),txtEmail.getText(),
                    Integer.parseInt(txtFullName.getText()),
                    tpId,Integer.parseInt(easyField.getText()),
                    Integer.parseInt(medField.getText()),
                    Integer.parseInt(diffField.getText()),
                    Integer.parseInt(limitField.getText()),
                    new Date(System.currentTimeMillis()),1);

            testService.updateTest(test);
            loadTableData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void deleteUser() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần xóa.");
//            return;
//        }
//
//        try {
//            int userId = (int) model.getValueAt(selectedRow, 0);
//            userBUS.deleteUser(userId);
//            loadTableData();
//            JOptionPane.showMessageDialog(this, "Xóa thành công!");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
//        }
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


        for (TestEntity test : testService.findAll()) {
            String topicTitle = topics.stream().filter(topic -> topic.getTpID() == test.getTopicId()).findFirst().get().getTpTitle();
            model.addRow(new Object[]{test.getTestId(), test.getTestCode(),
                    test.getTestTitle(),topicTitle,test.getTestTime(),test.getTestLimit()
                    ,test.getTestStatus() == 1 ? "Hoạt động" : "Không hoạt động",
                    test.getNumEasy(),test.getNumMedium(),test.getNumDiff()});
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
                txtFullName.setText(model.getValueAt(selectedRow, 4).toString());
                cbIsAdmin.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
                limitField.setText(model.getValueAt(selectedRow, 5).toString());
                easyField.setText(model.getValueAt(selectedRow, 7).toString());
                medField.setText(model.getValueAt(selectedRow, 8).toString());
                diffField.setText(model.getValueAt(selectedRow, 9).toString());

            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TestManagementForm().setVisible(true));
    }
}
