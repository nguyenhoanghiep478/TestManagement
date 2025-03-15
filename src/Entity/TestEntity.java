package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private int testId;
    private String testCode;
    private String testTitle;
    private int testTime;
    private int topicId;
    private int numEasy;
    private int numMedium;
    private int numDiff;
    private int testLimit;
    private Date testDate;
    private int testStatus;
}
