package model.programstate;

import datastructers.*;
import exceptions.MyException;
import model.statements.IStmt;
import model.values.Value;

import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;

public class PrgState {
    int id;
    static int nextId = 0;
    static synchronized void incrementId(){
        nextId+=1;
    }
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIHeap<Integer, Value> Heap;
    MyIDictionary<String, BufferedReader> fileTable;
    MyITable<Integer, Integer> LockTable;
    MyIList<Value> out;
    IStmt originalProgram;
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, IStmt prg, MyTable<Integer, Integer> lk){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg;
        fileTable = ftbl;
        Heap = heap;
        LockTable = lk;
        incrementId();

        id = nextId;
    }
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, MyITable<Integer, Integer> lk){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = ftbl;
        Heap = heap;
        LockTable = lk;
        incrementId();

        id = nextId;
    }


    public MyITable<Integer, Integer> getLockTable(){return LockTable;
    }
    public int getId() {
        return id;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

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

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    @Override
    public String toString() {
        return "Program ID: " + id + "\nCurrent State of Program:\nExecution stack: " + exeStack.toString()
                +"\nSymbol Table: " + symTable.toString() + "\nOutput: " + out.toString() + "\nFile Table: " + fileTable.toString() + "\nHeap: " + Heap.toString();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt =  exeStack.pop();
        return crtStmt.execute(this);
    }


}
