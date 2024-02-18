package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIStack;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.Type;

public class CompStmt implements IStmt{

    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s){
        first = f;
        second = s;
    }

    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
