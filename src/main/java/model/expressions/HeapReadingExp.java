package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.HeapReadingExpException;
import exceptions.MyException;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class HeapReadingExp implements Exp{

    Exp expression;
    public HeapReadingExp(Exp e){
        expression = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value expValue = expression.eval(tbl, heap);
        if(expValue.getType().equals(new RefType(expValue.getType())))
        {
            RefValue refExp = (RefValue) expValue;
            Value refVal = heap.lookup(refExp.getAddr());
            return refVal;
        }
        else
            throw new HeapReadingExpException("Expression must be a reference type!");
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadingExp(expression.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if(typ instanceof RefType){
            RefType reft = (RefType)typ;
            return reft.getInner();
        }
        throw new HeapReadingExpException("The rH argument is not a ref type");
    }

    @Override
    public String toString()
    {
        return "rH("+expression.toString()+")";
    }
}
