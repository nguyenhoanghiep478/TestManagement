package DAO.impl;

import DAO.IGenericDAO;
import Entity.Criteria;
import Utils.Mapper.RowMapper;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> implements IGenericDAO<T> {
    private static final String URL = "jdbc:mysql://localhost:3306/tracnghiem";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof List) {
                List<?> list = (List<?>) parameters[i];
                for (Object item : list) {
                    statement.setObject(i + 1, item);
                }
            } else {
                statement.setObject(i + 1, parameters[i]);
            }
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> results = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void update(String sql, Object... parameters) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long save(String sql, Object... parameters) {
        long id = 0;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(statement, parameters);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int count(String sql, Object... parameters) {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, parameters);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<T> searchBy(List<Criteria> criteriaList, RowMapper<T> rowMapper, String tableName) {
        List<T> results = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE 1 = 1");
        List<Object> parameters = new ArrayList<>();

        for (Criteria criteria : criteriaList) {
            query.append(" AND ").append(criteria.getKey()).append(" ").append(criteria.getOperation()).append(" ?");
            parameters.add(criteria.getValue());
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            setParameters(statement, parameters.toArray());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public <R> R queryScalar(String sql, Class<R> type, Object... parameters) {
        R result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, parameters);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    result = rs.getObject(1, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

