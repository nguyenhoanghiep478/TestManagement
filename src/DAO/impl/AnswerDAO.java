package DAO.impl;

import DAO.IAnswerDAO;
import DAO.ITestDAO;
import Entity.AnswerEntity;
import Entity.Criteria;
import Entity.QuestionEntity;
import Entity.TestEntity;
import Utils.Mapper.impl.AnswerMapper;
import Utils.Mapper.impl.TestMapper;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
public class AnswerDAO  extends AbstractDAO<AnswerEntity> implements IAnswerDAO {
    private  AnswerMapper rowMapper=new AnswerMapper();

    @Override
    public void delete(int testID) {
        String query = "UPDATE answers SET awStatus = 0 WHERE awID = ?";
        update(query, testID);
    }

    @Override
    public AnswerEntity findBy(int testID) {
        List<Criteria> criterias = List.of(new Criteria("awID", "=", testID));
        return searchBy(criterias, rowMapper, "answers").stream().findFirst().orElse(null);
    }
    @Override
    public List<AnswerEntity> findAll() {
        return searchBy(Collections.emptyList(),rowMapper, "answers");
    }
    @Override
    public long insert(AnswerEntity test) {
        String query = """
            INSERT INTO answers
            (qID,awContent, awPictures,isRight, awStatus)
            VALUES
            (?, ?, ?, ?, ?);
        """;

        return save(query,
                test.getqID(),
                test.getAwContent(),
                test.getAwPictures(),
                test.getIsRight(),
                test.getAwStatus()
        );
    }

    @Override
    public void update(AnswerEntity test) {
        String query = """
            UPDATE answers
            SET
                qID=?,
                awContent = ?,
                awPictures = ?, 
                isRight = ?,  
                awStatus = ?
            WHERE awID = ?;
        """;

        update(query,
                test.getqID(),
                test.getAwContent(),
                test.getAwPictures(),
                test.getIsRight(),
                test.getAwStatus(),
                test.getAwID()
        );
    }

    public List<AnswerEntity> findByQuestID(int qId) {
        String sql="select * from answers  where qId=?";
        return query(sql,rowMapper,qId);
    }
}
