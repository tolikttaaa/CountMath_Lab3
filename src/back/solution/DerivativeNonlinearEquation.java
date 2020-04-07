package back.solution;

import back.Pair;
import back.exception.UnavailableCodeException;

import java.util.ArrayList;

public abstract class DerivativeNonlinearEquation extends NonlinearEquation {
    public ArrayList<NonlinearEquation> getDerivatives() throws UnavailableCodeException {
        throw new UnavailableCodeException();
    }

    public ArrayList<Pair<Double, Double>> getPlotData() throws UnavailableCodeException {
        throw new UnavailableCodeException();
    }

    public String toString() {
        return null;
    }
}
