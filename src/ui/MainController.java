package ui;

import back.Function;
import back.NonlinearEquation;
import back.solution.NonlinearEquationSolutionType;
import back.solution.SystemOfNonlinearEquationsSolutionType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final double EPS = 1e-9;

    @FXML
    private Label helpPane;

    @FXML
    private VBox neInputPane;

    @FXML
    private VBox sneInputPane;

    @FXML
    private Label result;

    @FXML
    private Label error;

    @FXML
    private LineChart<Double, Double> chart;
    private Graph mathGraph;

    @FXML
    private void setVisibleHelpPane() {
        helpPane.setVisible(true);
        neInputPane.setVisible(false);
        sneInputPane.setVisible(false);
        error.setVisible(false);
        result.setVisible(false);

        mathGraph.clear();
    }

    @FXML
    private void setVisibleNEInputPane() {
        helpPane.setVisible(false);
        neInputPane.setVisible(true);
        sneInputPane.setVisible(false);
        error.setVisible(false);
        result.setVisible(true);

        neMethod.setValue(NonlinearEquationSolutionType.BISECTION_METHOD);
        neFunction.setValue(NonlinearEquation.FUNCTION_1);

        neLeftBound.setText("-10.0");
        neRightBound.setText("10.0");
        neAccuracy.setText("0.01");

        error.setText("");
        result.setText("");

        updateNEChart();
    }

    @FXML
    private void setVisibleSNEInputPane() {
        helpPane.setVisible(false);
        neInputPane.setVisible(false);
        sneInputPane.setVisible(true);
        error.setVisible(true);
        result.setVisible(true);

        sneMethod.setValue(SystemOfNonlinearEquationsSolutionType.NEWTON_METHOD);

        error.setText("");
        result.setText("");

        updateSNEChart();
    }

    @FXML
    private ChoiceBox<NonlinearEquationSolutionType> neMethod;

    @FXML
    private ChoiceBox<Function> neFunction;

    @FXML
    private ChoiceBox<SystemOfNonlinearEquationsSolutionType> sneMethod;

    @FXML
    private ChoiceBox sneFunction;

    @FXML
    private TextField neLeftBound;

    @FXML
    private TextField neRightBound;

    @FXML
    private TextField neAccuracy;

    @FXML
    private void neCalculate() {
        //TODO
    }

    @FXML
    private void sneCalculate() {
        //TODO
    }

    @FXML
    private void updateNEChart() {
        try {
            mathGraph.clear();
            mathGraph.plotLine(
                    neFunction.getValue(),
                    Double.parseDouble(neLeftBound.getText()),
                    Double.parseDouble(neRightBound.getText())
            );
        } catch (Exception e) {
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void updateSNEChart() {
        //TODO
    }

    private void neInit() {
        ObservableList<NonlinearEquationSolutionType> neMethods
                = FXCollections.observableArrayList(
                NonlinearEquationSolutionType.BISECTION_METHOD,
                NonlinearEquationSolutionType.METHOD_OF_CHORDS,
                NonlinearEquationSolutionType.NEWTON_METHOD,
                NonlinearEquationSolutionType.ITERATIVE_METHOD
        );
        neMethod.setItems(neMethods);

        ObservableList<Function> neFunctions = FXCollections.observableArrayList(
                NonlinearEquation.FUNCTION_1,
                NonlinearEquation.FUNCTION_2,
                NonlinearEquation.FUNCTION_3
        );
        neFunction.setItems(neFunctions);
        neFunction.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    neFunction.setValue(newValue);
                    neLeftBound.setText("-10.0");
                    neRightBound.setText("10.0");
                    neAccuracy.setText("0.01");
                    updateNEChart();
                });

        neLeftBound.setText("-10.0");
        neRightBound.setText("10.0");
        neAccuracy.setText("0.01");

        neLeftBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")
                    || Double.parseDouble(newValue) > Double.parseDouble(neRightBound.getText()) - EPS) {
                neLeftBound.setText(oldValue);
            } else {
                updateNEChart();
            }
        });
        neRightBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")
                    || Double.parseDouble(newValue) < Double.parseDouble(neLeftBound.getText()) + EPS) {
                neLeftBound.setText(oldValue);
            } else {
                updateNEChart();
            }
        });
        neAccuracy.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}([.]\\d{0,4})?")
                    || Double.parseDouble(newValue) < 0.00001d) {
                neAccuracy.setText(oldValue);
            }
        });;
    }

    private void sneInit() {
        ObservableList<SystemOfNonlinearEquationsSolutionType> sneMethods
                = FXCollections.observableArrayList(
                SystemOfNonlinearEquationsSolutionType.NEWTON_METHOD,
                SystemOfNonlinearEquationsSolutionType.ITERATIVE_METHOD
        );
        sneMethod.setItems(sneMethods);

        //TODO: clear sne
    }

    private void chartInit() {
        mathGraph = new Graph(chart);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        neInit();
        sneInit();
        chartInit();
        setVisibleHelpPane();
    }
}
