package model.statements;

import datastructers.MyIDictionary;
import exceptions.IfStmtException;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    public String toString(){
        return "(IF(" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtble = state.getSymTable();

        Value res = exp.eval(symtble, state.getHeap());
        if(res.getType().equals(new BoolType()))
        {
            BoolValue bRes = (BoolValue) res;
            if(bRes.getValue())
                thenS.execute(state);
            else
                elseS.execute(state);
        }
        else
            throw new IfStmtException("Expression inside if condition does not evaluate to Bool Value");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if(expType.equals(new BoolType()))
        {
            thenS.deepCopy().typecheck(typeEnv);
            elseS.deepCopy().typecheck(typeEnv);
            return typeEnv;
        }
        throw new IfStmtException("The condition of IF has not the type bool");
    }
}
