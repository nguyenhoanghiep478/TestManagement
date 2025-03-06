package GUI;

import javax.swing.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import BUS.AnswerBUS;
import BUS.QuestionBUS;
import Entity.AnswerEntity;
import Entity.QuestionEntity;

import java.awt.*;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class AnswerForm extends JFrame {
    private JTextField txtContent;
    private JLabel lblPicture;
    private JComboBox<String> cbIsRight;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete, btnUpload;
    private String imagePath = "";
    private List<AnswerEntity> answerList = new ArrayList<>();
    private QuestionEntity questionEntity;
    private QuestionForm questionForm;
    private QuestionBUS questionBUS=new QuestionBUS();
    private AnswerBUS answerBUS=new AnswerBUS();
    public AnswerForm(QuestionEntity question,QuestionForm form) {
    	getContentPane().setBackground(new Color(255, 255, 255));
        this.questionEntity=question;
        this.questionForm=form;
        setTitle("Quản lý Câu Trả Lời");
        setSize(803, 663);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 76, 760, 250);
        getContentPane().add(scrollPane);

        model = new DefaultTableModel(new String[]{ "Content","Is Right"}, 0);
        table = new JTable(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Content", "Is Right"
        	}
        ));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        scrollPane.setViewportView(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        JPanel panelForm = new JPanel();
        panelForm.setBackground(new Color(255, 255, 255));
        panelForm.setLayout(null);
        panelForm.setBounds(10, 352, 766, 250);
        getContentPane().add(panelForm);

        JLabel lblContent = new JLabel("Content:");
        lblContent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblContent.setBounds(251, 18, 80, 25);
        panelForm.add(lblContent);

        txtContent = new JTextField();
        txtContent.setBounds(331, 10, 267, 46);
        panelForm.add(txtContent);

        JLabel lblIsRight = new JLabel("Is Right:");
        lblIsRight.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblIsRight.setBounds(251, 77, 80, 25);
        panelForm.add(lblIsRight);

        cbIsRight = new JComboBox<>(new String[]{"True", "False"});
        cbIsRight.setBackground(new Color(255, 255, 255));
        cbIsRight.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbIsRight.setBounds(331, 66, 150, 46);
        panelForm.add(cbIsRight);

        btnUpload = new JButton("Chọn ảnh");
        btnUpload.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnUpload.setBounds(30, 123, 116, 46);
        panelForm.add(btnUpload);
        btnUpload.addActionListener(e -> uploadImage());

        btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnAdd.setBounds(630, 7, 126, 46);
        panelForm.add(btnAdd);
        btnAdd.addActionListener(e -> addAnswer());
        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
        btnAdd.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png")));

        btnUpdate = new JButton("Sửa");
        btnUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnUpdate.setBounds(630, 66, 126, 46);
        panelForm.add(btnUpdate);
        btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
        btnUpdate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/edit.png")));
        btnUpdate.addActionListener(e -> updateAnswer());

        btnDelete = new JButton("Xóa");
        btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnDelete.setBounds(630, 133, 126, 46);
        panelForm.add(btnDelete);
        btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
        btnDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/delete.png")));
        btnDelete.addActionListener(e -> deleteAnswer());

        lblPicture = new JLabel();
        lblPicture.setBounds(30, 12, 116, 97);
        panelForm.add(lblPicture);
        lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JButton add_AnswerQuest = new JButton("Lưu");
        add_AnswerQuest.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add_AnswerQuest.setBounds(630, 194, 126, 46);
        add_AnswerQuest.addActionListener(e->createQuestion());
        panelForm.add(add_AnswerQuest);
        
        JLabel lblThmCuHi = new JLabel("Thêm câu hỏi");
        lblThmCuHi.setHorizontalAlignment(SwingConstants.CENTER);
        lblThmCuHi.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblThmCuHi.setBounds(182, 10, 344, 56);
        getContentPane().add(lblThmCuHi);
        addTableSelectionListener();
        setVisible(true);
    }

    private void uploadImage() {
        FileDialog fileDialog = new FileDialog(this, "Chọn ảnh", FileDialog.LOAD);

        // Chỉ hiển thị file ảnh (JPG, PNG, JPEG)
        fileDialog.setFile("*.jpg;*.png;*.jpeg");

        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();

        if (directory != null && file != null) {
            imagePath = directory + file;

            // Hiển thị ảnh lên JLabel
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            lblPicture.setIcon(imageIcon);
        }
    }

    private void addAnswer() {
        String content = txtContent.getText();
        String isRight = (String) cbIsRight.getSelectedItem();
        String savedImagePath = imagePath.isEmpty() ? "" : imagePath;
        AnswerEntity answer = new AnswerEntity(0, 0,content, savedImagePath, isRight=="True"?1:0,1);
        answerList.add(answer);
        model.addRow(new Object[]{ answer.getAwContent(), answer.getIsRight()==1?"True":"False"});
        clearFields();
    }

    private void updateAnswer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu trả lời để sửa");
            return;
        }
        AnswerEntity answer = answerList.get(selectedRow);
        answer.setAwContent(txtContent.getText());
        answer.setAwPictures(imagePath.isEmpty() ? answer.getAwPictures() : imagePath);
        answer.setIsRight(cbIsRight.getSelectedItem()=="True"?1:0);
        model.setValueAt(answer.getAwContent(), selectedRow, 0);
        model.setValueAt(answer.getIsRight()==1?"True":"False", selectedRow, 1);
    }

    private void deleteAnswer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu trả lời để xóa");
            return;
        }
        answerList.remove(selectedRow);
        model.removeRow(selectedRow);
    }
    private void createQuestion() {
        try {
            // Kiểm tra nếu có ảnh được chọn
            String savedImagePath = "";
            if (!questionEntity.getqPictures().isEmpty()) {
                savedImagePath = questionForm.saveImageToFolder(questionEntity.getqPictures()); // Lưu ảnh vào thư mục IMAGE
            }
            questionEntity.setqPictures(savedImagePath);
            // Thêm câu hỏi vào DB
          long questId=  questionBUS.createQuestion(questionEntity);
            for(AnswerEntity answerEntity:answerList){
                answerEntity.setqID((int) questId);
               String temp="";
                if (!answerEntity.getAwPictures().isEmpty()) {
                    temp = questionForm.saveImageToFolder(answerEntity.getAwPictures()); // Lưu ảnh vào thư mục IMAGE
                }
                answerEntity.setAwPictures(temp);
                answerBUS.createAnswer(answerEntity);
            }
            new QuestionForm();
            dispose();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Lỗi khi thêm câu hỏi và các câu trả lời: " + e.getMessage());
        }
    }
    public void clearFields() {
        txtContent.setText("");
        lblPicture.setIcon(null);
        cbIsRight.setSelectedIndex(0);
        imagePath = "";
    }
    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Kiểm tra có hàng nào được chọn không
                AnswerEntity answerEntity=answerList.get(table.getSelectedRow());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(answerEntity.getAwPictures()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblPicture.setIcon(imageIcon);
                txtContent.setText(answerEntity.getAwContent());
                cbIsRight.setSelectedItem(answerEntity.getIsRight()==1?"True":"False");

            }
        });
    }
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AnswerForm().setVisible(true));
    }
}
