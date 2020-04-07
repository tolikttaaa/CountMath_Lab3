package back.solution;

import back.Pair;
import back.exception.UnavailableCodeException;

import java.util.ArrayList;

public abstract class NonlinearEquation {
    public abstract double getValue(ArrayList<Double> arguments);
    public abstract ArrayList<NonlinearEquation> getDerivatives() throws UnavailableCodeException;
    public abstract ArrayList<Pair<Double, Double>> getPlotData() throws UnavailableCodeException;
    public abstract String toString();
}
