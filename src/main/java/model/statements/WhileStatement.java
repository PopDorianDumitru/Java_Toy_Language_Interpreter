package model.statements;

import datastructers.MyIDictionary;
import exceptions.MyException;
import exceptions.WhileStatementException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;


public class WhileStatement implements IStmt{

    Exp exp;
    IStmt statement;

    public WhileStatement(Exp e, IStmt s){
        exp = e;
        statement = s;
    }
    @Override
    public String toString(){
        return "While( " + exp.toString() + " )" + "{ " + statement.toString() + " }";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value eval = exp.eval(state.getSymTable(), state.getHeap());
        if(eval.getType().equals(new BoolType()))
        {
            BoolValue boolVal = (BoolValue) eval;
            if(boolVal.getValue())
            {
                state.getStk().push(this);
                state.getStk().push(statement);
            }
            return null;
        }
        else
            throw new WhileStatementException("Expression must evaluate to boolean value!");
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStatement(exp.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeCond = exp.typecheck(typeEnv);
        if(typeCond.equals(new BoolType()))
            return statement.typecheck(typeEnv);
        throw new WhileStatementException("While condition is not bool type");
    }
}
