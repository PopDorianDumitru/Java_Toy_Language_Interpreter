package datastructers;

import model.statements.LockStmt;

public class LockTableValue {
    Integer location, value;
    public LockTableValue(Integer l, Integer v){
        location = l;
        value = v;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
}
