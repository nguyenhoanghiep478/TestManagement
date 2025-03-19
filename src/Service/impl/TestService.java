package Service.impl;

import DAO.ITestDAO;
import DAO.impl.TestDAO;
import Entity.MyCustomExam;
import Entity.TestEntity;
import Service.ITestService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class TestService implements ITestService {
    private final ITestDAO testDAO;
    @Override
    public TestEntity getTestById(int id) {
        return null;
    }

    @Override
    public List<TestEntity> findAll() {
        return testDAO.findAll();
    }




    @Override
    public int getTestTime(String testCode) {
        return testDAO.getTestTime(testCode);
    }

    @Override
    public List<String> getQuestions(String testCode,String exCode) {
        return testDAO.getQuestions(testCode,exCode);
    }

    @Override
    public List<List<String>> getAnswers(String testCode,String exCode) {
        return testDAO.getAnswers(testCode,exCode);
    }

    @Override
    public List<Integer> getCorrectAnswers(String testCode,String exCode) {
        return testDAO.getCorrectAnswers(testCode,exCode);
    }

    @Override
    public void insertDefaultExams(String testCode) {
         testDAO.insertDefaultExamData();
    }

    @Override
    public void deleteExams(String testCode) {
        testDAO.deleteExamByTestCode(testCode);
    }

    @Override
    public void logAction(String content, int userId, String testCode) {
        testDAO.logAction(content, userId, testCode);
    }

    @Override
    public void saveResult(String answers, int userId, String testCode, double score) {
        testDAO.saveResult(userId, testCode, answers, score);
    }

    @Override
    public boolean isExistTest(String testCode,int topicId) {
        return testDAO.isExistTest(testCode,topicId);
    }

    @Override
    public void createTest(TestEntity test) {
        testDAO.createTestStructure(test);
        if(!testDAO.isExistTestCode(test.getTestCode())){
            testDAO.createTest(test);
        }
    }

    @Override
    public void updateTest(TestEntity test) {
        if(testDAO.isAlreadyUsingTest(test.getTestCode())){
            return;
        }

        testDAO.updateTest(test);
        testDAO.updateTestStructure(test);
    }

    public boolean deleteTest(int testId) {
        return testDAO.delete(testId);
    }

    @Override
    public List<String> getExCodes(String testCode) {
        return testDAO.getExCodes(testCode);
    }

    public MyCustomExam getExamByCode(String testCode, String exCode){
        return testDAO.getExamByCode(testCode,exCode);
    }


}
