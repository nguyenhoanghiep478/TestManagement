package DAO;

import Entity.AnswerEntity;
import Entity.ResultEntity;

public interface IAnswerDAO {
    void delete(int testID);
    AnswerEntity findBy(int testID);
    long insert(AnswerEntity result);
    void update(AnswerEntity result);
}
