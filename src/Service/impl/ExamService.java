package Service.impl;

import DAO.IExamDAO;
import Entity.ExamEntity;
import Service.IExamService;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ExamService implements IExamService {
    private final IExamDAO examDAO;

    @Override
    public List<ExamEntity> getAllExam() {
        return examDAO.getAll();
    }
}
