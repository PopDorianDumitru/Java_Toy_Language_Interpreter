package Controller;

import exceptions.MyException;
import interpreter.Interpreter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.statements.IStmt;
import view.gui.interpreterwithui.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProgramSelectorController implements Initializable {

    public List<IStmt> programs;
    @FXML
    ListView<String> ProgramListView;

    public ProgramSelectorController() throws MyException {
    }

    @FXML
    public void populateList()
    {
        programs.forEach(pr->{
            ProgramListView.getItems().add(pr.toString());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            programs = Interpreter.getPrgStates();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        populateList();
    }
    @FXML
    void StartProgram() throws IOException, MyException {
            if(ProgramListView.getSelectionModel().getSelectedIndex() == -1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No program selected error");
                alert.setHeaderText("No program selected");
                alert.setContentText("You need to select a program to run");
                alert.showAndWait();
            }
            else
            {
                int getSelectedIndex = ProgramListView.getSelectionModel().getSelectedIndex();
                Stage window = (Stage) ProgramListView.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainProgramWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                window.setTitle("Interpreter");
                window.setScene(scene);
                MainProgramController controller =  fxmlLoader.getController();
                controller.loadProgram(programs.get(getSelectedIndex), getSelectedIndex);

            }
    }
}
