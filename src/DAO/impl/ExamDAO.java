package DAO.impl;

import DAO.IGenericDAO;
import DAO.IExamDAO;
import Entity.Criteria;
import Entity.ExamEntity;
import Utils.Mapper.impl.ExamMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExamDAO extends AbstractDAO<ExamEntity> implements IExamDAO {
    private final ExamMapper rowMapper;

    @Override
    public void delete(String examCode) {
        String query = "DELETE FROM exams WHERE examCode = ?";
        update(query, examCode);
    }

    @Override
    public ExamEntity findBy(String examCode) {
        List<Criteria> criterias = List.of(new Criteria("examCode", ":", examCode));
        return searchBy(criterias, rowMapper, "exams").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(ExamEntity exam) {
        String query = """
            INSERT INTO exams 
            (testCode, exOrder, exCode, ex_quesId)
            VALUES 
            (?, ?, ?, ?);
        """;

        return save(query,
                exam.getTestCode(),
                exam.getExOrder(),
                exam.getExCode(),
                exam.getEx_quesId()
        );
    }

    @Override
    public void update(ExamEntity exam) {
        String query = """
            UPDATE exams
            SET 
                testCode = ?, 
                exOrder = ?, 
                ex_quesId = ?
            WHERE exCode = ?;
        """;

        update(query,
                exam.getTestCode(),
                exam.getExOrder(),
                exam.getEx_quesId()
        );
    }

    @Override
    public List<ExamEntity> getAll() {
        return searchBy(null, rowMapper, "exams");
    }
}
