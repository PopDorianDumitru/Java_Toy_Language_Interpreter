package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIStack;
import exceptions.MyException;
import exceptions.OpenRFileException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    Exp exp;

    public OpenRFile(Exp e){
        exp = e;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try
        {
            MyIStack<IStmt> st = state.getStk();
            MyIDictionary<String, BufferedReader> fTable = state.getFileTable();
            Value t = exp.eval(state.getSymTable(), state.getHeap());
            if (!t.getType().equals(new StringType())) {
                throw new OpenRFileException("Not a string type!");
            }
            StringValue strValue = (StringValue) t;
            if (fTable.isDefined(strValue.getVal()))
                throw new OpenRFileException("File already opened!");
            BufferedReader reader = new BufferedReader(new FileReader(strValue.getVal()));
            fTable.put(strValue.getVal(), reader);
        } catch (FileNotFoundException e) {
            throw new OpenRFileException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = exp.typecheck(typeEnv);
        if(typeVar.equals(new StringType()))
        {
            return typeEnv;
        }
        throw new OpenRFileException("Name of file must be a string");
    }
}
