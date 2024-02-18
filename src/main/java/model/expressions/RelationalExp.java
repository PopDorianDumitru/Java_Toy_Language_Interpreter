package model.expressions;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.MyException;
import exceptions.RelationalExpException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExp implements Exp{
    // 0 = <
    // 1 = <=
    // 2 = ==
    // 3 = !=
    // 4 = >
    // 5 = >=
    Exp expl, expr;
    int op;
    public RelationalExp(Exp e1, Exp e2, int o){
        expl = e1;
        expr = e2;
        op = o;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        if(op < 0 || op > 5)
            throw new RelationalExpException("Relation not supported");
        Value v1 = expl.eval(tbl,heap), v2 = expr.eval(tbl,heap);
        if(!v1.getType().equals(new IntType()) || !v2.getType().equals(new IntType()))
            throw new RelationalExpException("Both expressions must evaluate to int type");
        IntValue li = (IntValue) v1, ri = (IntValue) v2;
        int left = li.getVal(), right = ri.getVal();
        switch (op){
            case 0:
                return new BoolValue(left < right);
            case 1:
                return new BoolValue(left <= right);
            case 2:
                return new BoolValue(left == right);
            case 3:
                return new BoolValue(left != right);
            case 4:
                return new BoolValue(left > right);
            case 5:
                return new BoolValue(left >= right);
        }

        return new BoolValue(true);
    }

    @Override
    public String toString() {
        String relation = "";
        switch (op){
            case 0:
                relation = "<";
                break;
            case 1:
                relation = "<=";
                break;
            case 2:
                relation = "==";
                break;
            case 3:
                relation = "!=";
                break;
            case 4:
                relation = ">";
                break;
            case 5:
                relation = ">=";
                break;
        }
        return expl.toString() + " " + relation + " " + expr.toString();
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(expl.deepCopy(), expr.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expl.typecheck(typeEnv);
        Type type2 = expr.typecheck(typeEnv);
        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
            {
                return new BoolType();
            }
            else
                throw new RelationalExpException("Second operand is not integer");
        }
        else
            throw new RelationalExpException("First operand is not integer");
    }
}
