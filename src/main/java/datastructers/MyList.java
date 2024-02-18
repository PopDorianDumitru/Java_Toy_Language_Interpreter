package datastructers;

import exceptions.MyListException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    List<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }
    @Override
    public void remove(int index) throws MyListException{
        if(list.size() <= index)
            throw new MyListException("Could not remove because list does not contain that index!");
        list.remove(index);
    }

    @Override
    public void add(T v) {
        list.add(v);
    }


    @Override
    public T get(int index) throws MyListException {
        if(list.size() <= index)
            throw new MyListException("Could not remove because list does not contain that index!");
        return list.get(index);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public void setList(List<T> newList) {
        list = newList;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toFileString() {
        String fin = "";
        for(T v : list)
        {
            fin = fin + v.toString() + "\n";
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
