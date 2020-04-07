package back.solution.gauss;

import java.util.ArrayList;

public class SystemOfLinearEquationsSolver {
    public ArrayList<Double> getSolution(SystemOfLinearEquations<Double, LinearEquation> system) {
        for (int i = 0; i < system.size() - 1; i++){
            for (int j = i + 1; j < system.size(); j++){
                Double k = system.get(j).findCoefficient(system.get(j).at(i), system.get(i).at(i));
                system.get(j).mul(k);
                system.get(j).addEquation(system.get(i));
            }
        }

        ArrayList<Double> res = new ArrayList<>(system.size());
        for (int i = system.size() - 1; i >= 0; i--) {
            Double sum = 0.0d;
            int j = system.size() - 1;

            for (; j > i; j--) {
                sum += system.itemAt(i, j) * res.get(j);
            }

            res.set(i, (system.itemAt(i, system.size()) - sum) / system.itemAt(i, j));
        }

        return res;
    }
}
