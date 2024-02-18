package repository;

import exceptions.MyException;
import exceptions.MyListException;
import model.programstate.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
//    public PrgState getCrtPrg() throws MyListException;
//    public PrgState getCrtPrg(int index) throws MyListException;
    public void addPrg(PrgState state);

    public PrgState getById(int id);

    public List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newStates);

    public void removePrg(int index) throws MyListException;
    public void logPrgStateExec(PrgState state);
    public String toString();
}
