package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.Type;

public class ReturnStmt implements IStmt{

    public ReturnStmt(){}

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.popSymTable();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
