package DAO.impl;

import DAO.IGenericDAO;
import DAO.IResultDAO;
import Entity.Criteria;
import Entity.ResultEntity;
import Utils.Mapper.impl.ResultMapper;
import lombok.RequiredArgsConstructor;

import javax.xml.transform.Result;
import java.util.List;

@RequiredArgsConstructor
public class ResultDAO extends AbstractDAO<ResultEntity> implements IResultDAO {
    private final ResultMapper rowMapper;

    @Override
    public void delete(int rsNum) {
        String query = "DELETE FROM result WHERE rs_num = ?";
        update(query, rsNum);
    }

    @Override
    public ResultEntity findBy(int rsNum) {
        List<Criteria> criterias = List.of(new Criteria("rs_num", ":", rsNum));
        return searchBy(criterias, rowMapper, "result").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(ResultEntity result) {
        String query = """
            INSERT INTO result 
            (rs_num, userID, exCode, rs_anwsers, rs_mark, rs_date)
            VALUES 
            (?, ?, ?, ?, ?, ?);
        """;

        return save(query,
                result.getResultNum(),
                result.getUserId(),
                result.getExCode(),
                result.getRsAnswer(),
                result.getResMark(),
                result.getResTime()
        );
    }

    @Override
    public void update(ResultEntity result) {
        String query = """
            UPDATE result
            SET 
                userID = ?, 
                exCode = ?, 
                rs_anwsers = ?, 
                rs_mark = ?, 
                rs_date = ?
            WHERE rs_num = ?;
        """;

        update(query,
                result.getUserId(),
                result.getExCode(),
                result.getRsAnswer(),
                result.getResMark(),
                result.getResTime(),
                result.getResultNum()
        );
    }
}
