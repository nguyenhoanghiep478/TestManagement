package Entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ResultEntity {
    private int resultNum;
    private int userId;
    private String exCode;
    private String rsAnswer;
    private BigDecimal resMark;
    private Date resTime;

}
