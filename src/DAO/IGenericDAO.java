package DAO;

import Entity.Criteria;
import Utils.Mapper.RowMapper;

import java.util.List;

public interface IGenericDAO<T>{
    List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
    void update(String sql, Object... parameters);
    long save(String sql, Object... parameters);
    int count(String sql, Object... parameters);
    List<T> searchBy(List<Criteria> by, RowMapper<T> rowMapper, String tableName);
}
