package DAO;

import Entity.ResultEntity;
import Entity.TopicEntity;

public interface ITopicDAO {
    void delete(int testID);
    TopicEntity findBy(int testID);
    long insert(TopicEntity result);
    void update(TopicEntity result);
}
