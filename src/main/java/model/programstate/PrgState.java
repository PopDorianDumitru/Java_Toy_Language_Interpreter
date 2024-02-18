package model.programstate;

import datastructers.*;
import exceptions.ControllerException;
import exceptions.MyException;
import javafx.util.Pair;
import model.statements.IStmt;
import model.values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;

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
    MyIList<Value> out;
    MyITable<Integer, Pair<Integer, ArrayList<Integer>>> Barrier;
    IStmt originalProgram;
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, IStmt prg, MyITable<Integer, Pair<Integer, ArrayList<Integer>>> bar){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg;
        fileTable = ftbl;
        Barrier = bar;
        Heap = heap;
        incrementId();

        id = nextId;
    }
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> ftbl, MyIHeap<Integer, Value> heap, MyITable<Integer, Pair<Integer, ArrayList<Integer>>> bar){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        Barrier = bar;
        fileTable = ftbl;
        Heap = heap;
        incrementId();

        id = nextId;
    }

    public int getId() {
        return id;
    }

    public MyITable<Integer, Pair<Integer, ArrayList<Integer>>> getBarrier() {
        return Barrier;
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
                +"\nSymbol Table: " + symTable.toString() + "\nOutput: " + out.toString() + "\nFile Table: " + fileTable.toString() + "\nHeap: " + Heap.toString() + "\nCyclic Barrier: " + Barrier.toString();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt =  exeStack.pop();
        return crtStmt.execute(this);
    }


}
