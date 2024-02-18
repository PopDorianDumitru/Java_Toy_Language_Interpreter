package datastructers;

import exceptions.MyDictionaryException;
import exceptions.MyHeapException;

import java.util.Enumeration;
import java.util.Map;

public interface MyIHeap <Integer, Value>{
    void remove(Integer k) throws MyDictionaryException, MyHeapException;
    void put(Integer k, Value v) throws MyHeapException;
    Value lookup(Integer k) throws MyDictionaryException, MyHeapException;

    public int getNextFreeLocation();
    void update(Integer k, Value v) throws MyHeapException;
    public String toString();

    public String toFileString();

    public void setContent(Map<Integer, Value> mp);
    public Map<Integer, Value> getContent();

    boolean isDefined(Integer k) throws MyHeapException;
}
