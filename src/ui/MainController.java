package ui;

import back.solution.NonlinearEquationSolutionType;
import back.solution.SystemOfNonlinearEquationsSolutionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Label helpPane;

    @FXML
    VBox neInputPane;

    @FXML
    VBox sneInputPane;

    @FXML
    Label result;

    @FXML
    Label error;

    @FXML
    LineChart chart;

    @FXML
    void setVisibleHelpPane() {
        helpPane.setVisible(true);
        neInputPane.setVisible(false);
        sneInputPane.setVisible(false);
        error.setVisible(false);
        result.setVisible(false);
    }

    @FXML
    void setVisibleNEInputPane() {
        neInit();
        helpPane.setVisible(false);
        neInputPane.setVisible(true);
        sneInputPane.setVisible(false);
        error.setVisible(false);
        result.setVisible(true);
    }

    @FXML
    void setVisibleSNEInputPane() {
        sneInit();
        helpPane.setVisible(false);
        neInputPane.setVisible(false);
        sneInputPane.setVisible(true);
        error.setVisible(true);
        result.setVisible(true);
    }

    @FXML
    ChoiceBox<NonlinearEquationSolutionType> neMethod;

    @FXML
    ChoiceBox neFunction;

    @FXML
    ChoiceBox<SystemOfNonlinearEquationsSolutionType> sneMethod;

    @FXML
    ChoiceBox sneFunction;

    @FXML
    void neCalculate() {
        //TODO
    }

    @FXML
    void sneCalculate() {
        //TODO
    }

    private void neInit() {
        //TODO: clear ne
    }

    private void sneInit() {
        //TODO: clear sne
    }

    private void chartInit() {
        //TODO: chart initialization
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<NonlinearEquationSolutionType> neMethods = FXCollections.observableArrayList(
                NonlinearEquationSolutionType.BISECTION_METHOD,
                NonlinearEquationSolutionType.METHOD_OF_CHORDS,
                NonlinearEquationSolutionType.NEWTON_METHOD,
                NonlinearEquationSolutionType.ITERATIVE_METHOD
        );
        neMethod.setItems(neMethods);
        neMethod.setValue(NonlinearEquationSolutionType.BISECTION_METHOD);

        ObservableList<SystemOfNonlinearEquationsSolutionType> sneMethods = FXCollections.observableArrayList(
                SystemOfNonlinearEquationsSolutionType.NEWTON_METHOD,
                SystemOfNonlinearEquationsSolutionType.ITERATIVE_METHOD
        );
        sneMethod.setItems(sneMethods);
        sneMethod.setValue(SystemOfNonlinearEquationsSolutionType.NEWTON_METHOD);

        //TODO: function initialization

        chartInit();
        setVisibleHelpPane();
    }
}
