package Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogEntity {
    private int logId;
    private String logContent;
    private int logUserId;
    private int logExId;
    private Date logDate;

}
