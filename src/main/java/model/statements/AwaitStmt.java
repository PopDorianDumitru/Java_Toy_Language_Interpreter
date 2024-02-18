package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIStack;
import datastructers.MyITable;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class AwaitStmt implements  IStmt{

    String var;

    public AwaitStmt(String v) {var = v;}

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyITable<Integer, Integer> latchTable = state.getLatchTable();
        if(!symTable.isDefined(var))
            throw new MyException("Var must be declared");
        if(!symTable.lookup(var).getType().equals(new IntType()))
            throw new MyException("Var must be of int type");
        IntValue fIndex = (IntValue) symTable.lookup(var);
        synchronized (latchTable){
            if(!latchTable.isDefined(fIndex.getVal()))
                throw new MyException("Var must be inside latch table");
            if(latchTable.lookup(fIndex.getVal()) != 0)
            {
                state.getStk().push(this.deepCopy());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(var) || !typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Variable must be declared and of type int");
        return typeEnv;
    }
}
