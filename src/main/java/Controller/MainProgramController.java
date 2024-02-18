package Controller;

import datastructers.*;
import exceptions.MyDictionaryException;
import exceptions.MyException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.programstate.PrgState;
import model.statements.IStmt;
import model.types.Type;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import view.gui.interpreterwithui.HelloApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainProgramController implements Initializable {

    @FXML
    TextField ProgramStateNumber;

    @FXML
    TableView<HeapTableValue> HeapTableView;

    @FXML
    TableColumn<HeapTableValue, Integer> HeapTableAddressColumn;
    @FXML
    TableColumn<HeapTableValue,Value> HeapTableValueColumn;

    @FXML
    ListView<Value> OutListView;
    @FXML
    ListView<String> FileTableListView;
    @FXML
    ListView<Integer> PrgStatesListView;
    @FXML
    TableView<SymTableValue> SymTableView;
    @FXML
    TableColumn<SymTableValue,String> SymTableNameColumn;
    @FXML
    TableColumn<SymTableValue,Value> SymTableValueColumn;
    @FXML
    ListView<IStmt> ExeStackListView;
    @FXML
    Button RunProgramButton;
    @FXML
            TableView<BarrierTableValue> BarrierTable;
    @FXML
            TableColumn<BarrierTableValue, Integer> IndexColumn;
    @FXML
            TableColumn<BarrierTableValue, Integer> ValueColumn;
    @FXML
            TableColumn<BarrierTableValue, ArrayList<Integer>>ListColumn;

    int selected = -1;
    public Controller programController;


    public MainProgramController() throws MyException {

    }

    @FXML
    public void OneStepExecute() throws MyException, IOException {
        if(RunProgramButton.getText().equals("Go back to program selection screen"))
        {
            Stage window = (Stage) RunProgramButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            window.setTitle("Interpreter");
            window.setScene(scene);
            return;
        }
        programController.oneStepForAllPrg(programController.getRepo().getPrgList());

        if(programController.getRepo().getPrgList().isEmpty())
            RunProgramButton.setText("Go back to program selection screen");

        populateProgramStatesListView();

        populateBarrierTable();
        populateHeapTableView();
        populateOutListView();
        populateFileListView();
        populateSymTableView();
        populateExeStackList();
        updateProgramStateNumber();
    }

    private void populateBarrierTable() throws MyDictionaryException {
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        BarrierTable.getItems().clear();
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();


        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> sym = programController.getRepo().getById(programID).getBarrier();
        Enumeration<Integer> e = sym.keys();
        while(e.hasMoreElements())
        {
            Integer a = e.nextElement();
            BarrierTable.getItems().add(new BarrierTableValue(a, sym.lookup(a).getKey(), sym.lookup(a).getValue()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProgramStateNumber.setText("There is one program currently running");
        ProgramStateNumber.setDisable(true);
        HeapTableAddressColumn.setCellValueFactory(new PropertyValueFactory<HeapTableValue, Integer>("address"));
        HeapTableValueColumn.setCellValueFactory(new PropertyValueFactory<HeapTableValue, Value>("val"));
        SymTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SymTableValueColumn.setCellValueFactory(new PropertyValueFactory<>("val"));
        ValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        ListColumn.setCellValueFactory(new PropertyValueFactory<>("list"));
        IndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        HeapTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        SymTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void loadProgram(IStmt statement, int index) throws MyException, IOException {
        MyIDictionary<String, Type> typeEnv1 = new MyDictionary<>();
        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
        MyIList<Value> output = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
        MyIHeap<Integer, Value> heap1 = new MyHeap<Integer,Value>();
        MyITable<Integer, Pair<Integer, ArrayList<Integer>>> Barrier = new MyTable<>();
        executionStack.push(statement.deepCopy());
        try{
            statement.typecheck(typeEnv1);
        }
        catch(MyException exe)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Program Selected Broken");
            alert.setHeaderText("The program selected has type problems");
            alert.setContentText("You need to select another program or fix the one you are trying to run");
            alert.showAndWait();
            Stage window = (Stage) RunProgramButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            window.setTitle("Interpreter");
            window.setScene(scene);
            return;
        }
        PrgState program = new PrgState(executionStack, symbolTable, output, fileTable, heap1, Barrier);
        IRepository repo1 = new Repository(program, "log" + index + ".txt");
        programController = new Controller(repo1);
        populateProgramStatesListView();

        populateBarrierTable();
        populateHeapTableView();
        populateOutListView();
        populateFileListView();
        populateProgramStatesListView();
        populateSymTableView();
        populateExeStackList();
        selected = 0;
        PrgStatesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                try {
                    selected = PrgStatesListView.getSelectionModel().getSelectedIndex();
                    changeThread();
                } catch (MyDictionaryException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void changeThread() throws MyDictionaryException {
        populateHeapTableView();
        populateOutListView();
        populateFileListView();
        populateSymTableView();
        populateBarrierTable();
        populateExeStackList();
    }

    public void updateProgramStateNumber()
    {
        if(programController.getRepo().getPrgList().isEmpty())
        {
            ProgramStateNumber.setText("No program running");
            return;
        }
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();

        ProgramStateNumber.clear();
        if(programController.getRepo().getPrgList().size() == 1)
            ProgramStateNumber.setText("1 program running");
        else
            ProgramStateNumber.setText("" + programController.getRepo().getPrgList().size() + " programs running");
    }

    public void populateExeStackList() {
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();



        ExeStackListView.getItems().clear();
        programController.getRepo().getById(programID).getStk().getStatements().forEach(s -> {
            ExeStackListView.getItems().add(s);
        });
    }

    public void populateFileListView() throws MyDictionaryException {
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        FileTableListView.getItems().clear();
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();


        MyIDictionary<String, java.io.BufferedReader> FileTable = programController.getRepo().getById(programID).getFileTable();
        Enumeration<String> e = FileTable.keys();
        while(e.hasMoreElements())
        {
            String a = e.nextElement();
            FileTableListView.getItems().add(a + " " + FileTable.lookup(a));
        }
    }

    public void populateProgramStatesListView()
    {
        int oldStyle  = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(programController.getRepo().getPrgList().isEmpty())
            return;


        PrgStatesListView.getItems().clear();
        List<PrgState> states = programController.getRepo().getPrgList();
        states.forEach(st->PrgStatesListView.getItems().add(st.getId()));
        selected = oldStyle;
        if(selected < PrgStatesListView.getItems().size())
            PrgStatesListView.getSelectionModel().select(selected);
        else
            selected = 0;
    }

    public void populateOutListView(){
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        OutListView.getItems().clear();
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();


        MyIList<Value> outString = programController.getRepo().getById(programID).getOut();
        outString.getList().forEach(v->{
            OutListView.getItems().add(v);
        });
    }

    public void populateSymTableView() throws MyDictionaryException {
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        SymTableView.getItems().clear();
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();


        MyIDictionary<String, Value> sym = programController.getRepo().getById(programID).getSymTable();
        Enumeration<String> e = sym.keys();
        while(e.hasMoreElements())
        {
            String a = e.nextElement();
            SymTableView.getItems().add(new SymTableValue(a, sym.lookup(a)));
        }
    }

    public void populateHeapTableView()
    {
        if(programController.getRepo().getPrgList().isEmpty())
            return;
        HeapTableView.getItems().clear();
        int getIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
        if(getIndex < 0)
            getIndex = 0;
        int programID = programController.getRepo().getPrgList().get(getIndex).getId();


        MyIHeap<Integer, Value> hp = programController.getRepo().getById(programID).getHeap();
        Map<Integer, Value> values = hp.getContent();

        values.forEach((inte, val)->{
            HeapTableView.getItems().add(new HeapTableValue(inte, val));
        });
    }

}
