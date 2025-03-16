package DAO.impl;

import DAO.ITestDAO;
import Entity.Criteria;
import Entity.MyCustomExam;
import Entity.TestEntity;
import Utils.Mapper.impl.TestMapper;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TestDAO extends AbstractDAO<TestEntity> implements ITestDAO {
    private final TestMapper rowMapper;

    @Override
    public void delete(int testID) {
        String query = "UPDATE test SET testStatus = 0 WHERE testID = ?";
        update(query, testID);
    }

    @Override
    public TestEntity findBy(int testID) {
        List<Criteria> criterias = List.of(new Criteria("testID", ":", testID));
        return searchBy(criterias, rowMapper, "test").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(TestEntity test) {
        String query = """
            INSERT INTO test 
            (testID, testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus)
            VALUES 
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        return save(query,
                test.getTestId(),
                test.getTestCode(),
                test.getTestTitle(),
                test.getTestTime(),
                test.getTopicId(),
                test.getNumEasy(),
                test.getNumMedium(),
                test.getNumDiff(),
                test.getTestLimit(),
                test.getTestDate(),
                test.getTestStatus()
        );
    }

    @Override
    public void update(TestEntity test) {
        String query = """
            UPDATE test
            SET 
                testCode = ?, 
                testTilte = ?, 
                testTime = ?, 
                tpID = ?, 
                num_easy = ?, 
                num_medium = ?, 
                num_diff = ?, 
                testLimit = ?, 
                testDate = ?, 
                testStatus = ?
            WHERE testID = ?;
        """;

        update(query,
                test.getTestCode(),
                test.getTestTitle(),
                test.getTestTime(),
                test.getTopicId(),
                test.getNumEasy(),
                test.getNumMedium(),
                test.getNumDiff(),
                test.getTestLimit(),
                test.getTestDate(),
                test.getTestStatus(),
                test.getTestId()
        );
    }

    @Override
    public int getTestTime(String testCode) {
        String sql = "SELECT testTime FROM test WHERE testCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("testTime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void deleteExamByTestCode(String testCode) {
        String sql = "DELETE FROM exams WHERE testCode = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testCode);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " exam(s) with testCode: " + testCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logAction(String content, int userId,String testCode) {
        String sql = "INSERT INTO logs (logContent, logUserID, logExCode, logDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, content);
            stmt.setInt(2, userId);
            stmt.setString(3, testCode);
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveResult(int userID, String testCode,String resultAnswer, double mark) {
        String sql = "INSERT INTO result (rs_num, userID, exCode, rs_anwsers, rs_mark, rs_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, userID);
            stmt.setString(3, testCode);
            stmt.setString(4, resultAnswer);
            stmt.setDouble(5, mark);
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TestEntity> findAll() {
        String sql = "SELECT testId,ts.testCode,testTitle,testTime,tpId,num_easy,num_medium,num_diff,testLimit,testDate,testStatus FROM tracnghiem.test left join test_structure as ts on test.testCode = ts.testCode";
        return query(sql,rowMapper);
    }

    @Override
    public boolean isExistTest(String testCode, int topicId) {
        String sql = "SELECT * FROM test_structure WHERE testCode = ? and tpID = ?";
        return query(sql,rowMapper,testCode,topicId)!=null;
    }

    @Override
    public void createTestStructure(TestEntity test) {
        String sql = "Insert into test_structure (testCode,tpID,num_easy,num_medium,num_diff) values (?,?,?,?,?)";
        save(sql,test.getTestCode(),test.getTopicId(),test.getNumEasy(),test.getNumMedium(),test.getNumDiff());
    }

    @Override
    public void createTest(TestEntity test) {
        String sql = "INSERT INTO test (testCode,testTitle,testTime,testLimit,testDate,testStatus) values (?,?,?,?,?,?)";
        save(sql,test.getTestCode(),test.getTestTitle(),test.getTestTime(),test.getTestLimit(),test.getTestDate(),test.getTestStatus());
    }

    @Override
    public boolean isExistTestCode(String testCode) {
        String sql = "SELECT * FROM test WHERE testCode = ? ";
        return query(sql,rowMapper,testCode)!=null;
    }

    @Override
    public boolean isAlreadyUsingTest(String testCode) {
        String sql = "SELECT * FROM test as t where t.testCode not in (select testCode from exams) and testCode = ? ";
        return query(sql,rowMapper,testCode)==null;
    }

    @Override
    public void updateTest(TestEntity test) {
        String sql = "Update test set testTitle = ?,testTime = ?,testLimit = ? where testID = ? ";
        update(sql,test.getTestTitle(),test.getTestTime(),test.getTestLimit(),test.getTestId());
    }

    @Override
    public void updateTestStructure(TestEntity test) {
        String sql = "Update test set num_easy = ?, num_medium = ?, num_diff = ? where testCode = ? and tpID= ?";
        update(sql,test.getNumEasy(),test.getNumMedium(),test.getNumDiff(),test.getTestCode(),test.getTopicId());
    }

    public void insertDefaultExamData() {
        String checkQuery = "SELECT COUNT(*) FROM exams";
        String insertQuery = "INSERT INTO exams (testCode, exOrder, exCode, ex_quesIDs) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             Statement checkStmt = conn.createStatement();
             ResultSet rs = checkStmt.executeQuery(checkQuery)) {

            if (rs.next() && rs.getInt(1) == 0) { // Nếu chưa có dữ liệu trong bảng exams
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, "test_001");
                    insertStmt.setString(2, "A");
                    insertStmt.setString(3, "test_001A");
                    insertStmt.setString(4, "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]"); // Danh sách câu hỏi JSON
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<String> getQuestions(String testCode) {
        List<String> questions = new ArrayList<>();
        String sql = "SELECT qContent FROM questions " +
                "WHERE FIND_IN_SET(qID, (SELECT ex_quesIDs FROM exams WHERE testCode = ?))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(rs.getString("qContent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    private List<Integer> getQuestionIDs(String testCode) {
        List<Integer> questionIDs = new ArrayList<>();
        String sql = "SELECT qID FROM questions WHERE FIND_IN_SET(qID, (SELECT ex_quesIDs FROM exams WHERE testCode = ?))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questionIDs.add(rs.getInt("qID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionIDs;
    }

    @Override
    public List<List<String>> getAnswers(String testCode) {
        List<List<String>> options = new ArrayList<>();
        List<Integer> questionIDs = getQuestionIDs(testCode);
        for (int qID : questionIDs) {
            options.add(getAnswerOptions(qID));
        }
        return options;
    }

    @Override
    public List<String> getAnswerOptions(int qID) {
        List<String> answers = new ArrayList<>();
        String sql = "SELECT awContent FROM answers WHERE qID = ? ORDER BY awID LIMIT 4";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                answers.add(rs.getString("awContent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public List<Integer> getCorrectAnswers(String testCode) {
        List<Integer> correctAnswersList = new ArrayList<>();
        List<Integer> questionIDs = getQuestionIDs(testCode);
        for (int qID : questionIDs) {
            correctAnswersList.add(getCorrectAnswerIndex(qID));
        }
        return correctAnswersList;
    }

    public int getCorrectAnswerIndex(int qID) {
        String sql = "SELECT awID FROM answers WHERE qID = ? AND isRight = 1 ORDER BY awID LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("awID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public  MyCustomExam getExamByCode(String testCode, String exCode)  {
        MyCustomExam myCustomExam=null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT t.testTitle, t.testDate, t.testTime, e.ex_quesIDs " +
                    "FROM test t JOIN exams e ON t.testCode = e.testCode " +
                    "WHERE t.testCode = ? AND e.exCode = ?";
           PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, testCode);
                ps.setString(2, exCode);
                ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        myCustomExam = new MyCustomExam(exCode,rs.getString("testTitle"),rs.getInt("testTime"), rs.getDate("testDate"), rs.getString("ex_quesIDs"));
                    }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return myCustomExam;
    }
}
