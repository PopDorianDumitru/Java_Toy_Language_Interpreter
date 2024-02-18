package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;

public class VarExp implements Exp{
    String id;
    public VarExp(String Id){
        id = Id;
    }

    public String toString(){
        return id;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {

        return tbl.lookup(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
