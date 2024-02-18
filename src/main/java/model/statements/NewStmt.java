package model.statements;

import datastructers.MyDictionary;
import datastructers.MyIDictionary;
import datastructers.MyIHeap;
import exceptions.MyDictionaryException;
import exceptions.MyException;
import exceptions.NewStmtException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class NewStmt implements IStmt{

    String varName;
    Exp expression;

    public NewStmt(String var, Exp exp){
        expression = exp;
        varName = var;
    }

    public Exp getExpression() {
        return expression;
    }

    public String getVarName() {
        return varName;
    }

    @Override
    public String toString() {
        return "new( " + varName + ", " + expression.toString() + " )";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value >heap = state.getHeap();
        try{
            Value val = symTable.lookup(varName);
            if(val.getType().equals(new RefType(val.getType())))
            {
                RefValue reference = (RefValue) val;
                Value valExpr = expression.eval(symTable, state.getHeap());
                if(valExpr.getType().equals(((RefValue) val).getLocationType()))
                {
                    int nextFreeLocation = heap.getNextFreeLocation();
                    heap.put(heap.getNextFreeLocation(), valExpr);
                    symTable.update(varName, new RefValue(nextFreeLocation, valExpr.getType()));
                }
                else
                {
                    throw new NewStmtException("You can't assign to a pointer of a certain type a value of another type!");
                }
            }
            else
            {
                throw new NewStmtException("That pointer does not point to a correct type!");
            }
        }
        catch (MyDictionaryException exc){
            throw new NewStmtException("That variable is not defined");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
        {
            return typeEnv;
        }
        throw new NewStmtException("Right hand side and left hand side have different types");
    }
}
