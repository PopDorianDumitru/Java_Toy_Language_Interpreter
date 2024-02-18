package model.types;

import model.values.RefValue;
import model.values.Value;


public class RefType implements Type{

    Type inner;
    public RefType(Type inn){
        inner = inn;
    }


    @Override
    public String toString(){
        return "Ref " + inner.toString();
    }


    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }

    public Type getInner(){
        return inner;
    }



    @Override
    public boolean equals(Object another){
        return another instanceof RefType;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(-1, this.inner);
    }
}
