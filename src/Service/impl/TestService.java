package Service.impl;

import DAO.ITestDAO;
import Entity.MyCustomExam;
import Entity.TestEntity;
import Service.ITestService;
import lombok.RequiredArgsConstructor;

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
    public List<String> getQuestions(String testCode) {
        return testDAO.getQuestions(testCode);
    }

    @Override
    public List<List<String>> getAnswers(String testCode) {
        return testDAO.getAnswers(testCode);
    }

    @Override
    public List<Integer> getCorrectAnswers(String testCode) {
        return testDAO.getCorrectAnswers(testCode);
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
    public MyCustomExam getExamByCode(String testCode, String exCode){
        return testDAO.getExamByCode(testCode,exCode);
    }


}
