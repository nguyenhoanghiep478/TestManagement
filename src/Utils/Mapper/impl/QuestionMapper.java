package Utils.Mapper.impl;

import Entity.QuestionEntity;
import Entity.ResultEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper  implements RowMapper<QuestionEntity> {
    @Override
    public QuestionEntity mapRow(ResultSet rs) throws SQLException {
        QuestionEntity result=new QuestionEntity();
        result.setqID(rs.getInt("qID"));
        result.setqContent(rs.getString("qContent"));
        result.setqPictures(rs.getString("qPictures"));
        result.setqTopicID(rs.getInt("qTopicID"));
        result.setqLevel(rs.getString("qLevel"));
        result.setqStatus(rs.getInt("qStatus"));
        return result;
    }
}
