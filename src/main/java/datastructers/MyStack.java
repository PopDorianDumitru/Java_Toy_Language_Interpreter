package datastructers;

import exceptions.MyStackException;
import model.statements.IStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }
    public T pop() throws MyStackException {
        if(stack.empty())
            throw new MyStackException("Could not pop because stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public String toFileString() {
        String fin = "";
        Stack<T> aux = (Stack<T>) stack.clone();
        while(!aux.empty())
        {
            fin = fin + aux.pop().toString() + "\n";
        }
        if(!fin.equals(""))
            fin = fin.substring(0, fin.length() - 1);
        else
            fin = "--Empty--";
        return fin;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public List<T> getStatements() {
        List<T> statements = new ArrayList<>();
        Stack<T> aux = (Stack<T>) stack.clone();
        while(!aux.empty())
        {
            statements.add(aux.pop());
        }
        return statements;
    }

}
