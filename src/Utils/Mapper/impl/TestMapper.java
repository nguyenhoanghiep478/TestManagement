package Utils.Mapper.impl;

import Entity.TestEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper implements RowMapper<TestEntity> {
    @Override
    public TestEntity mapRow(ResultSet rs) throws SQLException {
        TestEntity test = new TestEntity();
        test.setTestId(rs.getInt("testID"));
        test.setTestCode(rs.getString("testCode"));
        test.setTestTitle(rs.getString("testTilte"));
        test.setTestTime(rs.getInt("testTime"));
        test.setTopicId(rs.getInt("tpID"));
        test.setNumEasy(rs.getInt("num_easy"));
        test.setNumMedium(rs.getInt("num_medium"));
        test.setNumDiff(rs.getInt("num_diff"));
        test.setTestLimit(rs.getInt("testLimit"));
        test.setTestDate(rs.getDate("testDate"));
        test.setTestStatus(rs.getInt("testStatus"));
        return test;
    }
}
