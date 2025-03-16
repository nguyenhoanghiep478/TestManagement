package DAO.impl;

import DAO.ITopicDAO;
import Entity.Criteria;
import Entity.TopicEntity;
import Entity.UserEntity;
import Utils.Mapper.impl.TopicMapper;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;


public class TopicDAO extends AbstractDAO<TopicEntity> implements ITopicDAO {
    private final TopicMapper topicMapper=new TopicMapper();
    @Override
    public void delete(int testID) {
        String query = "UPDATE topics SET tpStatus = 0 WHERE tpID = ?";
        update(query, testID);
    }

    @Override
    public TopicEntity findBy(int testID) {
        String sql="select * from topics where tpStatus=?";
        return query(sql,topicMapper,1).stream().findFirst().orElse(null);
    }

    @Override
    public TopicEntity findByTitle(String name) {
        String sql="select * from topics where tpTitle=? and tpStatus=1";
        return query(sql,topicMapper,name).stream().findFirst().orElse(null);
    }

    public List<TopicEntity> findAll() {
        String sql="select * from topics where tpStatus=?";
        return query(sql,topicMapper,1);
    }
    @Override
    public long insert(TopicEntity result) {
        String query = """
            INSERT INTO topics 
            (tpTitle,tpParent,tpStatus)
            VALUES 
            (?, ?, ?);
        """;

        return save(query,
               result.getTpTitle(),
                result.getTpParent(),
                result.getTpStatus()
        );
    }
    @Override
    public void update(TopicEntity result) {
        String query = """
            UPDATE topics
            SET
              tpTitle=?,
              tpParent=?,
              tpStatus=?
            WHERE tpID = ?;
        """;

        update(query,
                result.getTpTitle(),
                result.getTpParent(),
                result.getTpStatus(),
                result.getTpID()
        );
    }


}
