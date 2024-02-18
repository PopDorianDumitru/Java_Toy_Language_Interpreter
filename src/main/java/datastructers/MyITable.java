package datastructers;

import exceptions.MyDictionaryException;
import exceptions.MyException;

import java.util.Enumeration;

public interface MyITable<Key, Value>{
    void remove(Key k) throws MyDictionaryException;
    void put(Key k, Value v);
    Value lookup(Key k) throws MyDictionaryException;

    Enumeration<Key> keys();

    void update(Key k, Value v);
    public String toString();

    public String toFileString();
    public int getNextFreeLocation();
    public boolean isOccuopied(Key k);
    public void release(Key k, Key m, Value v);
    boolean isDefined(Key k);

    boolean changeLock(Key fIndex, Value id) throws MyException;
}
