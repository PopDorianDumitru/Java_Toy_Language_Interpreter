package datastructers;

import exceptions.MyDictionaryException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class MyDictionary<Key, Value> implements MyIDictionary<Key, Value>{
    Dictionary<Key, Value> dict;

    public MyDictionary(){
        dict = new Hashtable<Key, Value>();
    }

    @Override
    public void remove(Key k) throws MyDictionaryException {
        if(dict.get(k) == null)
            throw new MyDictionaryException("Could not remove because key does not exist!");
        dict.remove(k);
    }

    @Override
    public void put(Key k, Value v) {
        dict.put(k,v);
    }

    @Override
    public Value lookup(Key k) throws MyDictionaryException {
        if(dict.get(k) == null)
            throw new MyDictionaryException("Could not get because key does not exist!");
        return dict.get(k);
    }

    @Override
    public void update(Key k, Value v) {
        dict.put(k,v);
    }

    @Override
    public boolean isDefined(Key k) {
        return dict.get(k) != null;
    }


    public Enumeration<Key> keys(){
        return dict.keys();
    }

    @Override
    public MyIDictionary<Key, Value> clone() {
        MyIDictionary<Key, Value> dictClone = new MyDictionary<>();
        Enumeration<Key> e = keys();
        while(e.hasMoreElements())
        {
            Key a = e.nextElement();
            dictClone.put(a, dict.get(a));
        }
        return dictClone;
    }

    @Override
    public String toString() {
        return dict.toString();
    }

    @Override
    public String toFileString() {
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
}
