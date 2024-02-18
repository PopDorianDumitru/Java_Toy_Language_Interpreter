package model.statements;

import datastructers.MyIDictionary;
import exceptions.CloseRFileException;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    Exp exp;
    public CloseRFile(Exp e){
        exp = e;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try
        {
            MyIDictionary<String, Value> symTbl = state.getSymTable();
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            Value value = exp.eval(symTbl, state.getHeap());
            if (!value.getType().equals(new StringType()))
                throw new CloseRFileException("The name of the file must be a string");
            StringValue fileName = (StringValue) value;
            if (!fileTable.isDefined(fileName.getVal()))
                throw new CloseRFileException("No file opened with that name");
            BufferedReader reader = fileTable.lookup(fileName.getVal());
            reader.close();
            fileTable.remove(fileName.getVal());
        } catch (IOException e) {
            throw new CloseRFileException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = exp.typecheck(typeEnv);
        if(typevar.equals(new StringType()))
            return typeEnv;
        throw new CloseRFileException("Expression must be of type string");
    }
}
