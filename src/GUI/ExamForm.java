package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.QuestionBUS;
import BUS.UserBUS;
import DAO.impl.ExamDAO;
import DAO.impl.TestDAO;
import Entity.*;
import Service.impl.ExamService;
import Service.impl.TestService;
import Service.impl.WordExportService;
import Utils.Mapper.impl.ExamMapper;
import Utils.Mapper.impl.TestMapper;
import lombok.RequiredArgsConstructor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExamForm extends JPanel {
	private ExamService examService=new ExamService(new ExamDAO(new ExamMapper()));
	private TestService testService=new TestService(new TestDAO(new TestMapper()));
	private QuestionBUS questionBUS=new QuestionBUS();
	    private JComboBox<String> cbTestCode;
	    private JTable table;
	    private DefaultTableModel model;
	    private JButton btnAdd, btnExport;
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
	        panelForm.setBounds(24, 392, 1115, 206);
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
	        btnAdd.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					String testCode = cbTestCode.getSelectedItem().toString();
					int testAmount = Integer.parseInt(UserName.getText());
					examService.generateExams(testCode, testAmount);
	        	}
	        });
	        btnAdd.setBackground(new Color(255, 255, 255));
	        btnAdd.setBounds(994, 9, 121, 46);
	        panelForm.add(btnAdd);
	        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
	        btnAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));
	        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        btnExport = new JButton("In đề");
	        btnExport.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		  try {
	        			  int selectedRow = table.getSelectedRow();

	        			  if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
	        			      String testCode = table.getValueAt(selectedRow, 0).toString();  // Lấy dữ liệu từ cột 0 (testCode)
	        			      String exCode = table.getValueAt(selectedRow, 1).toString();    // Lấy dữ liệu từ cột 1 (exCode)
			                     MyCustomExam exam = testService.getExamByCode(testCode, exCode);
			                     if (exam == null) {
			                         JOptionPane.showMessageDialog(null, "Không tìm thấy đề thi!");
			                         return;
			                     }
								 String exQuestionIds = exam.getEx_QuestIDs().replace("[","").replace("]","");
			                     List<QuestionEntity> questions = questionBUS.findAllInId(exQuestionIds);
							  WordExportService wordExportService=new WordExportService();
			                     wordExportService.exportExamToWord(exam, questions, "Exam_" + exCode + ".docx");
			                     JOptionPane.showMessageDialog(null, "Xuất file thành công!");
	        			  } else {
	        			      JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng!");
	        			  }
		                 } catch (Exception ex) {
		                     JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
		                 }
	        	}
	        });
	        btnExport.setBackground(new Color(255, 255, 255));
	        btnExport.setBounds(994, 65, 121, 46);
	        panelForm.add(btnExport);
	        btnExport.setHorizontalAlignment(SwingConstants.LEFT);
	        btnExport.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/word.png")));
	        btnExport.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        
	        JLabel lblTestcode = new JLabel("TestCode");
	        lblTestcode.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	        lblTestcode.setBounds(688, 20, 74, 25);
	        panelForm.add(lblTestcode);
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
			model.addRow(new Object[]{examEntity.getTestCode(),examEntity.getExCode(),
					examEntity.getExOrder(),examEntity.getEx_quesId()});
		}
	}
}
