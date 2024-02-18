package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.Type;

public class NopStmt implements IStmt{


    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString()
    {
        return "NOP";
    }

}
