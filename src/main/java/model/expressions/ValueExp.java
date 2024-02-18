package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value v){
        e = v;
    }

    public String toString(){
        return e.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
