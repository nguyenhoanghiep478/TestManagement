package Service;

import Entity.TestEntity;

import java.util.List;

public interface ITestService {
    TestEntity getTestById(int id);
    List<TestEntity> findAll();
    int getTestTime(String testCode);
    List<String> getQuestions(String testCode,String exCode);
    List<List<String>> getAnswers(String testCode,String exCode);
    List<Integer> getCorrectAnswers(String testCode,String exCode);
    void insertDefaultExams(String testCode);
    void deleteExams(String testCode);
    void logAction(String content,int userId,String testCode);
    void saveResult(String answers,int userId,String testCode,double score);

    boolean isExistTest(String testCode,int topicId);

    void createTest(TestEntity test);

    void updateTest(TestEntity test);

    List<String> getExCodes(String testCode);
}
