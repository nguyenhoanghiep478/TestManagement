package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.UserBUS;
import DAO.impl.ExamDAO;
import Entity.ExamEntity;
import Entity.TopicEntity;
import Entity.UserEntity;
import Service.impl.ExamService;
import Utils.Mapper.impl.ExamMapper;
import lombok.RequiredArgsConstructor;

public class ExamForm extends JPanel {
	private ExamService examService=new ExamService(new ExamDAO(new ExamMapper()));
	    private JComboBox<String> cbTestCode;
	    private JTable table;
	    private DefaultTableModel model;
	    private JButton btnAdd, btnUpdate, btnDelete;
	    private Font font = new Font("Arial", Font.PLAIN, 14);
	    private JTextField UserName;
	    private final UserBUS userBUS=new UserBUS();
	    public ExamForm() {
	    	setBackground(new Color(255, 255, 255));
	    	setFont(new Font("Tahoma", Font.PLAIN, 14));
	        setSize(1150, 631);
	        setLayout(null);
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(14, 10, 1115, 372);
	        add(scrollPane);


	        model = new DefaultTableModel(new String[]{"TestCode", "ExCode","ExOrder","Ex_Questid"}, 0);
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
	        panelForm.setBounds(14, 392, 1115, 206);
	      add(panelForm);
	        
	        cbTestCode = new JComboBox<>(new String[]{"User", "Admin"});
	        cbTestCode.setBackground(new Color(255, 255, 255));
	        cbTestCode.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        cbTestCode.setBounds(772, 9, 147, 46);



	        panelForm.add(cbTestCode);
	        
	        JLabel lblUsername = new JLabel("Số lượng đề");
	        lblUsername.setBounds(0, 20, 121, 25);
	        panelForm.add(lblUsername);
	        lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        UserName = new JTextField(20);
	        UserName.setBounds(104, 10, 251, 46);
	        panelForm.add(UserName);
	        UserName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        btnAdd = new JButton("Thêm");
	        btnAdd.setBackground(new Color(255, 255, 255));
	        btnAdd.setBounds(994, 9, 121, 46);
	        panelForm.add(btnAdd);
	        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
	        btnAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));
	        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        btnUpdate = new JButton("Sửa");
	        btnUpdate.setBackground(new Color(255, 255, 255));
	        btnUpdate.setBounds(994, 78, 121, 46);
	        panelForm.add(btnUpdate);
	        btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
	        btnUpdate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/edit.png")));
	        btnUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        btnDelete = new JButton("Xóa");
	        btnDelete.setBackground(new Color(255, 255, 255));
	        btnDelete.setBounds(994, 148, 121, 46);
	        panelForm.add(btnDelete);
	        btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
	        btnDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/delete.png")));
	        btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        JLabel lblTestcode = new JLabel("TestCode");
	        lblTestcode.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        lblTestcode.setBounds(688, 20, 74, 25);
	        panelForm.add(lblTestcode);

//	        btnDelete.addActionListener(e -> deleteUser());
//	        btnUpdate.addActionListener(e -> updateUser());
//	        addTableSelectionListener();
//	        btnAdd.addActionListener(e -> addUser());
	        loadTableData();
			loadTestCode();
	    }
	    private void loadTestCode() {
	        cbTestCode.removeAllItems(); // Xóa dữ liệu cũ trước khi tải mới
			List<String>lTestCode=examService.getAllExam().stream().map(testCode->testCode.getTestCode()).collect(Collectors.toList());
	        for (String testCode :lTestCode) {
	            cbTestCode.addItem(testCode);
	        }
	    }
	private void loadTableData() {
		model.setRowCount(0); // Xóa dữ liệu cũ
		for (ExamEntity examEntity : examService.getAllExam()) {
			model.addRow(new Object[]{examEntity.getTestCode(),examEntity.getExOrder(),
					examEntity.getExCode(),examEntity.getEx_quesId()});
		}
	}
}
