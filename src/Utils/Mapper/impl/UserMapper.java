package Utils.Mapper.impl;

import Entity.ResultEntity;
import Entity.UserEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet rs) throws SQLException {
        UserEntity result = new UserEntity();
        result.setUserID(rs.getInt("userID"));
        result.setUserName(rs.getString("userName"));
        result.setUserEmail(rs.getString("userEmail"));
        result.setUserPassword(rs.getString("userPassword"));
        result.setUserFullName(rs.getString("userFullName"));
        return result;
    }
}
