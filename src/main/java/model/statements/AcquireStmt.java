package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import javafx.util.Pair;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;

public class AcquireStmt implements IStmt{

    String var;

    public  AcquireStmt(String v){
        var = v;
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> sym = state.getSymTable();
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> sem = state.getSemaphoreTable();
        if(!sym.isDefined(var) || !sym.lookup(var).getType().equals(new IntType()))
            throw new MyException("Variable must be defined in sym table and be of type int");
        IntValue fIndex = (IntValue) sym.lookup(var);
        synchronized (sem){
            if(!sem.isDefined(fIndex.getVal()))
                throw new MyException("Variable must be an integer");
            Pair<Integer, ArrayList<Integer>> semaphore = sem.lookup(fIndex.getVal());
            Integer arrayLeng = semaphore.getValue().size();
            if(semaphore.getKey() > arrayLeng){
                if(!semaphore.getValue().contains(state.getId()))
                    semaphore.getValue().add(state.getId());

            }
            else
                state.getStk().push(this.deepCopy());

        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        throw new MyException("Variable must be of type int");
    }
}
