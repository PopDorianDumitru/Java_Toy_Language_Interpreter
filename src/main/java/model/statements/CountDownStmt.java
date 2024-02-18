package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyITable;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class CountDownStmt implements IStmt{

    String var;
    public CountDownStmt(String v){
        var =v;
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyITable<Integer, Integer> latchTbl = state.getLatchTable();

        if(!symTable.isDefined(var) || !symTable.lookup(var).getType().equals(new IntType()))
            throw new MyException("Var must be declared and of type int");

        Integer fIndex = ((IntValue) symTable.lookup(var)).getVal();
        synchronized (latchTbl){
            if(!latchTbl.isDefined(fIndex))
                throw new MyException("Index must be a latch");
            if(latchTbl.lookup(fIndex) > 0)
            {
                latchTbl.put(fIndex, latchTbl.lookup(fIndex) - 1);
                state.getOut().add(new StringValue(Integer.toString(state.getId())));
            }
            else
            {
                state.getOut().add(new StringValue(Integer.toString(state.getId())));
            }
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(var) || !typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Var must be declared and of type int");

        return typeEnv;
    }
}
