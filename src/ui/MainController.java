package ui;

import back.Bounds;
import back.Function;
import back.NonlinearEquation;
import back.solution.NonlinearEquationSolutionType;
import back.solution.NonlinearEquationSolver;
import back.solution.SystemOfNonlinearEquationsSolutionType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final double EPS = 1e-9;
    private static final NonlinearEquationSolver neSolver = new NonlinearEquationSolver();

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
    private NumberAxis xAxis;

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
        error.setVisible(true);
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

        //TODO: sne

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

    private double getLeftBound() {
        return Double.parseDouble(neLeftBound.getText());
    }

    @FXML
    private TextField neRightBound;

    private double getRightBound() {
        return Double.parseDouble(neRightBound.getText());
    }

    @FXML
    private TextField neAccuracy;

    @FXML
    private void neCalculate() {
        try {
            result.setText(neSolver.solveNonlinearEquation(
                    neFunction.getValue(),
                    new Bounds(
                            getLeftBound(),
                            getRightBound()
                    ),
                    Double.parseDouble(neAccuracy.getText()), neMethod.getValue()
            ).toString());
        } catch (Exception e) {
            result.setText("");
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void sneCalculate() {
        //TODO: sne
    }

    @FXML
    private void updateNEChart() {
        try {
            mathGraph.clear();
            mathGraph.plotLine(
                    neFunction.getValue(),
                    getLeftBound(),
                    getRightBound()
            );
            xAxis.setAutoRanging(false);
            double step = (getRightBound() - getLeftBound()) / 10;
            xAxis.setLowerBound(getLeftBound() - step);
            xAxis.setUpperBound(getRightBound() + step);
            xAxis.setTickUnit(step);

        } catch (Exception e) {
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void updateSNEChart() {
        //TODO: sne
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
        neMethod.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    error.setText("");
                    result.setText("");
                }
        );

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

                    error.setText("");
                    result.setText("");

                    updateNEChart();
                });

        neLeftBound.setText("-10.0");
        neRightBound.setText("10.0");
        neAccuracy.setText("0.01");

        neLeftBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                neLeftBound.setText(oldValue);
            } else {
                try {
                    if (Double.parseDouble(newValue) > getRightBound() - EPS) {
                        neLeftBound.setText(oldValue);
                    } else {
                        error.setText("");
                        result.setText("");
                        updateNEChart();
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });
        neRightBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,3}([.]\\d{0,4})?")) {
                neRightBound.setText(oldValue);
            } else {
                try {
                    if (Double.parseDouble(newValue) < getLeftBound() + EPS) {
                        neLeftBound.setText(oldValue);
                    } else {
                        error.setText("");
                        result.setText("");
                        updateNEChart();
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });
        neAccuracy.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,3}([.]\\d{0,5})?")) {
                neAccuracy.setText(oldValue);
            } else {
                try {
                    if (Double.parseDouble(newValue) < 0.00001d) {
                        neLeftBound.setText(oldValue);
                    } else {
                        error.setText("");
                        result.setText("");
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });
    }

    private void sneInit() {
        ObservableList<SystemOfNonlinearEquationsSolutionType> sneMethods
                = FXCollections.observableArrayList(
                SystemOfNonlinearEquationsSolutionType.NEWTON_METHOD,
                SystemOfNonlinearEquationsSolutionType.ITERATIVE_METHOD
        );
        sneMethod.setItems(sneMethods);

        //TODO: sne
    }

    private void chartInit() {
        mathGraph = new Graph(chart);
        chart.setCreateSymbols(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        neInit();
        sneInit();
        chartInit();
        setVisibleHelpPane();
    }
}
