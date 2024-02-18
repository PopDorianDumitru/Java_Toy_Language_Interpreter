package datastructers;

import exceptions.MyDictionaryException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class MyTable<Key, Value> implements MyITable<Key, Value>{
    ConcurrentHashMap<Key, Value> dict;
    int freeSpace = 0;

    public MyTable(){
        dict = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void remove(Key k) throws MyDictionaryException {
        if(dict.get(k) == null)
            throw new MyDictionaryException("Could not remove because key does not exist!");
        dict.remove(k);
    }

    @Override
    public synchronized void put(Key k, Value v) {
        dict.put(k,v);
    }

    @Override
    public synchronized Value lookup(Key k) throws MyDictionaryException {
        if(dict.get(k) == null)
            throw new MyDictionaryException("Could not get because key does not exist!");
        return dict.get(k);
    }

    @Override
    public synchronized void update(Key k, Value v) {
        dict.put(k,v);
    }

    @Override
    public synchronized boolean isDefined(Key k) {
        return dict.get(k) != null;
    }


    public synchronized Enumeration<Key> keys(){
        return dict.keys();
    }


    @Override
    public synchronized String toString() {
        return dict.toString();
    }

    @Override
    public synchronized String toFileString() {
        String fin = "";
        Enumeration<Key> e = dict.keys();
        while(e.hasMoreElements())
        {
            Key a = e.nextElement();
            fin = fin + a.toString() + " --> " + dict.get(a).toString() + '\n';
        }
        if(!fin.equals(""))
        {
            fin = fin.substring(0, fin.length() - 1);
        }
        else
            fin = "--Empty--";
        return fin;
    }

    @Override
    public synchronized void removeId(Key k, Integer v) {
        ((Pair<Integer, ArrayList<Integer>>)dict.get(k)).getValue().remove(v);
    }

    @Override
    public synchronized int getNextFreeSpace() {
        freeSpace+=1;
        return freeSpace;
    }
}
