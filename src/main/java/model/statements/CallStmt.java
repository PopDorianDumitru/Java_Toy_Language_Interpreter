package model.statements;

import datastructers.MyDictionary;
import datastructers.MyIDictionary;
import exceptions.MyException;
import model.expressions.Exp;
import model.programstate.PrgState;
import model.types.Type;
import model.values.Value;

import java.util.ArrayList;
import java.util.Arrays;

public class CallStmt implements IStmt{

    String name;
    ArrayList<Exp> params;

    public CallStmt(String nm, ArrayList<Exp> pms) {
        name = nm;
        params = pms;
    }
    public CallStmt(String nm, Exp... para){
        name = nm;
        params = new ArrayList<>();
        params.addAll(Arrays.asList(para));
    }
    public  String toString(){
        return "call " + name + "(" + params + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getProcTable().isDefined(name))
            throw new MyException("Function not declared");
        IStmt fS = state.getProcTable().lookup(name).getValue();
        ArrayList<String> ps = state.getProcTable().lookup(name).getKey();
        if(ps.size() != params.size())
            throw new MyException("Function called with wrong nr of arguments");
        MyIDictionary<String, Value> symTableProc = new MyDictionary<>();
        int i = 0;
        while(i < ps.size())
        {
            symTableProc.put(ps.get(i), params.get(i).eval(state.getSymTable(), state.getHeap()));
            i+=1;
        }
        state.pushSymTable(symTableProc);

        state.getStk().push(new ReturnStmt());
        state.getStk().push(fS);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CallStmt(name, params);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
