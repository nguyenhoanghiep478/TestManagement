package DAO;

import DAO.impl.TopicDAO;
import Entity.ResultEntity;
import Entity.TopicEntity;

import java.util.List;

public interface ITopicDAO {
    void delete(int testID);
    TopicEntity findBy(int testID);
    TopicEntity findByTitle(String name);
    long insert(TopicEntity result);
    void update(TopicEntity result);
    List<TopicEntity>findAll();
}
