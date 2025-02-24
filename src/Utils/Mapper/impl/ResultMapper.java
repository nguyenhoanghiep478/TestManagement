package Utils.Mapper.impl;

import Entity.ResultEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultMapper implements RowMapper<ResultEntity> {
    @Override
    public ResultEntity mapRow(ResultSet rs) throws SQLException {
        ResultEntity result = new ResultEntity();
        result.setResultNum(rs.getInt("rs_num"));
        result.setUserId(rs.getInt("userID"));
        result.setExCode(rs.getString("exCode"));
        result.setRsAnswer(rs.getString("rs_anwsers"));
        result.setResMark(rs.getBigDecimal("rs_mark"));
        result.setResTime(rs.getTimestamp("rs_date"));
        return result;
    }
}
