package datastructers;

import exceptions.MyDictionaryException;
import exceptions.MyException;
import exceptions.MyHeapException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<Integer, Value> implements MyIHeap<Integer, Value>{
    Map<Integer, Value> heap;
    java.lang.Integer biggestFreeLocation;
    Stack<java.lang.Integer> nextFreeLocation;
    public MyHeap(){
        heap = new ConcurrentHashMap<Integer,Value>();
        biggestFreeLocation = 1;
        nextFreeLocation = new Stack< java.lang.Integer >();
        nextFreeLocation.push(biggestFreeLocation);
    }
    @Override
    public int getNextFreeLocation()
    {
        return nextFreeLocation.peek();
    }
    @Override
    public void remove(Integer k) throws MyHeapException {
        if(!(k instanceof java.lang.Integer))
            throw new MyHeapException("Address must be an Integer");
        if(heap.get(k) == null)
            throw new MyHeapException("Could not remove because key does not exist!");
        heap.remove(k);
        nextFreeLocation.push((java.lang.Integer) k);
    }

    @Override
    public void put(Integer k, Value v) throws MyHeapException {
        if(!(k instanceof java.lang.Integer))
            throw new MyHeapException("Address must be an Integer");
        if((java.lang.Integer) k == 0)
        {
            throw new MyHeapException("Address 0 is invalid!");
        }
        heap.put(k,v);
        nextFreeLocation.pop();
        if(k == biggestFreeLocation)
        {
            biggestFreeLocation++;
            nextFreeLocation.push(biggestFreeLocation);
        }
    }

    @Override
    public Value lookup(Integer k) throws MyHeapException {
        if(!(k instanceof java.lang.Integer))
            throw new MyHeapException("Address must be an Integer");
        if(heap.get(k) == null)
            throw new MyHeapException("Could not get because key does not exist!");
        return heap.get(k);
    }




    @Override
    public void update(Integer k, Value v) throws MyHeapException {
        if(!(k instanceof java.lang.Integer))
            throw new MyHeapException("Address must be an Integer");
        if((java.lang.Integer) k == 0)
            throw new MyHeapException("Address must be different from 0");
        if(heap.get(k) == null)
            throw new MyHeapException("Could not update value because key does not exist!");
        heap.put(k, v);
    }

    @Override
    public String toFileString() {
        String fin = "";
        Set<Integer> keys = heap.keySet();
        for(Integer e : keys)
        {
            fin = fin + e.toString() + " --> " + heap.get(e).toString() + '\n';
        }
        if(!fin.equals(""))
        {
            fin = fin.substring(0, fin.length() - 1 );
        }
        else
            fin = "--Empty--";
        return fin;
    }

    @Override
    public void setContent(Map<Integer, Value> mp) {

        heap = mp;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public String toString()
    {
        String finString = "{";
        Set<Integer> keys = heap.keySet();
        for(Integer key : keys)
        {
            finString = finString + (java.lang.Integer)key + "->" + heap.get(key) + ", ";
        }
        if(!finString.equals("{"))
        {
            finString = finString.substring(0, finString.length() - 1);
            finString += "}";
        }
        else
            finString = "-- Empty --";
        return finString;
    }
    @Override
    public boolean isDefined(Integer k) throws MyHeapException {
        if(!(k instanceof java.lang.Integer))
            throw new MyHeapException("Address must be an Integer");
        return heap.get(k) != null;
    }
}
