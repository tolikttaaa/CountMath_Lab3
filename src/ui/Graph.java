package ui;

import back.Function;
import back.NonlinearEquations;
import back.Pair;
import back.exception.NotAllowedScopeException;
import back.exception.NotImplementedMethodException;
import back.exception.UnavailableCodeException;
import back.solution.NonlinearEquation;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Graph {
    private XYChart<Double, Double> graph;

    public Graph(final XYChart<Double, Double> graph) {
        this.graph = graph;
    }

    public void plotLine(final Function function, final double lowerBound, final double upperBound)
            throws NotAllowedScopeException, UnavailableCodeException {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (double x = lowerBound; x <= upperBound; x += (upperBound - lowerBound) / 1000d) {
            plotPoint(x, function.getValue(x), series);
        }

        graph.getData().add(series);
    }

    public void plotLine(final NonlinearEquation equation)
            throws UnavailableCodeException, NotImplementedMethodException {
        if (equation.equals(NonlinearEquations.EQUATION_2)) {
            final XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
            final XYChart.Series<Double, Double> series3 = new XYChart.Series<>();

            ArrayList<Pair<Double, Double>> dots = equation.getPlotData();

            for (int i = 0; i < dots.size() / 2; i++) {
                plotPoint(dots.get(i).first, dots.get(i).second, series2);
                plotPoint(dots.get(i + dots.size() / 2).first, dots.get(i + dots.size() / 2).second, series3);
            }

            graph.getData().add(series2);
            graph.getData().add(series3);
        } else {
            final XYChart.Series<Double, Double> series = new XYChart.Series<>();

            for (Pair<Double, Double> dot : equation.getPlotData()) {
                plotPoint(dot.first, dot.second, series);
            }

            graph.getData().add(series);
        }
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public void clear() {
        graph.getData().clear();
    }
}