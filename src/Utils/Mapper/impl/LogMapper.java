package Utils.Mapper.impl;

import Entity.LogEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogMapper implements RowMapper<LogEntity> {
    @Override
    public LogEntity mapRow(ResultSet rs) throws SQLException {
        LogEntity result = new LogEntity();
        result.setLogId(rs.getInt("logId"));
        result.setLogContent(rs.getString("logContent"));
        result.setLogUserId(rs.getInt("logUserId"));
        result.setLogExId(rs.getInt("logExId"));
        result.setLogDate(rs.getTimestamp("logDate"));
        return result;
    }
}
