package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import datastructers.MyTable;
import exceptions.MyException;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;

public class NewBarrierStmt implements IStmt{
    String var;
    Exp exp;
    public NewBarrierStmt(String v, Exp e){
        var = v;
        exp = e;
    }
    public String toString(){
        return "newBarrier(" + var + "," + exp + ")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value v = exp.eval(state.getSymTable(), state.getHeap());
        if(!v.getType().equals(new IntType()))
            throw new MyException("Variable must be int");
        if(!state.getSymTable().isDefined(var) || !state.getSymTable().lookup(var).getType().equals(new IntType()))
            throw new MyException("Variable must be defined and of type int");
        IntValue iv = (IntValue) v;
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> barrier = state.getBarrier();
        synchronized (barrier){
            int nf = barrier.getNextPosition();
            barrier.put(nf, new Pair<>(iv.getVal(), new ArrayList<>()));

            state.getSymTable().update(var, new IntValue(nf));

        }
        return null;

    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("Expression must be int");
        if(!typeEnv.isDefined(var) || !typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Var must be defined and of type int");
        return typeEnv;
    }
}
