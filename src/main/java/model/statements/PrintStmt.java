package model.statements;

import datastructers.MyIDictionary;
import datastructers.MyIList;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.Type;
import model.values.Value;

public class PrintStmt implements IStmt{

    Exp exp;

    public PrintStmt(Exp e){
        exp = e;
    }

    public String toString(){
        return "print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> ot = state.getOut();
        MyIDictionary<String, Value> symtble = state.getSymTable();
        ot.add(exp.eval(symtble, state.getHeap()));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
