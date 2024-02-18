package datastructers;

import model.values.Value;

public class SymTableValue {
    String name;
    Value val;

    public SymTableValue(String nm, Value v){
        name = nm;
        val = v;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    public Value getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
