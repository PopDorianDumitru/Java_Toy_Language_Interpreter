package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value{
    int val;

    public IntValue(int v) {
        this.val = v;
    }
    public IntValue(){}

    public Value deepCopy(){
        return new IntValue(val);
    }

    public int getVal(){
        return this.val;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntValue;
    }

    @Override
    public String toString(){
        return "" + this.val;
    }

    public Type getType(){
        return new IntType();
    }

}
