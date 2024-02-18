package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import exceptions.VarDeclException;
import model.programstate.PrgState;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(String nm, Type ty)
    {
        name = nm;
        typ = ty;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtble = state.getSymTable();
        if(symtble.isDefined(name))
            throw new VarDeclException("Variable with that name has already been defined");
        symtble.put(name, typ.defaultValue());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, typ);
        return typeEnv;
    }
}
