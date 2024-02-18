package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIStack;
import datastructers.MyStack;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.Type;
import model.values.Value;

public class ForkStmt implements IStmt{

    IStmt statement;

    public ForkStmt(IStmt stmt)
    {
        statement = stmt;
    }

    @Override
    public String toString() {
        return "fork("+ statement.toString() + ");";

    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newExeStack = new MyStack<>();
        newExeStack.push(statement);
        MyIDictionary<String, Value> cloneSymTable = state.getSymTable().clone();

        return new PrgState(newExeStack, cloneSymTable, state.getOut(), state.getFileTable(), state.getHeap(), state.getLockTable());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv);
        return typeEnv;
    }
}
