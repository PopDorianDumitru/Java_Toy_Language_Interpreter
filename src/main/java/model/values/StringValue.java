package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value{
    String str;
    public StringValue(String val){
        str = val;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue;
    }

    public String getVal(){
        return str;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(str);
    }
}
