package BUS;

import Entity.UserEntity;

import java.util.List;

public interface IUserBUS {
    UserEntity findUserById(int testID);
    List<UserEntity>getAllUser();
    boolean createUser(UserEntity result);
    boolean updateUser(UserEntity result);
    boolean deleteUser(int userId);
    boolean isUserExist(String name);
    boolean login(String userName,String password);
}
