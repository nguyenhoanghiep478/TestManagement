package DAO;

import Entity.TestEntity;

public interface ITestDAO {
    void delete(int testID);
    TestEntity findBy(int testID);
    long insert(TestEntity test);
    void update(TestEntity test);
}
