package DAO.impl;

import DAO.ITestDAO;
import Entity.Criteria;
import Entity.TestEntity;
import Utils.Mapper.impl.TestMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestDAO extends AbstractDAO<TestEntity> implements ITestDAO {
    private final TestMapper rowMapper;

    @Override
    public void delete(int testID) {
        String query = "UPDATE test SET testStatus = 0 WHERE testID = ?";
        update(query, testID);
    }

    @Override
    public TestEntity findBy(int testID) {
        List<Criteria> criterias = List.of(new Criteria("testID", ":", testID));
        return searchBy(criterias, rowMapper, "test").stream().findFirst().orElse(null);
    }

    @Override
    public long insert(TestEntity test) {
        String query = """
            INSERT INTO test 
            (testID, testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus)
            VALUES 
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        return save(query,
                test.getTestId(),
                test.getTestCode(),
                test.getTestTitle(),
                test.getTestTime(),
                test.getTopicId(),
                test.getNumEasy(),
                test.getNumMedium(),
                test.getNumDiff(),
                test.getTestLimit(),
                test.getTestDate(),
                test.getTestStatus()
        );
    }

    @Override
    public void update(TestEntity test) {
        String query = """
            UPDATE test
            SET 
                testCode = ?, 
                testTilte = ?, 
                testTime = ?, 
                tpID = ?, 
                num_easy = ?, 
                num_medium = ?, 
                num_diff = ?, 
                testLimit = ?, 
                testDate = ?, 
                testStatus = ?
            WHERE testID = ?;
        """;

        update(query,
                test.getTestCode(),
                test.getTestTitle(),
                test.getTestTime(),
                test.getTopicId(),
                test.getNumEasy(),
                test.getNumMedium(),
                test.getNumDiff(),
                test.getTestLimit(),
                test.getTestDate(),
                test.getTestStatus(),
                test.getTestId()
        );
    }
}
