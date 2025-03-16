package GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import BUS.UserBUS;
import DAO.impl.TestDAO;
import Entity.TestEntity;
import Entity.UserEntity;
import Service.impl.TestService;
import Utils.Mapper.impl.TestMapper;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListTestForm extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnTest;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private String selectedTestCode;
   private TestService testService=new TestService(new TestDAO(new TestMapper()));
    public ListTestForm(String operate) {
    	setBackground(new Color(255, 255, 255));
    	setFont(new Font("Tahoma", Font.PLAIN, 14));
        setSize(1150, 631);
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 1115, 509);
        add(scrollPane);


        model = new DefaultTableModel(new String[]{"TestCode", "TestTittle", "TestTime","TestLimit","TestDate"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        table.setRowHeight(20);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        
        btnTest = new JButton("Thi ngay");
     
        if(operate.compareTo("static")==0){
        	btnTest.setText("Xem thống kê");
        	   btnTest.addActionListener(new ActionListener() {
               	public void actionPerformed(ActionEvent e) {
               		if (selectedTestCode != null) {
               			ExamCandidatesForm userForm = new ExamCandidatesForm(selectedTestCode);
               	    } else {
               	        JOptionPane.showMessageDialog(null,"vui lòng chọn bài thi cần thống kê");
               	    }
               	}
               });
        }else {
        	   btnTest.addActionListener(new ActionListener() {
               	public void actionPerformed(ActionEvent e) {
               		
               		if (selectedTestCode != null) {
               			new TestForm("test_001",1,"test_001A");
               	    } else {
               	        JOptionPane.showMessageDialog(null,"vui lòng chọn môn cần thi");
               	    }
               	}
               });
        }
        btnTest.setBounds(468, 550, 151, 46);
        add(btnTest);
        btnTest.setBackground(new Color(255, 255, 255));
        btnTest.setHorizontalAlignment(SwingConstants.LEFT);
        btnTest.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));
        btnTest.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        loadTableData();
        addTableSelectionListener();
        setVisible(true);
    }
    private void loadTableData() {
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (TestEntity testEntity:testService.findAll()) {
            model.addRow(new Object[]{testEntity.getTestCode(), testEntity.getTestTitle(),
                    testEntity.getTestTime(),testEntity.getTestLimit(),testEntity.getTestDate()});
        }
    }
    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Kiểm tra có hàng nào được chọn không
                selectedTestCode=model.getValueAt(selectedRow, 0).toString();
            }
        });
    }
}
