package model.statements;

import datastructers.MyIDictionary;
import exceptions.AssignStmtException;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.Type;
import model.values.Value;

public class AssignStmt implements  IStmt{
    String id;
    Exp exp;

    public String toString(){
        return id + "=" + exp.toString();
    }

    public AssignStmt(String name, Exp e){
        id = name;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtble = state.getSymTable();
        if(symtble.isDefined(id))
        {
            Value val = exp.eval(symtble, state.getHeap());
            Type typId = (symtble.lookup(id)).getType();
            if(val.getType().equals(typId))
            {
                symtble.update(id,val);
            }
            else
                throw new AssignStmtException("declared type of variable " + id + " and type of the assigned expression do not match");
        }
        else
            throw new AssignStmtException("The used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeVal = exp.typecheck(typeEnv);
        if(typeVar.equals(typeVal))
            return typeEnv;
        throw new AssignStmtException("Right hand side and left hand side have different types");
    }
}
