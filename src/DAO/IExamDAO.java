package DAO;

import Entity.ExamEntity;

public interface IExamDAO {
    void delete(String exCode);
    ExamEntity findBy(String exCode);
    long insert(ExamEntity exam);
    void update(ExamEntity exam);
}
