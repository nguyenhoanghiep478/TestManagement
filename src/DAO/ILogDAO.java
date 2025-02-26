package DAO;

import Entity.LogEntity;

public interface ILogDAO {
    void delete(int logID);
    LogEntity findBy(int logID);
    long insert(LogEntity logs);
    void update(LogEntity logs);
}
