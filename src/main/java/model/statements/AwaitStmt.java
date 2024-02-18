package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyDictionaryException;
import exceptions.MyException;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

import java.util.ArrayList;

public class AwaitStmt implements IStmt{

    String var;
    public AwaitStmt(String v){
        var = v;
    }
    public String toString(){
        return "await(" + var + ")";
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {

        if(!state.getSymTable().isDefined(var) || !state.getSymTable().lookup(var).getType().equals(new IntType()))
            throw new MyException("Variable must be defined and of type int");
        IntValue foundIndex = (IntValue) state.getSymTable().lookup(var);
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> barrier = state.getBarrier();
        synchronized (barrier){
            if(!barrier.isDefined(foundIndex.getVal()))
                throw new MyException("Variable must be an index in the barrier table");
            Pair<Integer, ArrayList<Integer>> v = barrier.lookup(foundIndex.getVal());
            int nl = v.getValue().size();
            if(v.getKey() > nl)
            {
                if(v.getValue().contains(state.getId()))
                    state.getStk().push(this.deepCopy());
                else
                {
                    barrier.add(foundIndex.getVal(), state.getId());
                    state.getStk().push(this.deepCopy());
                }
            }
        }

        return null;
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
