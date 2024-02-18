package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.LogicExpException;
import exceptions.MyException;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;

    int op; // 1 = &&, 2 = ||

    public LogicExp(Exp fe, Exp se, int ope)
    {
        e1 = fe;
        e2 = se;
        op = ope;
    }

    public String toString(){
        if(op == 1)
            return e1.toString() + " && " + e2.toString();
        else if(op == 2)
            return e1.toString() + " || " + e2.toString();
        return "";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        if(e1.eval(tbl, heap).getType().equals(new BoolType()))
        {
            if(e2.eval(tbl, heap).getType().equals(new BoolType()))
            {
                BoolValue v1 = (BoolValue) e1.eval(tbl, heap);
                BoolValue v2 = (BoolValue) e2.eval(tbl, heap);
                if(op == 1)
                    return new BoolValue(v1.getValue() && v2.getValue());
                else if(op == 2)
                    return new BoolValue(v1.getValue() || v2.getValue());
                else
                    throw new LogicExpException("Invalid operation on bool model.types");
            }
            else
                throw new LogicExpException("Second expression is not of type bool");
        }
        else
            throw new LogicExpException("First expression is not of type bool");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);
        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
                throw new LogicExpException("Second operand is not bool");
        }
        else
            throw new LogicExpException("First operand is not bool");
    }
}
