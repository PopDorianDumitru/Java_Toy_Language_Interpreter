package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class NewLockStmt implements IStmt{

    String var;

    public NewLockStmt(String v){
        var = v;
    }

    public String toString(){
        return "newLock(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyITable<Integer, Integer> lockTable = state.getLockTable();
        int nextFree = lockTable.getNextFreeLocation();
        lockTable.put(nextFree, -1);

        if(state.getSymTable().isDefined(var) && state.getSymTable().lookup(var).getType().equals(new IntType()))
            state.getSymTable().update(var, new IntValue(nextFree));
        else
            throw new MyException("Var must be defined and of type int");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(var))
            throw new MyException("That var does not exist!");
        if(!typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Variable must be of type int");
        return typeEnv;
    }
}
