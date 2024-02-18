package Controller;

import datastructers.*;
import exceptions.ControllerException;
import exceptions.MyDictionaryException;
import exceptions.MyException;
import model.programstate.PrgState;
import model.types.RefType;
import model.values.*;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController{
    IRepository repo;
    ExecutorService executor = Executors.newFixedThreadPool(8);
    boolean debugFlag;
    public Controller(IRepository rep){
        repo = rep;
        debugFlag = true;
    }

    public IRepository getRepo()
    {
        return repo;
    }

//    public void loadFirstExample(){
//        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
//                        VarExp("v"))));
//        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
//
//
//        MyIList<Value> output = new MyList<Value>();
//        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
//        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
//        executionStack.push(ex1);
//        PrgState program = new PrgState(executionStack, symbolTable, output, fileTable);
//        repo.addPrg(program);
//    }
//
//    public void loadSecondExample(){
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
//                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)),
//                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
//                                        IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));
//        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
//        MyIList<Value> output = new MyList<Value>();
//        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
//        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
//        executionStack.push(ex2);
//        PrgState program = new PrgState(executionStack, symbolTable, output, fileTable);
//        repo.addPrg(program);
//    }
//
//
//    public void loadFourthExample(){
//        IStmt declaringV = new VarDeclStmt("v", new StringType());
//        IStmt assigningV = new AssignStmt("v", new ValueExp(new StringValue("in.txt")));
//        IStmt openingFile = new OpenRFile(new VarExp("v"));
//        IStmt declaringC = new VarDeclStmt("c", new IntType());
//        IStmt readingC = new ReadFile(new VarExp("v"), "c");
//        IStmt printingC = new PrintStmt(new VarExp("c"));
//        IStmt closingFile = new CloseRFile(new VarExp("v"));
//        IStmt fin = new CompStmt(declaringV, new CompStmt(assigningV, new CompStmt(openingFile, new CompStmt(declaringC, new CompStmt(readingC,new CompStmt(readingC.deepCopy(), new CompStmt(readingC.deepCopy(), new CompStmt(printingC, closingFile))))))));
//        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
//        MyIList<Value> output = new MyList<Value>();
//        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
//        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
//        executionStack.push(fin);
//        PrgState program = new PrgState(executionStack, symbolTable, output, fileTable);
//        repo.addPrg(program);
//    }
//    public void loadThirdExample(){
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
//                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
//                                        VarExp("v"))))));
//        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
//        MyIList<Value> output = new MyList<Value>();
//        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
//        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
//        executionStack.push(ex3);
//        PrgState program = new PrgState(executionStack, symbolTable, output, fileTable);
//        repo.addPrg(program);
//    }

    void addToRepo(PrgState state){
        repo.addPrg(state);
    }

//    public PrgState oneStep(PrgState state) throws MyException {
//        MyIStack<IStmt> stk = state.getStk();
//        if(stk.isEmpty()) throw new ControllerException("prgstate stack is empty");
//        IStmt crtStmt =  stk.pop();
//        return crtStmt.execute(state);
//    }

//    public void allStep() throws MyException {
//        PrgState prg = repo.getCrtPrg();
//        if(debugFlag)
//            System.out.println(prg);
//        //repo.logPrgStateExec();
//        while(!prg.getStk().isEmpty())
//        {
//            oneStep(prg);
//            if(debugFlag)
//                System.out.println(prg);
//            prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable(), prg.getHeap()), prg.getHeap().getContent()));
//
//            repo.logPrgStateExec();
//        }
//        prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable(), prg.getHeap()), prg.getHeap().getContent()));
//
//
//        System.out.println(prg);
//        repo.removePrg(0);
//    }

//    public void allStep(int index) throws MyException {
//        PrgState prg = repo.getCrtPrg();
//        System.out.println(prg);
//        while(!prg.getStk().isEmpty())
//        {
//            oneStep(prg);
//            System.out.println(prg);
//            prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable(), prg.getHeap()), prg.getHeap().getContent()));
//        }
//    }



    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap)
    {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void allStep() throws MyException{
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        System.out.println(repo);
        while(prgList.size() > 0)
        {
            oneStepForAllPrg(prgList);
            repo.getPrgList().get(0).getHeap().setContent(safeGarbageCollector(prgList, prgList.get(0).getHeap().getContent()));

            prgList = removeCompletedPrg(repo.getPrgList());
            System.out.println(repo);
        }
        executor.shutdownNow();

        repo.setPrgList(prgList);

        System.out.println(repo);

    }

    void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        prgList = removeCompletedPrg(repo.getPrgList());

        repo.setPrgList(prgList);
        if(prgList.size() == 0)
            return;
        List<Callable<PrgState>> callList = prgList.stream().map(p->((Callable<PrgState>)(()->{return p.oneStep();}))).collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future->{
                        try{
                            return future.get();
                        }
                        catch(Exception ignored)
                        {

                        }
                        return null;
                    })
                    .filter(p->p!=null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(prg->repo.logPrgStateExec(prg));
            repo.setPrgList(prgList);
            repo.getPrgList().get(0).getHeap().setContent(safeGarbageCollector(prgList, prgList.get(0).getHeap().getContent()));


        } catch (InterruptedException e) {
            throw new ControllerException(e.getMessage());
        }

    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }


    public Map<Integer, Value> safeGarbageCollector(List<PrgState> prgStates, Map<Integer, Value> heap) throws MyDictionaryException {
        List<Integer> addresses = new ArrayList<>();
        for (PrgState prgState : prgStates) {
            addresses.addAll(getAddrFromSymTable(prgState.getSymTable(), prgState.getHeap()));
        }
        addresses = addresses.stream().distinct().collect(Collectors.toList());
        final List<Integer> finalAddresses = addresses;
        return heap.entrySet().stream()
                .filter(e->finalAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(MyIDictionary<String,Value> symTableValues, MyIHeap<Integer, Value> hp) throws MyDictionaryException {
        Enumeration<String> e = symTableValues.keys();
        List<Value> Values = new ArrayList<>();
        while(e.hasMoreElements())
        {
            String a = e.nextElement();
            Values.add(symTableValues.lookup(a));
            Value v = symTableValues.lookup(a);
            if(v.getType().equals(new RefType(v.getType())))
            {
                RefValue refV = (RefValue) v;
                while(refV.getLocationType().equals(new RefType(refV.getLocationType())) && refV.getAddr() != -1)
                {
                    try{
                        RefValue nextRef = (RefValue)hp.lookup(refV.getAddr());
                        Values.add(nextRef);
                        refV = nextRef;
                    }
                    catch(MyException exc)
                    {
                        break;
                    }
                }
            }
        }
        return Values.stream()
                .filter(v-> v instanceof RefValue)
                .map(v ->{RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

}
