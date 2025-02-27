package DAO;

import Entity.LogEntity;

public interface ILogDAO {
    void delete(int logID);
    LogEntity findBy(int logID);
    long insert(LogEntity log);
    void update(LogEntity log);
}
