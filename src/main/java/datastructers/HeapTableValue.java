package datastructers;

import model.values.Value;

public class HeapTableValue {
    Integer address;
    Value val;
    public HeapTableValue(Integer add, Value v){
        address = add;
        val = v;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Value getVal() {
        return val;
    }

    public Integer getAddress() {
        return address;
    }

    public void setVal(Value val) {
        this.val = val;
    }
}
