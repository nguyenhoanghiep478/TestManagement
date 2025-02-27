package Utils.Mapper.impl;

import Entity.ExamEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamMapper implements RowMapper<ExamEntity> {
    @Override
    public ExamEntity mapRow(ResultSet rs) throws SQLException {
        ExamEntity result = new ExamEntity();
        result.setTestCode(rs.getString("testCode"));
        result.setExOrder(rs.getString("exOrder"));
        result.setExCode(rs.getString("exCode"));
        result.setEx_quesId(rs.getString("ex_quesId"));
        return result;
    }
}
