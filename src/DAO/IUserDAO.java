package DAO;

import Entity.ResultEntity;
import Entity.TopicEntity;
import Entity.UserEntity;

import java.util.List;

public interface IUserDAO {
    void delete(int testID);
    UserEntity findBy(int testID);
    long insert(UserEntity result);
    void update(UserEntity result);
    List<UserEntity> findAll();
}
