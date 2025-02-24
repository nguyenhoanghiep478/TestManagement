package Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {
    private String key;
    private Object value;
    private String operation;

}
