package datastructers;

import exceptions.MyDictionaryException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class MyTable<Key, Value> implements MyITable<Key, Value>{
    ConcurrentHashMap<Key, Value> table;
    int nextFree = 0;

    public MyTable(){
        table = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void remove(Key k) throws MyDictionaryException {
        if(table.get(k) == null)
            throw new MyDictionaryException("Could not remove because key does not exist!");
        table.remove(k);
    }

    @Override
    public synchronized void put(Key k, Value v) {
        table.put(k,v);
    }

    @Override
    public synchronized Value lookup(Key k) throws MyDictionaryException {
        if(table.get(k) == null)
            throw new MyDictionaryException("Could not get because key does not exist!");
        return table.get(k);
    }

    @Override
    public synchronized void update(Key k, Value v) {
        table.put(k,v);
    }

    @Override
    public synchronized boolean isDefined(Key k) {
        return table.get(k) != null;
    }

    @Override
    public int getNextPosition() {
        nextFree+=1;
        return nextFree;
    }

    @Override
    public synchronized void add(Key k, int v) {
        Pair<Integer, ArrayList<Integer>> vl = (Pair<Integer, ArrayList<Integer>>) table.get(k);
        vl.getValue().add(v);
    }


    public synchronized Enumeration<Key> keys(){
        return table.keys();
    }



    @Override
    public synchronized String toString() {
        return table.toString();
    }

    @Override
    public synchronized String toFileString() {
        String fin = "";
        Enumeration<Key> e = table.keys();
        while(e.hasMoreElements())
        {
            Key a = e.nextElement();
            fin = fin + a.toString() + " --> " + ((Pair<Integer, ArrayList<Integer>>)table.get(a)).getKey() + "---" + ((Pair<Integer, ArrayList<Integer>>)table.get(a)).getValue()  + '\n';
        }
        if(!fin.equals(""))
        {
            fin = fin.substring(0, fin.length() - 1);
        }
        else
            fin = "--Empty--";
        return fin;
    }
}
