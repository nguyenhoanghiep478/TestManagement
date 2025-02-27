package Utils.Mapper.impl;

import Entity.LogEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogMapper implements RowMapper<LogEntity> {
    @Override
    public LogEntity mapRow(ResultSet rs) throws SQLException {
        LogEntity log = new LogEntity();
        log.setLogId(rs.getInt("logId"));
        log.setLogContent(rs.getString("logContent"));
        log.setLogUserId(rs.getInt("logUserId"));
        log.setLogExId(rs.getInt("logExId"));
        log.setLogDate(rs.getTimestamp("logDate"));
        return log;
    }
}
