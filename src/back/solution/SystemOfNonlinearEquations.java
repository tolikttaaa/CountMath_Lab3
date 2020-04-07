package back.solution;

import back.exception.UnavailableCodeException;

import java.util.ArrayList;

public class SystemOfNonlinearEquations {
    private ArrayList<NonlinearEquation> system;

    public SystemOfNonlinearEquations(ArrayList<NonlinearEquation> equations) {
        this.system = new ArrayList<>(equations);
    }

    public NonlinearEquation get(int index) {
        return system.get(index);
    }

    public ArrayList<ArrayList<NonlinearEquation>> getJacobianMatrix() throws UnavailableCodeException {
        ArrayList<ArrayList<NonlinearEquation>> res = new ArrayList<>();

        for (NonlinearEquation nonlinearEquation : system) {
            res.add(new ArrayList<>(nonlinearEquation.getDerivatives()));
        }

        return res;
    }
}
