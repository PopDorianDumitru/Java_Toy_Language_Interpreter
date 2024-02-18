package datastructers;

import java.util.ArrayList;

public class BarrierTableValue {
    Integer index, value;
    ArrayList<Integer> list;
    public BarrierTableValue(Integer i, Integer v, ArrayList<Integer> ls){
        index = i;
        value = v;
        list = ls;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }

    public ArrayList<Integer> getList() {
        return list;
    }
}
