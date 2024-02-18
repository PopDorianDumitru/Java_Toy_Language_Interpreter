package model.programstate;

import datastructers.*;
import exceptions.ControllerException;
import exceptions.MyException;
import exceptions.MyStackException;
import javafx.util.Pair;
import model.statements.IStmt;
import model.values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;

public class PrgState {
    int id;
    static int nextId = 0;

    public PrgState(MyIStack<IStmt> stk, MyIStack<MyIDictionary<String, Value>> dicts, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, MyITable<String, Pair<ArrayList<String>, IStmt>> pt) {
        exeStack = stk;
        symTables = dicts;
        ProcTable = pt;
        this.out = out;
        this.fileTable = fileTable;
        Heap = heap;
        incrementId();

        id = nextId;
    }

    static synchronized void incrementId(){
        nextId+=1;
    }
    MyIStack<IStmt> exeStack;
    MyIHeap<Integer, Value> Heap;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIList<Value> out;
    MyIStack<MyIDictionary<String, Value>> symTables;
    MyITable<String, Pair<ArrayList<String>, IStmt>> ProcTable;
    IStmt originalProgram;
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, IStmt prg,  MyITable<String, Pair<ArrayList<String>, IStmt>> pt){
        exeStack = stk;
        ProcTable = pt;
        symTables = new MyStack<>();
        symTables.push(symtbl);
        out = ot;
        originalProgram = prg;
        fileTable = ftbl;
        Heap = heap;
        incrementId();

        id = nextId;
    }
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, MyITable<String, Pair<ArrayList<String>, IStmt>> pt){
        exeStack = stk;
        ProcTable = pt;
        symTables = new MyStack<>();
        symTables.push(symtbl);
        out = ot;
        fileTable = ftbl;
        Heap = heap;
        incrementId();

        id = nextId;
    }

    public MyITable<String, Pair<ArrayList<String>, IStmt>> getProcTable() {
        return ProcTable;
    }

    public int getId() {
        return id;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTables.top();
    }

    public void pushSymTable(MyIDictionary<String, Value> st){
        symTables.push(st);
    }
    public void popSymTable() throws MyStackException {symTables.pop();}
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public MyIList<Value> getOut() {
        return out;
    }


    public MyIHeap<Integer, Value> getHeap(){
        return Heap;
    }
    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setHeap(MyIHeap<Integer, Value> heap) {
        Heap = heap;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

//    public void setSymTable(MyIDictionary<String, Value> symTable) {
//        this.symTable = symTable;
//    }

    @Override
    public String toString() {
        return "Program ID: " + id + "\nCurrent State of Program:\nExecution stack: " + exeStack.toString()
                +"\nSymbol Tables: " + symTables.toString() + "\nOutput: " + out.toString() + "\nFile Table: " + fileTable.toString() + "\nHeap: " + Heap.toString();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt =  exeStack.pop();
        return crtStmt.execute(this);
    }


    public MyIStack<MyIDictionary<String, Value>> getCloneTables() throws MyStackException {
        MyIDictionary<String, Value> symTable;

        MyIStack<MyIDictionary<String, Value>> auxStack = new MyStack<>(), clone = new MyStack<>();
        while(!symTables.isEmpty())
        {
            symTable = symTables.pop();
            auxStack.push(symTable);
        }
        while(!auxStack.isEmpty())
        {
            symTable = auxStack.pop();
            symTables.push(symTable);
            clone.push(symTable.clone());
        }
        return clone;
        
    }
}
