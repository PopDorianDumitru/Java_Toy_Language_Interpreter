package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.BoolType;
import model.types.Type;

public class ConditionalAssignmentStmt implements IStmt{
    String var;
    Exp exp1, exp2, exp3;

    public ConditionalAssignmentStmt(String v, Exp e1, Exp e2, Exp e3){
        var = v;
        exp1 = e1;
        exp2 = e2;
        exp3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt nxt = new IfStmt( exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3) );
        state.getStk().push(nxt);
        return null;
    }

    @Override
    public String toString() {
        return var + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!exp1.typecheck(typeEnv).equals(new BoolType()))
            throw new MyException("Expression 1 must evaluate to bool");
        if(!typeEnv.lookup(var).equals(exp2.typecheck(typeEnv)) || !typeEnv.lookup(var).equals(exp3.typecheck(typeEnv)))
            throw new MyException("The variable and the other two expression must have the same type");

        return typeEnv;
    }
}
