package repository;

import datastructers.MyIDictionary;
import datastructers.MyIList;
import datastructers.MyIStack;
import datastructers.MyList;
import exceptions.MyException;
import exceptions.MyListException;
import exceptions.RepositoryException;
import model.programstate.PrgState;
import model.statements.IStmt;

import java.io.*;
import java.security.Key;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class Repository implements IRepository{
    MyIList<PrgState> prgStates;
    String logFilePath;
    public Repository(PrgState initialProgram, String file){
        prgStates = new MyList<>();
        prgStates.add(initialProgram);
        logFilePath = file;
    }


    public String toString(){
        String str = "";
        for (PrgState prgState : prgStates.getList()) {
            str = str + prgState.toString() + "\n";
        }
        return str;
    }

//    @Override
//    public PrgState getCrtPrg() throws MyListException {
//        PrgState state = prgStates.get(0);
//
//        return state;
//    }
//    public PrgState getCrtPrg(int index) throws MyListException {
//        return prgStates.get(index);
//    }

    @Override
    public void addPrg(PrgState state) {
        prgStates.add(state);
    }

    @Override
    public PrgState getById(int id) {
        List<PrgState> prg =  prgStates.getList().stream().filter(p->p.getId() == id).collect(Collectors.toList());
        if(prg.size() == 0)
            return null;
        return prg.get(0);
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates.getList();
    }

    @Override
    public void setPrgList(List<PrgState> newStates) {
        prgStates.setList(newStates);
    }

    @Override
    public void removePrg(int index) throws MyListException {
        prgStates.remove(index);
    }

    @Override
    public void logPrgStateExec(PrgState st)  {


        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            return;
        }
        logFile.println("Program of id: " + st.getId());
        logFile.println("ExeStack: ");
        logFile.println(st.getStk().toFileString());
        logFile.println("SymTable: ");
        logFile.println(st.getSymTable().toFileString());
        logFile.println("Out: ");
        logFile.println(st.getOut().toFileString());
        logFile.println("FileTable: ");
        MyIDictionary<String, BufferedReader> fileTable = st.getFileTable();
        Enumeration<String> e = fileTable.keys();
        boolean empty = true;
        while (e.hasMoreElements()) {
            empty = false;
            String t = e.nextElement();
            logFile.println(t);
        }
        if(empty)
            logFile.println("--Empty--");
        logFile.println("Heap: ");
        logFile.println(st.getHeap().toFileString());
        logFile.println("Barrier table: ");
        logFile.println(st.getBarrier().toFileString());
        logFile.println();
        logFile.flush();
        logFile.close();

    }
}
