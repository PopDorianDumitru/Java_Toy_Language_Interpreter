package datastructers;

import exceptions.MyStackException;
import model.statements.IStmt;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyStackException;
    void push(T v);
    String toFileString();
    boolean isEmpty();
    public String toString();
    public List<T> getStatements();
}
