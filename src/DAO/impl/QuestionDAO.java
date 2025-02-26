package DAO.impl;

import DAO.IQuestDAO;
import Entity.Criteria;
import Entity.QuestionEntity;
import Utils.Mapper.impl.QuestionMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class QuestionDAO extends AbstractDAO<QuestionEntity> implements IQuestDAO {
    private  QuestionMapper rowMapper;
    @Override
    public void delete(int rsNum) {
        String query = "UPDATE questions SET qStatus = 0 WHERE qID = ?";
        update(query, rsNum);
    }

    @Override
    public QuestionEntity findBy(int rsNum) {
        List<Criteria> criterias = List.of(new Criteria("qID", ":", rsNum));
        return searchBy(criterias, rowMapper, "questions").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(QuestionEntity result) {
        String query = """
            INSERT INTO questions
            (qContent, qPictures, qTopicID, qLevel, qStatus)
            VALUES
            (?, ?, ?, ?, ?);
        """;

        return save(query,
                result.getqContent(),
                result.getqPictures(),
                result.getqTopicID(),
                result.getqLevel(),
                result.getqStatus()
        );
    }
    @Override
    public void update(QuestionEntity result) {
        String query = """
            UPDATE questions
            SET
                qContent = ?,
                qPictures = ?, 
                qTopicID = ?, 
                qLevel = ?, 
                qStatus = ?
            WHERE qID = ?;
        """;

        update(query,
                result.getqContent(),
                result.getqPictures(),
                result.getqTopicID(),
                result.getqLevel(),
                result.getqStatus(),
                result.getqID()
        );
    }
}
