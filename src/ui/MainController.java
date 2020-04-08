package ui;

import back.Bounds;
import back.Function;
import back.Functions;
import back.NonlinearEquations;
import back.solution.*;

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
import java.util.ArrayList;
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
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Double, Double> chart;
    private Graph mathGraph;

    @FXML
    private ChoiceBox<NonlinearEquationSolutionType> neMethod;

    @FXML
    private ChoiceBox<Function> neFunction;

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

    private double getNEAccuracy() {
        return Double.parseDouble(neAccuracy.getText());
    }

    @FXML
    private ChoiceBox<SystemOfNonlinearEquationsSolutionType> sneMethod;

    @FXML
    private ChoiceBox<NonlinearEquation> sneEquation1;

    @FXML
    private ChoiceBox<NonlinearEquation> sneEquation2;

    @FXML
    private TextField sneX1Start;

    private double getX1Start() {
        return Double.parseDouble(sneX1Start.getText());
    }

    @FXML
    private TextField sneX2Start;

    private double getX2Start() {
        return Double.parseDouble(sneX2Start.getText());
    }

    @FXML
    private TextField sneAccuracy;

    private double getSNEAccuracy() {
        return Double.parseDouble(sneAccuracy.getText());
    }

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
        neFunction.setValue(Functions.FUNCTION_1);

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

        sneEquation1.setValue(NonlinearEquations.EQUATION_1);
        sneEquation2.setValue(NonlinearEquations.EQUATION_3);

        sneX1Start.setText("0");
        sneX2Start.setText("0");
        sneAccuracy.setText("0.01");

        error.setText("");
        result.setText("");

        updateSNEChart();
    }

    @FXML
    private void neCalculate() {
        try {
            result.setText(
                    NonlinearEquationSolver.solveNonlinearEquation(
                            neFunction.getValue(),
                            new Bounds(
                                    getLeftBound(),
                                    getRightBound()
                            ),
                            getNEAccuracy(),
                            neMethod.getValue()
                    ).toString()
            );
        } catch (Exception e) {
            result.setText("");
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void sneCalculate() {
        try {
            ArrayList<NonlinearEquation> equations = new ArrayList<>();
            equations.add(sneEquation1.getValue());
            equations.add(sneEquation2.getValue());
            SystemOfNonlinearEquations system = new SystemOfNonlinearEquations(equations);

            ArrayList<Double> startValue = new ArrayList<>();
            startValue.add(getX1Start());
            startValue.add(getX2Start());

            result.setText(
                    SystemOfNonlinearEquationsSolver.solveSystemOfNonlinearEquations(
                            system,
                            startValue,
                            getSNEAccuracy(),
                            sneMethod.getValue()
                    ).toString()
            );
        } catch (Exception e) {
            result.setText("");
            error.setText(e.getMessage());
        }
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
            yAxis.setAutoRanging(true);
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
        try {
            mathGraph.clear();
            if (sneEquation1.getValue().equals(NonlinearEquations.EQUATION_2)) {
                mathGraph.plotLine(
                        sneEquation2.getValue()
                );
                mathGraph.plotLine(
                        sneEquation1.getValue()
                );
            } else {
                mathGraph.plotLine(
                        sneEquation1.getValue()
                );
                mathGraph.plotLine(
                        sneEquation2.getValue()
                );
            }
            xAxis.setAutoRanging(false);
            xAxis.setUpperBound(5);
            xAxis.setLowerBound(-5);
            xAxis.setTickUnit(1);
            yAxis.setAutoRanging(false);
            yAxis.setUpperBound(8);
            yAxis.setLowerBound(-2);
            yAxis.setTickUnit(1);
        } catch (Exception e) {
            error.setText(e.getMessage());
        }
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
                Functions.FUNCTION_1,
                Functions.FUNCTION_2,
                Functions.FUNCTION_3
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
                        neAccuracy.setText(oldValue);
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
        sneMethod.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    error.setText("");
                    result.setText("");
                }
        );

        ObservableList<NonlinearEquation> sneEquations
                = FXCollections.observableArrayList(
//                NonlinearEquations.EQUATION_TEST_1,
//                NonlinearEquations.EQUATION_TEST_2,
                NonlinearEquations.EQUATION_1,
                NonlinearEquations.EQUATION_2,
                NonlinearEquations.EQUATION_3
        );
        sneEquation1.setItems(sneEquations);
        sneEquation2.setItems(sneEquations);

        sneEquation1.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(sneEquation2.getValue())) {
                        sneEquation1.setValue(oldValue);

                        error.setText("This equation is already used!!!");
                    } else {
                        sneEquation1.setValue(newValue);
                        sneX1Start.setText("0");
                        sneX2Start.setText("0");
                        sneAccuracy.setText("0.01");

                        error.setText("");
                        result.setText("");

                        updateSNEChart();
                    }
                });
        sneEquation2.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(sneEquation1.getValue())) {
                        sneEquation2.setValue(oldValue);

                        error.setText("This equation is already used!!!");
                    } else {
                        sneEquation2.setValue(newValue);
                        sneX1Start.setText("0");
                        sneX2Start.setText("0");
                        sneAccuracy.setText("0.01");

                        error.setText("");
                        result.setText("");

                        updateSNEChart();
                    }
                });

        sneX1Start.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                sneX1Start.setText(oldValue);
            } else {
                error.setText("");
                result.setText("");
            }
        });
        sneX2Start.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                sneX2Start.setText(oldValue);
            } else {
                error.setText("");
                result.setText("");
            }
        });
        sneAccuracy.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,3}([.]\\d{0,5})?")) {
                sneAccuracy.setText(oldValue);
            } else {
                try {
                    if (Double.parseDouble(newValue) < 0.00001d) {
                        sneAccuracy.setText(oldValue);
                    } else {
                        error.setText("");
                        result.setText("");
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });
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
