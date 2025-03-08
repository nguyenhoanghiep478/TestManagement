package GUI;

import BUS.IQuestionBUS;
import BUS.ITopicBUS;
import BUS.QuestionBUS;
import BUS.TopicBUS;
import Entity.QuestionEntity;
import Entity.TopicEntity;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class QuestionForm extends JFrame {
    private JTextField txtContent;
    private JLabel lblPicture;
    private JComboBox<String> cbTopic, cbLevel;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete, btnUpload;
    private String imagePath = "";

    private JTextField search;
    private final ITopicBUS topicBUS = new TopicBUS();
    private final IQuestionBUS questionBUS = new QuestionBUS();

    public QuestionForm() {
        getContentPane().setBackground(new Color(255, 255, 255));
        setTitle("Quản lý Câu Hỏi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 76, 760, 250);
        getContentPane().add(scrollPane);

        model = new DefaultTableModel(new String[]{"Id", "Content", "Topic", "Level"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        table.setBackground(new Color(255, 255, 255));
        scrollPane.setViewportView(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        table.getTableHeader().setBackground(Color.WHITE);
        JPanel panelForm = new JPanel();
        panelForm.setBackground(new Color(255, 255, 255));
        panelForm.setLayout(null);
        panelForm.setBounds(10, 352, 766, 201);
        getContentPane().add(panelForm);

        JLabel lblContent = new JLabel("Content:");
        lblContent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblContent.setBounds(251, 18, 80, 25);
        panelForm.add(lblContent);

        txtContent = new JTextField();
        txtContent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtContent.setBounds(331, 10, 267, 46);
        panelForm.add(txtContent);

        JLabel lblTopic = new JLabel("Topic:");
        lblTopic.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblTopic.setBounds(251, 77, 80, 25);
        panelForm.add(lblTopic);

        cbTopic = new JComboBox<>(new String[]{"Toán", "Lý", "Hóa", "Sinh"});
        cbTopic.setBackground(new Color(255, 255, 255));
        cbTopic.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbTopic.setBounds(331, 66, 150, 46);
        panelForm.add(cbTopic);

        JLabel lblLevel = new JLabel("Level:");
        lblLevel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblLevel.setBounds(251, 133, 80, 25);
        panelForm.add(lblLevel);

        cbLevel = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        cbLevel.setBackground(new Color(255, 255, 255));
        cbLevel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cbLevel.setBounds(331, 122, 150, 46);
        panelForm.add(cbLevel);

        btnUpload = new JButton("Chọn ảnh");
        btnUpload.setBackground(new Color(255, 255, 255));
        btnUpload.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnUpload.setBounds(30, 123, 112, 46);
        panelForm.add(btnUpload);
        btnUpload.addActionListener(e -> uploadImage());

        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(255, 255, 255));
        btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnAdd.setBounds(630, 7, 126, 46);
        btnAdd.setHorizontalAlignment(SwingConstants.LEFT);
        // Lấy icon từ đường dẫn
        ImageIcon addIcon = new ImageIcon(getClass().getClassLoader().getResource("ICON/add.png"));
        // Thay đổi kích thước icon (ví dụ: 30x30 pixels)
        Image scaledAddImage = addIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledAddIcon = new ImageIcon(scaledAddImage);
        // Đặt icon cho nút
        btnAdd.setIcon(scaledAddIcon);
        panelForm.add(btnAdd);
        btnAdd.addActionListener(e -> addQuestion());

        btnUpdate = new JButton("Sửa");
        btnUpdate.setBackground(new Color(255, 255, 255));
        btnUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnUpdate.setBounds(630, 66, 126, 46);
        btnUpdate.setHorizontalAlignment(SwingConstants.LEFT);
        // Lấy icon từ đường dẫn
        ImageIcon editIcon = new ImageIcon(getClass().getClassLoader().getResource("ICON/edit.png"));

        // Thay đổi kích thước icon (ví dụ: 30x30 pixels)
        Image scaledEditImage = editIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledEditIcon = new ImageIcon(scaledEditImage);

        // Đặt icon cho nút
        btnUpdate.setIcon(scaledEditIcon);
        panelForm.add(btnUpdate);
        btnUpdate.addActionListener(e -> updateQuestion());

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(255, 255, 255));
        btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnDelete.setBounds(630, 133, 126, 46);
        btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
        // Lấy icon từ đường dẫn
        ImageIcon deleteIcon = new ImageIcon(getClass().getClassLoader().getResource("ICON/delete.png"));

        // Thay đổi kích thước icon (ví dụ: 30x30 pixels)
        Image scaledDeleteImage = deleteIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledDeleteIcon = new ImageIcon(scaledDeleteImage);

        // Đặt icon cho nút
        btnDelete.setIcon(scaledDeleteIcon);
        panelForm.add(btnDelete);
        loadTopics();
        loadQuestions();
        addTableSelectionListener();
        lblPicture = new JLabel();
        lblPicture.setBounds(30, 12, 112, 102);
        panelForm.add(lblPicture);
        lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        search = new JTextField();
        search.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        search.setBounds(459, 21, 311, 46);
        getContentPane().add(search);
        search.setColumns(10);

        JComboBox comboBox = new JComboBox();
        comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBox.setBounds(331, 20, 111, 46);
        getContentPane().add(comboBox);
        btnDelete.addActionListener(e -> deleteQuestion());
    }

    public String saveImageToFolder(String sourcePath) throws IOException {
        // Thư mục IMAGE trong src
        String folderPath = "src/IMAGE";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Lấy tên file ảnh
        File sourceFile = new File(sourcePath);
        String fileName = sourceFile.getName();

        // Đường dẫn lưu ảnh trong src/IMAGE
        File destinationFile = new File(folder, fileName);

        // Sao chép file ảnh vào thư mục IMAGE
        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return "src/IMAGE/" + fileName;  // Trả về đường dẫn tương đối
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

    private void addQuestion() {
        // Mở hộp thoại chọn file Excel
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // Đọc dữ liệu từ file Excel
                List<QuestionEntity> questions = readQuestionsFromExcel(selectedFile.getAbsolutePath());

                // Thêm từng câu hỏi vào cơ sở dữ liệu
                for (QuestionEntity question : questions) {
                    questionBUS.createQuestion(question);
                }

                // Thông báo thành công
                JOptionPane.showMessageDialog(this, "Đã thêm " + questions.size() + " câu hỏi từ file Excel.");

                // Tải lại danh sách câu hỏi
                loadQuestions();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public List<QuestionEntity> readQuestionsFromExcel(String filePath) throws IOException {
        List<QuestionEntity> questions = new ArrayList<>();

        // Mở file Excel
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Lấy sheet đầu tiên
            Sheet sheet = workbook.getSheetAt(0);

            // Duyệt qua các dòng (bỏ qua dòng tiêu đề)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // Đọc dữ liệu từ các cột
                String content = row.getCell(0).getStringCellValue();
                String topic = row.getCell(1).getStringCellValue();
                String level = row.getCell(2).getStringCellValue();
                String picturePath = row.getCell(3).getStringCellValue();

                // Tạo đối tượng QuestionEntity
                QuestionEntity question = new QuestionEntity();
                question.setqContent(content);
                question.setqTopicID(topicBUS.findTopicByTitle(topic).getTpID()); // Chuyển đổi tên topic thành ID
                question.setqLevel(level);
                question.setqPictures(picturePath);
                question.setqStatus(1); // Mặc định status là 1

                // Thêm vào danh sách
                questions.add(question);
            }
        }

        return questions;
    }

    private void updateQuestion() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để sửa");
                return;
            }

            int id = (int) table.getValueAt(selectedRow, 0);
            String content = txtContent.getText();
            int topic = topicBUS.findTopicByTitle((String) cbTopic.getSelectedItem()).getTpID();
            String level = (String) cbLevel.getSelectedItem();

            // Lấy đường dẫn ảnh cũ từ database
            QuestionEntity existingQuestion = questionBUS.findQuestionById(id);
            String newImagePath = existingQuestion.getqPictures(); // Giữ nguyên ảnh cũ mặc định

            // Nếu người dùng chọn ảnh mới, lưu ảnh vào thư mục IMAGE và cập nhật đường dẫn
            if (!imagePath.isEmpty()) {
                newImagePath = saveImageToFolder(imagePath);
            }

            // Cập nhật câu hỏi trong database
            QuestionEntity updatedQuestion = new QuestionEntity(id, content, newImagePath, topic, level, 1);
            questionBUS.updateQuestion(updatedQuestion);

            loadQuestions();  // Tải lại danh sách câu hỏi
            clearFields();    // Xóa dữ liệu nhập sau khi cập nhật
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật câu hỏi: " + e.getMessage());
        }
    }

    private void deleteQuestion() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn câu hỏi để xóa");
                return;
            }

            int id = (int) table.getValueAt(selectedRow, 0);
            questionBUS.deleteQuestion(id);
            loadQuestions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa câu hỏi: " + e.getMessage());
        }
    }

    private void loadTopics() {
        cbTopic.removeAllItems(); // Xóa dữ liệu cũ trước khi tải mới
        for (TopicEntity topic : topicBUS.getAllTopic()) {
            cbTopic.addItem(topic.getTpTitle());
        }
    }

    public void clearFields() {
        txtContent.setText("");
        lblPicture.setIcon(null);
        cbTopic.setSelectedIndex(0);
        cbLevel.setSelectedIndex(0);
        imagePath = "";
    }

    public void loadQuestions() {
        // Xóa dữ liệu cũ trong table
        model.setRowCount(0);

        List<QuestionEntity> questions = questionBUS.getAllQuestion();
        // Đổ dữ liệu vào JTable
        for (QuestionEntity question : questions) {
            // Lấy thông tin chủ đề (topic) từ TopicBUS
            String topicTitle = topicBUS.findTopicById(question.getqTopicID()).getTpTitle();

            // Thêm dòng dữ liệu vào table
            model.addRow(new Object[]{
                    question.getqID(),
                    question.getqContent(),
                    topicTitle,
                    question.getqLevel()
            });
        }
    }

    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Kiểm tra có hàng nào được chọn không
                QuestionEntity questionEntity = questionBUS.findQuestionById(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(questionEntity.getqPictures()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblPicture.setIcon(imageIcon);
                txtContent.setText(model.getValueAt(selectedRow, 1).toString());
                cbTopic.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
                cbLevel.setSelectedItem(model.getValueAt(selectedRow, 3).toString());

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionForm().setVisible(true));
    }
}

