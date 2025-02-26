package Utils.Mapper.impl;

import Entity.AnswerEntity;
import Entity.QuestionEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<AnswerEntity> {
    @Override
    public AnswerEntity mapRow(ResultSet rs) throws SQLException {
        AnswerEntity result = new AnswerEntity();
        result.setAwID(rs.getInt("awID"));
        result.setAwContent(rs.getString("awContent"));
        result.setAwPictures(rs.getString("awPictures"));
        result.setIsRight(rs.getInt("isRight"));
        result.setAwStatus(rs.getInt("awStatus"));
        result.setqID(rs.getInt("qID"));
        return result;
    }
}