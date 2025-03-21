package GUI;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExamCandidatesForm extends JFrame {
	private JLabel lblSubject, lblExamDate, lblTotalCandidates, lblParticipated, lblResultStatus;
	private JTable table;
	private DefaultTableModel model;

	public ExamCandidatesForm(String testCode) {
		setBackground(new Color(255, 255, 255));
		setSize(new Dimension(1150, 630)); // Đặt kích thước JPanel
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); // Căn giữa màn hình

		// Panel chứa thông tin chung
		JPanel panelTop = new JPanel(new GridLayout(3, 2, 10, 10));
		panelTop.setBackground(new Color(255, 255, 255));
		panelTop.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 10px margin bottom
		lblSubject = new JLabel("Môn thi: ");
		lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblExamDate = new JLabel("Ngày thi: ");
		lblExamDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTotalCandidates = new JLabel("Tổng số thí sinh: ");
		lblTotalCandidates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblParticipated = new JLabel("Số lượng thí sinh dự thi: ");
		lblParticipated.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblResultStatus = new JLabel("Kết quả trung bình: ");
		lblResultStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));

		panelTop.add(lblSubject);
		panelTop.add(lblExamDate);
		panelTop.add(lblTotalCandidates);
		panelTop.add(lblParticipated);
		panelTop.add(lblResultStatus);

		// Bảng danh sách thí sinh
		model = new DefaultTableModel(new String[]{"UserName", "UserEmail", "UserFullName"}, 0);
		table = new JTable(model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		table.getTableHeader().setBackground(Color.WHITE);

		add(panelTop, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadExamData(testCode);
		setVisible(true);

	}

	private void loadExamData(String testCode) {
		String url = "jdbc:mysql://localhost:3306/tracnghiem";
		String user = "root";
		String password = "123456";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			// Lấy thông tin bài thi
			String queryTest = "SELECT testTitle, testDate FROM test WHERE testCode = ?";
			PreparedStatement stmtTest = conn.prepareStatement(queryTest);
			stmtTest.setString(1, testCode);
			ResultSet rsTest = stmtTest.executeQuery();
			if (rsTest.next()) {
				lblSubject.setText("Môn thi: " + rsTest.getString("testTitle"));
				lblExamDate.setText("Ngày thi: " + rsTest.getDate("testDate"));
			}

			// Lấy danh sách thí sinh
			String queryUsers = "SELECT u.userName, u.userEmail, u.userFullName, r.rs_mark " +
					"FROM users u JOIN result r ON u.userID = r.userID " +
					"WHERE r.exCode IN (SELECT exCode FROM exams WHERE testCode = ?)";
			PreparedStatement stmtUsers = conn.prepareStatement(queryUsers);
			stmtUsers.setString(1, testCode);
			ResultSet rsUsers = stmtUsers.executeQuery();

			int totalCandidates = 0;
			int participated = 0;
			int passed = 0;

			model.setRowCount(0);
			while (rsUsers.next()) {
				totalCandidates++;
				if (rsUsers.getInt("rs_mark") >= 0) { // Giả sử điểm >= 0 là có tham gia
					participated++;
					if (rsUsers.getInt("rs_mark") >= 50) {
						passed++;
					}
				}
				Vector<String> row = new Vector<>();
				row.add(rsUsers.getString("userName"));
				row.add(rsUsers.getString("userEmail"));
				row.add(rsUsers.getString("userFullName"));
				model.addRow(row);
			}

			lblTotalCandidates.setText("Tổng số thí sinh: " + totalCandidates);
			lblParticipated.setText("Số lượng thí sinh dự thi: " + participated);
			lblResultStatus.setText("Kết quả trung bình: " + (participated > 0 && passed * 100 / participated >= 50 ? "Trên 50%" : "Dưới 50%"));

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Test hiển thị JPanel trong JFrame
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->  new ExamCandidatesForm("test_001"));
	}
}
