package datastructers;

import model.values.Value;

public class ProcTableValue {
    String procedure, body;
    public ProcTableValue(String proc, String bod){
        procedure = proc;
        body = bod;
    }

    public String getBody() {
        return body;
    }

    public String getProcedure() {
        return procedure;
    }
}
