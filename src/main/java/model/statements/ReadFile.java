package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import exceptions.ReadFileException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile implements IStmt{


    Exp exp;
    String varName;

    public ReadFile(Exp e, String var){
        varName = var;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try
        {
            MyIDictionary<String, Value> symTable = state.getSymTable();
            MyIDictionary<String, BufferedReader> fTable = state.getFileTable();
            if (!symTable.isDefined(varName))
                throw new ReadFileException("That variable is not defined");
            Value varValue = symTable.lookup(varName);
            if (!varValue.getType().equals(new IntType()))
                throw new ReadFileException("You can only read into int types");
            Value expValue = exp.eval(symTable, state.getHeap());
            if (!expValue.getType().equals(new StringType())) {
                throw new ReadFileException("Name of file must be string");
            }
            StringValue expString = (StringValue) expValue;
            IntValue varInt = (IntValue) varValue;
            if (!fTable.isDefined(expString.getVal())) {
                throw new ReadFileException("File is not opened");
            }
            BufferedReader reader = fTable.lookup(expString.getVal());
            String ln = reader.readLine();
            IntValue r;
            if(ln == null)
                r = new IntValue(0);
            else
            {
                int nm = Integer.parseInt(ln);
                r = new IntValue(nm);
            }
            symTable.update(varName, r);
        } catch (IOException e) {
            throw new ReadFileException(e.getMessage());
        }
        return null;

    }


    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeRef = exp.typecheck(typeEnv);
        if(typeVar.equals(new IntType()))
        {
            if(typeRef.equals(new StringType()))
            {
                return typeEnv;
            }
            else
                throw new ReadFileException("Name of file must be string");
        }
        else
            throw new ReadFileException("Must read into int variable");
    }
}
