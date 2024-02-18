package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;

public class ReleaseStmt implements IStmt{
    String var;

    public  ReleaseStmt(String v){
        var = v;
    }

    @Override
    public String toString() {
        return "release(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        MyIDictionary<String, Value> dic = state.getSymTable();
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> sem = state.getSemaphoreTable();
        if(!dic.isDefined(var) || !dic.lookup(var).getType().equals(new IntType()))
            throw new MyException("Variable must be defined and of type int");
        IntValue fIndex = (IntValue)dic.lookup(var);

        synchronized (sem){
            if(!sem.isDefined(fIndex.getVal()))
                throw new MyException("Number must be defined in sem table");
            Pair<Integer, ArrayList<Integer>> semaphore = sem.lookup(fIndex.getVal());
            if(semaphore.getValue().contains(state.getId()))
                sem.removeId(fIndex.getVal(), state.getId());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        throw new MyException("Variable must be of type int");
    }
}
