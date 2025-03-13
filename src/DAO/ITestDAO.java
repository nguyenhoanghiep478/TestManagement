package DAO;

import Entity.TestEntity;

import java.util.List;

public interface ITestDAO {
    void delete(int testID);
    TestEntity findBy(int testID);
    long insert(TestEntity test);
    void update(TestEntity test);
    int getTestTime(String testCode);
    List<String> getQuestions(String testCode);
    List<List<String>> getAnswers(String testCode);
    List<String> getAnswerOptions(int qId);
    List<Integer> getCorrectAnswers(String testCode);
    void insertDefaultExamData();
    void deleteExamByTestCode(String testCode);
    void logAction(String content,int userId,String testCode);
    void saveResult(int userID,String testCode,String resultAnswer,double mark);
}
