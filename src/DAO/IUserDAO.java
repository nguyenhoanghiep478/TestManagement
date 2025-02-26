package DAO;

import Entity.ResultEntity;
import Entity.UserEntity;

public interface IUserDAO {
    void delete(int testID);
    UserEntity findBy(int testID);
    long insert(UserEntity result);
    void update(UserEntity result);
}
