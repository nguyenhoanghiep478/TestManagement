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
import java.util.stream.Collectors;

public class AnswerForm extends JFrame {
    private JTextField txtContent;
    private JLabel lblPicture;
    private JComboBox<String> cbIsRight;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete, btnUpload;
    private String imagePath = "";
    private QuestionEntity questionEntity;
    private QuestionForm questionForm=new QuestionForm();
    private AnswerBUS answerBUS=new AnswerBUS();
    public AnswerForm(QuestionEntity question) {
    	getContentPane().setBackground(new Color(255, 255, 255));
        this.questionEntity=question;
        setTitle("Quản lý Câu Trả Lời");
        setSize(803, 663);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 76, 769, 250);
        getContentPane().add(scrollPane);

        model = new DefaultTableModel(new String[]{ "Id","Content","Is Right"}, 0);
        table = new JTable(model);
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
        lblContent.setBounds(189, 20, 80, 25);
        panelForm.add(lblContent);

        txtContent = new JTextField();
        txtContent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtContent.setBounds(267, 10, 341, 46);
        panelForm.add(txtContent);

        JLabel lblIsRight = new JLabel("Is Right:");
        lblIsRight.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblIsRight.setBounds(189, 77, 80, 25);
        panelForm.add(lblIsRight);

        cbIsRight = new JComboBox<>(new String[]{"True", "False"});
        cbIsRight.setBackground(new Color(255, 255, 255));
        cbIsRight.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbIsRight.setBounds(267, 69, 150, 46);
        panelForm.add(cbIsRight);

        btnUpload = new JButton("Chọn ảnh");
        btnUpload.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnUpload.setBounds(10, 125, 116, 46);
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
        lblPicture.setBounds(10, 18, 116, 97);
        panelForm.add(lblPicture);
        lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loadAnswer();
        JLabel lblThmCuHi = new JLabel("Quản lý câu trả lời");
        lblThmCuHi.setHorizontalAlignment(SwingConstants.CENTER);
        lblThmCuHi.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblThmCuHi.setBounds(233, 10, 344, 56);
        getContentPane().add(lblThmCuHi);
        addTableSelectionListener();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        try {
            String content = txtContent.getText();
            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nội dung không được để trống");
                return;
            }
            String savedImagePath = "";
            if (!imagePath.isEmpty()) {
                savedImagePath = questionForm.saveImageToFolder(imagePath); // Lưu ảnh vào thư mục IMAGE
            }
            String isRight = (String) cbIsRight.getSelectedItem();
            AnswerEntity answer = new AnswerEntity(0, questionEntity.getqID(),content, savedImagePath, isRight=="True"?1:0,1);
            answerBUS.createAnswer(answer);
            JOptionPane.showMessageDialog(this, "Thêm câu trả lời thành công");
            loadAnswer();
            clearFields();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm câu hỏi " + e.getMessage());
        }

    }

    private void updateAnswer() {
        try{
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn câu trả lời để sửa");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            AnswerEntity answer =answerBUS.findAnswerById(id);
            String newImagePath = answer.getAwPictures(); // Giữ nguyên ảnh cũ mặc định

            // Nếu người dùng chọn ảnh mới, lưu ảnh vào thư mục IMAGE và cập nhật đường dẫn
            if (!imagePath.isEmpty()) {
                newImagePath = questionForm.saveImageToFolder(imagePath);
            }
            answer.setAwContent(txtContent.getText());
            answer.setAwPictures(newImagePath);
            answer.setIsRight(cbIsRight.getSelectedItem()=="True"?1:0);
            answerBUS.updateAnswer(answer);
            clearFields();
            loadAnswer();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm câu hỏi " + e.getMessage());
        }

    }

    private void deleteAnswer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn câu trả lời để xóa");
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 0);
        answerBUS.removeAnswer(id);
        loadAnswer();
    }
//    private void createQuestion() {
//        try {
//            // Kiểm tra nếu có ảnh được chọn
//            String savedImagePath = "";
//            if (!questionEntity.getqPictures().isEmpty()) {
//                savedImagePath = questionForm.saveImageToFolder(questionEntity.getqPictures()); // Lưu ảnh vào thư mục IMAGE
//            }
//            questionEntity.setqPictures(savedImagePath);
//            // Thêm câu hỏi vào DB
//          long questId=  questionBUS.createQuestion(questionEntity);
//            for(AnswerEntity answerEntity:answerList){
//                answerEntity.setqID((int) questId);
//               String temp="";
//                if (!answerEntity.getAwPictures().isEmpty()) {
//                    temp = questionForm.saveImageToFolder(answerEntity.getAwPictures()); // Lưu ảnh vào thư mục IMAGE
//                }
//                answerEntity.setAwPictures(temp);
//                answerBUS.createAnswer(answerEntity);
//            }
//            new QuestionForm();
//            dispose();
//        } catch (Exception e) {
//
//            JOptionPane.showMessageDialog(this, "Lỗi khi thêm câu hỏi và các câu trả lời: " + e.getMessage());
//        }
//    }
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
                int id = (int) table.getValueAt(selectedRow, 0);
                AnswerEntity answerEntity =answerBUS.findAnswerById(id);
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(answerEntity.getAwPictures()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblPicture.setIcon(imageIcon);
                txtContent.setText(answerEntity.getAwContent());
                cbIsRight.setSelectedItem(answerEntity.getIsRight()==1?"True":"False");

            }
        });
    }
    public void loadAnswer() {
        List<AnswerEntity>g=answerBUS.getAllAnswer();
        List<AnswerEntity>list=answerBUS.getAllAnswer().stream().filter(answerEntity -> answerEntity.getqID()==questionEntity.getqID()).toList();
        model.setRowCount(0);
        for (AnswerEntity answerEntity:list) {
            model.addRow(new Object[]{answerEntity.getAwID(),answerEntity.getAwContent(),answerEntity.getIsRight()==1?"True":"Flase"});
        }
    }
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AnswerForm().setVisible(true));
    }
}
