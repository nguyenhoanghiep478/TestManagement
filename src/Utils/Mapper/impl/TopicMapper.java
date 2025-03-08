package Utils.Mapper.impl;

import Entity.ResultEntity;
import Entity.TopicEntity;
import Utils.Mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicMapper implements RowMapper<TopicEntity> {
    @Override
    public TopicEntity mapRow(ResultSet rs) throws SQLException {
        TopicEntity result = new TopicEntity();
        result.setTpID(rs.getInt("tpID"));
        result.setTpTitle(rs.getString("tpTitle"));
        result.setTpParent(rs.getInt("tpParent"));
        result.setTpStatus(rs.getInt("tpStatus"));
        return result;
    }
}
