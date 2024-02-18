package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.ArithmeticExpException;
import exceptions.MyException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    int op;
    public ArithExp(Exp firstExpression, Exp secondExpression, int operation){
        e1 = firstExpression;
        e2 = secondExpression;
        op = operation;
    }

    public String toString(){
        String operation = "+";
        if(op == 1)
            operation="+";
        if(op == 2)
            operation="-";
        if(op == 3)
            operation="*";
        if(op == 4)
            operation="/";
        return e1.toString() + " " + operation + " " + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl, heap);
            if(v2.getType().equals(new IntType()))
            {
                IntValue iv1 = (IntValue) v1, iv2 = (IntValue) v2;
                int n1, n2;
                n1 = iv1.getVal();
                n2 = iv2.getVal();
                if(op == 1) return new IntValue(n1 + n2);
                if(op == 2) return new IntValue(n1 - n2);
                if(op == 3) return new IntValue(n1*n2);
                if(op == 4)
                {
                    if(n2 == 0)
                        throw new ArithmeticExpException("Can't divide by zero!");
                    return new IntValue(n1/n2);
                }
            }
            else throw new ArithmeticExpException("Second operand is not an integer!");
        }
        else throw new ArithmeticExpException("First operand is not an integer!");
        return v1;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVal1 = e1.typecheck(typeEnv);
        Type typeVal2 = e2.typecheck(typeEnv);
        if(typeVal1.equals(new IntType())){
            if(typeVal2.equals(new IntType()))
                return new IntType();
            else
                throw  new ArithmeticExpException("Second operand is not an integer");
        }
        else
            throw new ArithmeticExpException("First operand is not an integer");
    }
}
