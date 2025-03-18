package Service;

import Entity.ExamEntity;

import java.util.List;

public interface IExamService {
    List<ExamEntity> getAllExam();
    void generateExams(String testCode,int amount);
}
