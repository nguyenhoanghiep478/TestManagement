package BUS;

import DAO.impl.UserDAO;
import Entity.UserEntity;

import java.util.List;

public class UserBUS implements IUserBUS{
    private final UserDAO userDAO=new UserDAO();
    @Override
    public UserEntity findUserById(int testID) {
        return userDAO.findBy(testID);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userDAO.findAll();
    }

    @Override
    public boolean createUser(UserEntity result) {
        userDAO.insert(result);
        return true;
    }

    @Override
    public boolean updateUser(UserEntity result) {
        userDAO.update(result);
        return true;
    }

    @Override
    public boolean deleteUser(int userId) {
        userDAO.delete(userId);
        return true;
    }
    @Override
    public boolean isUserExist(String name){
        return userDAO.findByName(name)!=null;
    }
    @Override
    public boolean login(String userName,String password){
        UserEntity userEntity=userDAO.findByName(userName);
        if(userEntity==null) return false;
        return userEntity.getUserPassword().equals(password);
    }
}
