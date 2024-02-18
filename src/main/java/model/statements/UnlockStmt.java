package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class UnlockStmt implements IStmt{

    String var;
    public UnlockStmt(String v){
        var = v;
    }
    public String toString(){
        return "unlock(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> dic = state.getSymTable();
        if(!dic.isDefined(var) || !dic.lookup(var).getType().equals(new IntType()))
            throw new MyException("Var must be defined and an integer");
        int fIndex = ((IntValue)dic.lookup(var)).getVal();
        if(!state.getLockTable().isDefined(fIndex))
            throw new MyException("Index must be inside lock table");
        state.getLockTable().release(fIndex, state.getId(), -1);


        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        throw new MyException("Var must be of type int");
    }
}
