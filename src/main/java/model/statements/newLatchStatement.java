package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class newLatchStatement implements IStmt{
    String var;
    Exp exp;
    public newLatchStatement(String v, Exp e){
        var = v;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value pv = exp.eval(state.getSymTable(), state.getHeap());
        if(!pv.getType().equals(new IntType()))
            throw new MyException("Expression must be of type int");
        int nextFree;
        MyITable<Integer, Integer> latch = state.getLatchTable();
        synchronized (latch){
            nextFree = latch.getNextFree();
            latch.put(nextFree, ((IntValue)pv).getVal() );
        }
        if(state.getSymTable().isDefined(var) && state.getSymTable().lookup(var).getType().equals(new IntType()))
            state.getSymTable().update(var, new IntValue(nextFree));
        else
            throw new MyException("Var must be declared and of int type");


        return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + "," + exp + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new newLatchStatement(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(var) || !typeEnv.lookup(var).equals(new IntType()) || !exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("Both values must be of type int");
        return typeEnv;
    }
}
