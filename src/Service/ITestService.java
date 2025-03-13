package Service;

import Entity.TestEntity;

import java.util.List;

public interface ITestService {
    TestEntity getTestById(int id);
    List<TestEntity> findAll();
    int getTestTime(String testCode);
    List<String> getQuestions(String testCode);
    List<List<String>> getAnswers(String testCode);
    List<Integer> getCorrectAnswers(String testCode);
    void insertDefaultExams(String testCode);
    void deleteExams(String testCode);
    void logAction(String content,int userId,String testCode);
    void saveResult(String answers,int userId,String testCode,double score);
}
