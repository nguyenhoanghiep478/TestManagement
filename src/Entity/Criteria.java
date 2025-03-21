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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
