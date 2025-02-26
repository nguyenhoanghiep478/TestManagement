package DAO;

import Entity.QuestionEntity;
import Entity.ResultEntity;

public interface IQuestDAO {
    void delete(int questID);
    QuestionEntity findBy(int questID);
    long insert(QuestionEntity result);
    void update(QuestionEntity result);
}
