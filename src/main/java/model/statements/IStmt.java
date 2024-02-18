package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.Type;

import java.io.FileNotFoundException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;

}
