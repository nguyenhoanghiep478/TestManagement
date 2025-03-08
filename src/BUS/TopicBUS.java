package BUS;

import DAO.ITopicDAO;
import DAO.impl.TopicDAO;
import Entity.TopicEntity;

import java.util.List;

public class TopicBUS implements ITopicBUS{
    private final ITopicDAO topicDAO=new TopicDAO();
    @Override
    public TopicEntity findTopicById(int testID) {
        return topicDAO.findBy(testID);
    }

    @Override
    public List<TopicEntity> getAllTopic() {
        return topicDAO.findAll();
    }

    @Override
    public long createTopic(TopicEntity result) {
        return topicDAO.insert(result);
    }

    @Override
    public void updateTopic(TopicEntity result) {
           topicDAO.update(result);
    }

    @Override
    public void deleteTopic(int userId) {
        topicDAO.delete(userId);
    }

    @Override
    public boolean isTopicExist(String title) {
        return topicDAO.findByTitle(title)!=null;
    }

    @Override
    public TopicEntity findTopicByTitle(String title) {
        return topicDAO.findByTitle(title);
    }
}
