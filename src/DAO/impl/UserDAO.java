package DAO.impl;

import DAO.IUserDAO;
import Entity.Criteria;
import Entity.TopicEntity;
import Entity.UserEntity;
import Utils.Mapper.impl.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class UserDAO extends AbstractDAO<UserEntity> implements IUserDAO {
    private final UserMapper userMapper=new UserMapper();
    @Override
    public void delete(int testID) {
        String query = "DELETE FROM users WHERE userID = ?";
        update(query, testID);
    }

    @Override
    public UserEntity findBy(int testID) {
        List<Criteria> criterias = List.of(new Criteria("userID", "=", testID));
        return searchBy(criterias, userMapper, "users").stream().findFirst().orElse(null);
    }
    public UserEntity findByName(String name) {
        List<Criteria> criterias = List.of(new Criteria("userName", "=", name));
        return searchBy(criterias, userMapper, "users").stream().findFirst().orElse(null);
    }
    public List<UserEntity> findAll() {
        return searchBy(Collections.emptyList(),userMapper, "users");
    }
    @Override
    public long insert(UserEntity result) {
        String query = """
            INSERT INTO users 
            (userName, userEmail,userPassword,userFullName,isAdmin)
            VALUES 
            (?, ?, ?, ?,?);
        """;

        return save(query,
               result.getUserName(),
                result.getUserEmail(),
                result.getUserPassword(),
                result.getUserFullName(),
                result.getIsAdmin()
        );
    }

    @Override
    public void update(UserEntity result) {
        String query = """
            UPDATE users
            SET 
                userName = ?, 
                userEmail = ?, 
                userPassword = ?, 
                userFullName = ?, 
                isAdmin = ?
            WHERE userID = ?;
        """;

        update(query,
                result.getUserName(),
                result.getUserEmail(),
                result.getUserPassword(),
                result.getUserFullName(),
                result.getIsAdmin(),
                result.getUserID()
        );
    }
}

