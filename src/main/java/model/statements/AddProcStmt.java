package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import javafx.util.Pair;
import model.programstate.PrgState;
import model.types.Type;

import java.util.ArrayList;
import java.util.Arrays;

public class AddProcStmt implements IStmt{

    String name;
    ArrayList<String> params;
    IStmt stmt;


    public AddProcStmt(String nm, ArrayList<String> p, IStmt s){
        stmt = s;
        name = nm;
        params = p;
    }

    public String toString(){
        return "procedure " + name + "(" +  params + ") " + stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getProcTable().put(name, new Pair<>(params, stmt));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AddProcStmt(name, params, stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
