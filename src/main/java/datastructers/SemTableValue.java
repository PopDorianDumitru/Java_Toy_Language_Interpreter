package datastructers;

import java.util.ArrayList;

public class SemTableValue {
    Integer index, value;
    ArrayList<Integer> listValues;
    public  SemTableValue(Integer i, Integer v, ArrayList<Integer> lv){
        index = i;
        value = v;
        listValues = lv;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }

    public ArrayList<Integer> getListValues() {
        return listValues;
    }
}
