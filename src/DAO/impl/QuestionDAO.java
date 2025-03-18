package DAO.impl;

import DAO.IQuestDAO;
import Entity.Criteria;
import Entity.QuestionEntity;
import Utils.Mapper.impl.QuestionMapper;
import lombok.RequiredArgsConstructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@RequiredArgsConstructor
public class QuestionDAO extends AbstractDAO<QuestionEntity> implements IQuestDAO {
    private  QuestionMapper rowMapper=new QuestionMapper();
    @Override
    public void delete(int questID) {
        String deleteAnswersQuery = "DELETE FROM answers WHERE qID = ?";
        String deleteQuestionQuery = "DELETE FROM questions WHERE qID = ?";
        update(deleteAnswersQuery, questID);
        update(deleteQuestionQuery, questID);
    }

    @Override
    public QuestionEntity findBy(int rsNum) {
        List<Criteria> criterias = List.of(new Criteria("qID", "=", rsNum));
        return searchBy(criterias, rowMapper, "questions").stream().findFirst().orElse(null);
    }
    @Override
    public List<QuestionEntity> findAll() {
        return searchBy(Collections.emptyList(),rowMapper, "questions");
    }
    public List<QuestionEntity>findAllInId(String listId){
        List<QuestionEntity>l=new ArrayList<>();
        List<String> idList = Arrays.asList(listId.split(","));
        String placeholders = String.join(",", idList.stream().map(id -> "?").toArray(String[]::new));

        String sql = "SELECT * FROM questions WHERE qID IN (" + placeholders + ")";
        try{
            Connection con=getConnection();
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            for (int i = 0; i < idList.size(); i++) {
                preparedStatement.setInt(i + 1, Integer.parseInt(idList.get(i).trim()));
            }
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                l.add(rowMapper.mapRow(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
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
