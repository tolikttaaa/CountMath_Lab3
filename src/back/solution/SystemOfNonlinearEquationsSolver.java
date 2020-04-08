package back.solution;

import back.exception.NoSolutionException;
import back.exception.NotImplementedMethodException;
import back.exception.NotImplementedSolutionException;
import back.exception.UnavailableCodeException;
import back.solution.gauss.LinearEquation;
import back.solution.gauss.SystemOfLinearEquations;
import back.solution.gauss.SystemOfLinearEquationsSolver;

import java.util.ArrayList;

public class SystemOfNonlinearEquationsSolver {
    static private final long N_MAX_VALUE = 10_000_000L;

    static public SystemOfNonlinearEquationsSolutionResult solveSystemOfNonlinearEquations(
            SystemOfNonlinearEquations system,
            ArrayList<Double> startValue,
            double accuracy,
            SystemOfNonlinearEquationsSolutionType solutionType
    ) throws
            NoSolutionException,
            NotImplementedMethodException,
            UnavailableCodeException {
        switch (solutionType) {
            case NEWTON_METHOD:
                return newtonMethodSolution(system, startValue, accuracy);
            case ITERATIVE_METHOD:
            default:
                throw new NotImplementedSolutionException();
        }
    }

    static private SystemOfNonlinearEquationsSolutionResult newtonMethodSolution(
            SystemOfNonlinearEquations system,
            ArrayList<Double> startValue,
            double accuracy
    ) throws NoSolutionException, UnavailableCodeException {
        ArrayList<Double> x = new ArrayList<>(startValue);

        for (int i = 0; i < N_MAX_VALUE; i++) {
            ArrayList<Double> prev_x = new ArrayList<>(x);

            SystemOfLinearEquations<Double, LinearEquation> linearSystem = new SystemOfLinearEquations<>();

            ArrayList<ArrayList<NonlinearEquation>> jacobianMatrix = system.getJacobianMatrix();
            for (int j = 0; j < x.size(); j++) {
                ArrayList<Double> multipliers = new ArrayList<>();

                for (int k = 0; k < x.size(); k++) {
                    multipliers.add(jacobianMatrix.get(j).get(k).getValue(prev_x));
                }

                LinearEquation curLinearEquation = new LinearEquation(multipliers, -system.get(j).getValue(prev_x));
                linearSystem.push(curLinearEquation);
            }

            ArrayList<Double> solution = new ArrayList<>(SystemOfLinearEquationsSolver.getSolution(linearSystem));

            for (int j = 0; j < solution.size(); j++) {
                x.set(j, prev_x.get(j) + solution.get(j));
            }

            if (getMeasuredError(x, prev_x) < accuracy) {
                return new SystemOfNonlinearEquationsSolutionResult(x, i);
            }
        }

        throw new NoSolutionException("count of iterations more than 10_000_000");
    }

    static private double getMeasuredError(ArrayList<Double> x, ArrayList<Double> prev_x) {
        double max = 0;

        for (int i = 0; i < x.size(); i++) {
            max = Math.max(Math.abs(prev_x.get(i) - x.get(i)), max);
        }

        return max;
    }
}
