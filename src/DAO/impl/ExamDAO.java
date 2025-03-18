package DAO.impl;

import DAO.IGenericDAO;
import DAO.IExamDAO;
import Entity.Criteria;
import Entity.ExamEntity;
import Utils.Mapper.impl.ExamMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExamDAO extends AbstractDAO<ExamEntity> implements IExamDAO {
    private final ExamMapper rowMapper;

    @Override
    public void delete(String examCode) {
        String query = "DELETE FROM exams WHERE examCode = ?";
        update(query, examCode);
    }

    @Override
    public ExamEntity findBy(String examCode) {
        List<Criteria> criterias = List.of(new Criteria("examCode", ":", examCode));
        return searchBy(criterias, rowMapper, "exams").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(ExamEntity exam) {
        String query = """
            INSERT INTO exams 
            (testCode, exOrder, exCode, ex_quesId)
            VALUES 
            (?, ?, ?, ?);
        """;

        return save(query,
                exam.getTestCode(),
                exam.getExOrder(),
                exam.getExCode(),
                exam.getEx_quesId()
        );
    }

    @Override
    public void update(ExamEntity exam) {
        String query = """
            UPDATE exams
            SET 
                testCode = ?, 
                exOrder = ?, 
                ex_quesId = ?
            WHERE exCode = ?;
        """;

        update(query,
                exam.getTestCode(),
                exam.getExOrder(),
                exam.getEx_quesId()
        );
    }

    @Override
    public List<ExamEntity> getAll() {
        return searchBy(null, rowMapper, "exams");
    }

    @Override
    public void generateExams(String testCode) {
        try (Connection conn = getConnection()) {
            String query = "SELECT tpID, num_easy, num_medium, num_diff FROM test_structure WHERE testCode = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, testCode);
            ResultSet rs = stmt.executeQuery();

            List<Integer> selectedQuestions = new ArrayList<>();

            while (rs.next()) {
                int topicID = rs.getInt("tpID");
                int numEasy = rs.getInt("num_easy");
                int numMedium = rs.getInt("num_medium");
                int numDiff = rs.getInt("num_diff");

                selectedQuestions.addAll(selectRandomQuestions(conn, topicID, "easy", numEasy));
                selectedQuestions.addAll(selectRandomQuestions(conn, topicID, "medium", numMedium));
                selectedQuestions.addAll(selectRandomQuestions(conn, topicID, "diff", numDiff));
            }

            // Lưu danh sách câu hỏi vào bảng exams
            saveExam(conn, testCode, selectedQuestions);

            System.out.println("Đề thi đã được tạo thành công! " + selectedQuestions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> selectRandomQuestions(Connection conn, int topicID, String level, int num) throws SQLException {
        List<Integer> questionList = new ArrayList<>();
        String query = "SELECT qID FROM questions WHERE qTopicID = ? AND qLevel = ? AND qStatus = 1 ORDER BY RAND() LIMIT ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, topicID);
        stmt.setString(2, level);
        stmt.setInt(3, num);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            questionList.add(rs.getInt("qID"));
        }
        return questionList;
    }

    private static void saveExam(Connection conn, String testCode, List<Integer> questionIDs) throws SQLException {
        String insertQuery = "INSERT INTO exams (testCode, exOrder, exCode, ex_quesIDs) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertQuery);
        stmt.setString(1, testCode);
        stmt.setInt(2, 1);
        stmt.setString(3, "EX" + testCode + "1");

        String questionIDsString = "[" + questionIDs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
        stmt.setString(4, questionIDsString);

        stmt.executeUpdate();
    }
}
