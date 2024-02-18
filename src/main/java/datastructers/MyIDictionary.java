package datastructers;

import exceptions.MyDictionaryException;

import java.util.Enumeration;

public interface MyIDictionary<Key, Value> {
    void remove(Key k) throws MyDictionaryException;
    void put(Key k, Value v);
    Value lookup(Key k) throws MyDictionaryException;

    Enumeration<Key> keys();

    public MyIDictionary<Key, Value> clone();
    void update(Key k, Value v);
    public String toString();

    public String toFileString();

    boolean isDefined(Key k);
}
