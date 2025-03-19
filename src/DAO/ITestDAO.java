package DAO;

import Entity.MyCustomExam;
import Entity.TestEntity;

import java.util.List;

public interface ITestDAO {
    boolean delete(int testID);
    TestEntity findBy(int testID);
    long insert(TestEntity test);
    void update(TestEntity test);
    int getTestTime(String testCode);
    List<String> getQuestions(String testCode,String exCode);
    List<List<String>> getAnswers(String testCode,String exCode);
    List<String> getAnswerOptions(int qId);
    List<Integer> getCorrectAnswers(String testCode,String exCode);
    void insertDefaultExamData();
    void deleteExamByTestCode(String testCode);
    void logAction(String content,int userId,String testCode);
    void saveResult(int userID,String testCode,String resultAnswer,double mark);

    List<TestEntity> findAll();

    boolean isExistTest(String testCode, int topicId);

    void createTestStructure(TestEntity test);

    void createTest(TestEntity test);

    boolean isExistTestCode(String testCode);

    boolean isAlreadyUsingTest(String testCode);

    void updateTest(TestEntity test);

    void updateTestStructure(TestEntity test);
    MyCustomExam getExamByCode(String testCode, String exCode);

    List<String> getExCodes(String testCode);
}
