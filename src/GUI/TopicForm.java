package GUI;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.ITopicBUS;
import BUS.TopicBUS;
import Entity.TopicEntity;

import java.awt.*;
import java.util.List;

public class TopicForm extends JPanel {
   
    private JComboBox<String> cbTopicParent;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private JTextField txtTopicTittle;
    private final ITopicBUS topicBUS = new TopicBUS();

    public TopicForm() {
    	setBackground(new Color(255, 255, 255));

        setSize(1150, 631);
        setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 10, 1120, 386);
        add(scrollPane);

        model = new DefaultTableModel(new String[]{"ID", "Title",  "Parent"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        table.setRowHeight(20);
        table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        table.setBackground(new Color(255, 255, 255));
        scrollPane.setViewportView(table);

        JPanel panelForm = new JPanel();
        panelForm.setBackground(new Color(255, 255, 255));
        panelForm.setLayout(null);
        panelForm.setBounds(20, 416, 1120, 189);
        add(panelForm);

        JLabel lblTopicName = new JLabel("Title:");
        lblTopicName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblTopicName.setBounds(10, 20, 61, 25);
        panelForm.add(lblTopicName);
        JLabel lblTopicParent = new JLabel("Topic parent");
        lblTopicParent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblTopicParent.setBounds(562, 20, 100, 25);
        panelForm.add(lblTopicParent);

        cbTopicParent = new JComboBox<>();
        cbTopicParent.setBackground(new Color(255, 255, 255));
        cbTopicParent.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbTopicParent.setBounds(690, 10, 193, 46);
        panelForm.add(cbTopicParent);
        loadTopicParents(); // Load dữ liệu cho combobox

        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(255, 255, 255));
        btnAdd.setBounds(1000, 9, 120, 46);
        panelForm.add(btnAdd);
        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
        btnAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));
        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        btnUpdate = new JButton("Sửa");
        btnUpdate.setBackground(new Color(255, 255, 255));
        btnUpdate.setBounds(1000, 65, 120, 46);
        btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
        btnUpdate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/edit.png")));
        panelForm.add(btnUpdate);
        btnUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(255, 255, 255));
        btnDelete.setBounds(1000, 121, 120, 46);
        btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
        btnDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/delete.png")));
        panelForm.add(btnDelete);
        btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        txtTopicTittle = new JTextField();
        txtTopicTittle.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtTopicTittle.setBounds(72, 9, 307, 46);
        panelForm.add(txtTopicTittle);
        txtTopicTittle.setColumns(10);

        btnAdd.addActionListener(e -> addTopic());
        btnUpdate.addActionListener(e -> updateTopic());
        btnDelete.addActionListener(e -> deleteTopic());
        addTableSelectionListener();
        loadTableData();
    }

    private void loadTopicParents() {
        cbTopicParent.removeAllItems();
        cbTopicParent.addItem("Không có chủ đề cha"); // Giá trị mặc định

        List<TopicEntity> topics = topicBUS.getAllTopic();
        for (TopicEntity topic : topics) {
            cbTopicParent.addItem(topic.getTpTitle()); // Chỉ hiển thị tên chủ đề
        }
    }
    private void addTopic() {
        if (!validateInput()) return;

        try {
            if(topicBUS.isTopicExist(txtTopicTittle.getText())) {
                JOptionPane.showMessageDialog(this, "Tiêu đề chủ đề đã tồn tại!");
                return ;
            }
            String selectedParent = (String) cbTopicParent.getSelectedItem();
            int parentID = (selectedParent.equals("Không có chủ đề cha")) ? 0 : topicBUS.findTopicByTitle(selectedParent).getTpID();
            TopicEntity topic = new TopicEntity(0, txtTopicTittle.getText(),parentID,1);
            topicBUS.createTopic(topic);
            loadTableData();
            loadTopicParents();
            clearFields();
            JOptionPane.showMessageDialog(this, "Thêm chủ đề thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void updateTopic() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chủ đề cần sửa.");
            return;
        }
        if (!validateInput()) return;

        try {
            int topicId = (int) model.getValueAt(selectedRow, 0);
            String selectedParent = (String) cbTopicParent.getSelectedItem();
            int topicParent = (selectedParent.equals("Không có chủ đề cha")) ? 0 : topicBUS.findTopicByTitle(selectedParent).getTpID();
            TopicEntity topic = new TopicEntity(topicId, txtTopicTittle.getText(),topicParent,1);
            topicBUS.updateTopic(topic);
            loadTableData();
            loadTopicParents();
            clearFields();
            JOptionPane.showMessageDialog(this, "Cập nhật chủ đề thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void deleteTopic() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chủ đề cần xóa.");
            return;
        }

        try {
            int topicId = (int) model.getValueAt(selectedRow, 0);
            topicBUS.deleteTopic(topicId);
            loadTableData();
            loadTopicParents();
            JOptionPane.showMessageDialog(this, "Xóa chủ đề thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        String topicTitle = txtTopicTittle.getText().trim();
        if (topicTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên chủ đề không được để trống.");
            return false;
        }
        return true;
    }
    private void loadTableData() {
        model.setRowCount(0);
        for (TopicEntity topic : topicBUS.getAllTopic()) {
            String parentName = (topic.getTpParent() == 0) ? "Không có" : topicBUS.findTopicById(topic.getTpParent()).getTpTitle();
            model.addRow(new Object[]{topic.getTpID(), topic.getTpTitle(), parentName});
        }
    }
    private void clearFields() {
       txtTopicTittle.setText("");
        cbTopicParent.setSelectedIndex(0);
    }

    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtTopicTittle.setText(model.getValueAt(selectedRow, 1).toString());
                String parentName = model.getValueAt(selectedRow, 2).toString();
                loadTopicParents();
                    cbTopicParent.removeItem(txtTopicTittle.getText());
                cbTopicParent.setSelectedItem(parentName.equals("Không có") ? "Không có chủ đề cha" : parentName);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TopicForm().setVisible(true));
    }
}