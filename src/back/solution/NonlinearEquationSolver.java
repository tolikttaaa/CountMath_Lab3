package back.solution;

import back.Bounds;
import back.DerivativeFunc;
import back.Function;
import back.Interval;
import back.exception.*;

public class NonlinearEquationSolver {
    static private final long N_MAX_VALUE = 10_000_000L;
    static private final double EPS = 1e-9d;

    static public NonlinearEquationSolutionResult solveNonlinearEquation(
            Function function,
            Bounds bounds,
            double accuracy,
            NonlinearEquationSolutionType solutionType
    ) throws
            NotAllowedScopeException,
            NoSolutionException,
            NotImplementedMethodException,
            UnavailableCodeException {
        checkAllowedScope(function, bounds);

        if (isSolvable(function, bounds)) {
            if (Math.abs(function.getValue(bounds.getLeftBound())) < EPS) {
                return new NonlinearEquationSolutionResult(
                        bounds.getLeftBound(),
                        function.getValue(bounds.getLeftBound()),
                        1
                );
            }

            if (Math.abs(function.getValue(bounds.getRightBound())) < EPS) {
                return new NonlinearEquationSolutionResult(
                        bounds.getRightBound(),
                        function.getValue(bounds.getRightBound()),
                        1
                );
            }

            switch (solutionType) {
                case BISECTION_METHOD:
                    return bisectionMethodSolution(function, bounds, accuracy);
                case ITERATIVE_METHOD:
                    return iterativeMethodSolution(function, bounds, accuracy);
                case METHOD_OF_CHORDS:
                case NEWTON_METHOD:
                default:
                    throw new NotImplementedSolutionException();
            }
        } else {
            throw new NoSolutionException("f(x_l)*f(x_r) > 0");
        }
    }

    static private NonlinearEquationSolutionResult bisectionMethodSolution(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotAllowedScopeException,
            NoSolutionException,
            UnavailableCodeException {
        double left = bounds.getLeftBound();
        double right = bounds.getRightBound();

        for (long i = 0; i < N_MAX_VALUE; i++) {
            double x = (left + right) / 2.0d;

            double leftValue = function.getValue(left);
            double value = function.getValue(x);

            if (value * leftValue > 0) {
                left = x;
            } else {
                right = x;
            }

            if (Math.abs(right - left) < accuracy || Math.abs(value) < accuracy) {
                return new NonlinearEquationSolutionResult(x, value, i);
            }
        }

        throw new NoSolutionException("count of iterations more than 10_000_000");
    }

    static private NonlinearEquationSolutionResult iterativeMethodSolution(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotImplementedMethodException,
            NotAllowedScopeException,
            NoSolutionException,
            UnavailableCodeException {
        double lambda = -1 / function.getDerivative().getMaxValue(bounds);
        Function phi = new DerivativeFunc() {
            @Override
            public double get(double argument) throws UnavailableCodeException {
                try {
                    return argument + lambda  * function.getValue(argument);
                } catch (Exception e) {
                    throw new UnavailableCodeException();
                }
            }

            @Override
            public Interval[] getNotAllowedScope() throws UnavailableCodeException {
                try {
                    return function.getDerivative().getNotAllowedScope();
                } catch (NotImplementedMethodException e) {
                    throw new UnavailableCodeException();
                }
            }
        };

        double prev_x = bounds.getLeftBound();
        double x;

        for (long i = 0; i < N_MAX_VALUE; i++) {
            x = phi.getValue(prev_x);

            if (Math.abs(x - prev_x) < accuracy || Math.abs(function.getValue(x)) < accuracy) {
                return new NonlinearEquationSolutionResult(x, function.getValue(x), i);
            }

            prev_x = x;
        }

        prev_x = bounds.getRightBound();

        for (long i = 0; i < N_MAX_VALUE; i++) {
            x = phi.getValue(prev_x);

            if (Math.abs(x - prev_x) < accuracy || Math.abs(function.getValue(x)) < accuracy) {
                return new NonlinearEquationSolutionResult(x, function.getValue(x), i);
            }

            prev_x = x;
        }

        throw new NoSolutionException("count of iterations more than 10_000_000");
    }

    static private boolean isSolvable(Function function, Bounds bounds)
            throws NotAllowedScopeException, UnavailableCodeException {
        return function.getValue(bounds.getLeftBound())
                * function.getValue(bounds.getRightBound()) < EPS;
    }

    static private void checkAllowedScope(Function function, Bounds bounds)
            throws NotAllowedScopeException, UnavailableCodeException {
        for (Interval notAllowedScope : function.getNotAllowedScope()) {
            if (notAllowedScope.isPoint()) continue;
            if (notAllowedScope.isIntersect(bounds)) {
                throw new NotAllowedScopeException();
            }
        }
    }
}
