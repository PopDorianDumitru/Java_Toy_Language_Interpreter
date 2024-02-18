package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.MyException;
import model.types.Type;
import model.values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException;
    Exp deepCopy();
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
