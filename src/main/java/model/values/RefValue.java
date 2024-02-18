package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value{

    int address;
    Type locationType;

    public RefValue(int address, Type loc){
        this.address = address;
        locationType = loc;
    }

    public int getAddr(){
        return address;
    }

    @Override
    public boolean equals(Object object){
        return object instanceof RefValue;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Type getLocationType(){
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString()
    {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }
}
