package DAO.impl;

import DAO.IGenericDAO;
import DAO.ILogDAO;
import Entity.Criteria;
import Entity.LogEntity;
import Utils.Mapper.impl.LogMapper;
import lombok.RequiredArgsConstructor;


import java.util.List;

@RequiredArgsConstructor
public class LogDAO extends AbstractDAO<LogEntity> implements ILogDAO {
    private final LogMapper rowMapper;

    @Override
    public void delete(int logId) {
        String query = "DELETE FROM logs WHERE logId = ?";
        update(query, logId);
    }

    @Override
    public LogEntity findBy(int logId) {
        List<Criteria> criterias = List.of(new Criteria("logId", ":", logId));
        return searchBy(criterias, rowMapper, "logs").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(LogEntity log) {
        String query = """
            INSERT INTO logs 
            (logId, logContent, logUserId, logExId, logDate)
            VALUES 
            (?, ?, ?, ?, ?);
        """;

        return save(query,
                log.getLogId(),
                log.getLogContent(),
                log.getLogUserId(),
                log.getLogExId(),
                log.getLogDate()
        );
    }

    @Override
    public void update(LogEntity log) {
        String query = """
            UPDATE result
            SET 
                logContent = ?, 
                logUserId = ?, 
                logExId = ?, 
                logDate = ?
            WHERE logId = ?;
        """;

        update(query,
                log.getLogContent(),
                log.getLogUserId(),
                log.getLogExId(),
                log.getLogDate()
        );
    }
}
