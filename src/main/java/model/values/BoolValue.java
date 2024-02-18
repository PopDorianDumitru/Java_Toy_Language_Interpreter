package model.values;


import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(boolean value){
        val = value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolValue;
    }

    @Override
    public String toString(){
        if(val)
            return "true";
        return "false";
    }

    public Value deepCopy()
    {
        return new BoolValue(val);
    }

    public BoolValue(){}

    public boolean getValue(){
        return this.val;
    }

    public Type getType(){
        return new BoolType();
    }

}
