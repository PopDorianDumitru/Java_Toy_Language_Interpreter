module view.gui.interpreterwithui {
    requires javafx.controls;
    requires javafx.fxml;


    opens view.gui.interpreterwithui to javafx.fxml;
    exports interpreter;
    exports Controller;
    opens Controller;
    exports model.statements;
    exports repository;
    exports exceptions;
    exports datastructers;
    exports model.programstate;
    exports view.gui.interpreterwithui;
}