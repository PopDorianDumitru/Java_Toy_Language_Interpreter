package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class LockStmt implements IStmt{
    String var;
    public LockStmt(String v){
        var = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyITable<Integer, Integer> lockTbl = state.getLockTable();
        if(!symTbl.isDefined(var) || !symTbl.lookup(var).getType().equals(new IntType()))
            throw new MyException("Var must be defined and an integer");

        int fIndex = ((IntValue) symTbl.lookup(var)).getVal();
        if(!lockTbl.isDefined(fIndex))
            throw new MyException("Not an index");

        if(lockTbl.changeLock(fIndex, state.getId()))
            return null;
        state.getStk().push(this.deepCopy());


        return null;
    }

    public String toString(){
        return "lock(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Var must be of type int");
        return typeEnv;
    }
}
