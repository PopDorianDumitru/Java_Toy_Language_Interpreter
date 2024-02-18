package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import javafx.util.Pair;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;

public class CreateSemaphoreStmt implements IStmt{
    String var;
    Exp exp;

    public CreateSemaphoreStmt(String v, Exp e){
        var = v;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value v = exp.eval(state.getSymTable(), state.getHeap());

        if(!v.getType().equals(new IntType()))
            throw new MyException("Exp must evaluate to integer");
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> sem = state.getSemaphoreTable();
        synchronized (sem){
            int nextFree = sem.getNextFreeSpace();
            sem.put(nextFree, new Pair<>(((IntValue)v).getVal(), new ArrayList<>()));
            if(state.getSymTable().isDefined(var) && state.getSymTable().lookup(var).getType().equals(new IntType()))
                state.getSymTable().update(var, new IntValue(nextFree));
            else
                throw new MyException("Variable must be defined and of type int");
        }

        return null;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + "," + exp + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.lookup(var).equals(new IntType()) || !exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("Both var and exp must evaluate to integer");
        return typeEnv;
    }
}
