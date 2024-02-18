package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.HeapWritingExpException;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class HeapWritingStmt implements IStmt {
    String varName;
    Exp expression;

    public HeapWritingStmt(String var, Exp e){
        expression = e;
        varName = var;
    }

    public String getVarName() {
        return varName;
    }

    public Exp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression.toString() + ")";
    }



    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(tbl.isDefined(varName))
        {
            Value val = tbl.lookup(varName);
            if(val.getType().equals(new RefType(val.getType())))
            {
                RefValue valRef = (RefValue) val;
                if(heap.isDefined(valRef.getAddr()))
                {
                    Value expValue = expression.eval(tbl, heap);
                    if(expValue.getType().equals(valRef.getLocationType()))
                    {
                        heap.update(valRef.getAddr(), expValue);
                        return null;
                    }
                    else
                        throw new HeapWritingExpException("The value of the expression must be the same as the value to which the pointer points to");
                }
                else
                    throw new HeapWritingExpException("That key does not point to a valid memory space!");
            }
            else
                throw new HeapWritingExpException("That key is not a reference type!");
        }
        else
            throw new HeapWritingExpException("That key is not defined!");
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWritingStmt(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExpr = expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExpr)))
        {
            if(typeExpr.equals(((RefType) typeVar).getInner()))
            {
                return typeEnv;
            }
            else
                throw new HeapWritingExpException("Expression is not of correct type");
        }
        else
            throw new HeapWritingExpException("Variable is not of reference type");
    }
}
