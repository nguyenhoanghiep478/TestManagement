package DAO;

import Entity.ResultEntity;


public interface IResultDAO {
    void delete(int testID);
    ResultEntity findBy(int testID);
    long insert(ResultEntity result);
    void update(ResultEntity result);
}
