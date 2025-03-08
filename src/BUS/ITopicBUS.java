package BUS;

import Entity.TopicEntity;
import Entity.UserEntity;

import java.util.List;

public interface ITopicBUS {
    TopicEntity findTopicById(int testID);
    List<TopicEntity> getAllTopic();
    long createTopic(TopicEntity result);
    void updateTopic(TopicEntity result);
    void deleteTopic(int userId);
    boolean isTopicExist(String name);
    TopicEntity findTopicByTitle(String title);
}
