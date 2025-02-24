package Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {
    private String key;
    private Object value;
    private String operation;

    public Criteria(String key,  String operation,Object value) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }
}
