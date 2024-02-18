package datastructers;

import exceptions.MyListException;
import model.values.Value;

import java.util.Collection;
import java.util.List;

public interface MyIList<T> {
    void remove(int index) throws MyListException;
    void add(T v);
    public String toString();

    public int size();
    public String toFileString();
    T get(int index) throws MyListException;
    List<T> getList();
    void setList(List<T> newList);

}
