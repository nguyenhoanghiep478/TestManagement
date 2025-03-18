package DAO;

import Entity.ExamEntity;

import java.util.List;

public interface IExamDAO {
    void delete(String exCode);
    ExamEntity findBy(String exCode);
    long insert(ExamEntity exam);
    void update(ExamEntity exam);
    List<ExamEntity> getAll();
    void generateExams(String testCode);
}
