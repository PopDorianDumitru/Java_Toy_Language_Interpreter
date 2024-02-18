package datastructers;

import exceptions.MyDictionaryException;

import java.util.Enumeration;

public interface MyITable<Key,Value> {
    void remove(Key k) throws MyDictionaryException;
    void put(Key k, Value v);
    Value lookup(Key k) throws MyDictionaryException;

    Enumeration<Key> keys();

    void update(Key k, Value v);
    public String toString();

    public String toFileString();

    public void removeId(Key k, Integer v);
    public int getNextFreeSpace();
    boolean isDefined(Key k);
}
