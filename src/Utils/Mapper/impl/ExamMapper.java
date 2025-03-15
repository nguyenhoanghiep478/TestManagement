package Utils.Mapper.impl;

import Entity.ExamEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamMapper implements RowMapper<ExamEntity> {
    @Override
    public ExamEntity mapRow(ResultSet rs) throws SQLException {
        ExamEntity exam = new ExamEntity();
        exam.setTestCode(rs.getString("testCode"));
        exam.setExOrder(rs.getString("exOrder"));
        exam.setExCode(rs.getString("exCode"));
        exam.setEx_quesId(rs.getString("ex_quesIDs"));
        return exam;
    }
}
