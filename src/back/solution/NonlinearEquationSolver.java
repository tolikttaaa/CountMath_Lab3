package back.solution;

import back.Bounds;
import back.DerivativeFunc;
import back.Function;
import back.Interval;
import back.exception.NoSolutionException;
import back.exception.NotAllowedScopeException;
import back.exception.NotImplementedMethodException;
import back.exception.NotImplementedSolutionException;

public class NonlinearEquationSolver {
    static private final long N_MAX_VALUE = 100_000_000L;
    static private final double EPS = 1e-9d;

    public NonlinearEquationSolutionResult solveNonlinearEquation(
            Function function,
            Bounds bounds,
            double accuracy,
            NonlinearEquationSolutionType solutionType
    ) throws
            NotAllowedScopeException,
            NoSolutionException,
            NotImplementedMethodException {
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

    private NonlinearEquationSolutionResult bisectionMethodSolution(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotAllowedScopeException,
            NoSolutionException {
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

        throw new NoSolutionException("count of iterations more than 100_000_000");
    }

    private NonlinearEquationSolutionResult iterativeMethodSolution(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotImplementedMethodException,
            NotAllowedScopeException,
            NoSolutionException {
        double lambda = -1 / function.getDerivative().getMaxValue(bounds);
        Function phi = new DerivativeFunc() {
            @Override
            public double get(double argument) {
                try {
                    return argument + lambda  * function.getDerivative().getValue(argument);
                } catch (Exception e) {
                    System.err.println(e.toString());
                    System.err.println("Unavailable code!!!");
                }

                return Double.NaN;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                try {
                    return function.getDerivative().getNotAllowedScope();
                } catch (NotImplementedMethodException e) {
                    System.err.println(e.toString());
                    System.err.println("Unavailable code!!!");
                }

                return new Interval[0];
            }
        };

        double prev_x = bounds.getLeftBound();
        double x;

        for (long i = 0; i < N_MAX_VALUE; i++) {
            x = phi.getValue(prev_x);

            if (Math.abs(x - prev_x) < accuracy || Math.abs(function.getValue(x)) < accuracy) {
                return new NonlinearEquationSolutionResult(x, function.getValue(x), i);
            }
        }

        throw new NoSolutionException("count of iterations more than 100_000_000");
    }

    private boolean isSolvable(Function function, Bounds bounds)
            throws NotAllowedScopeException {
        return function.getValue(bounds.getLeftBound())
                * function.getValue(bounds.getRightBound()) < EPS;
    }

    public NonlinearEquationSolutionResult solveNonlinearEquation(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotAllowedScopeException,
            NoSolutionException,
            NotImplementedMethodException {
        return solveNonlinearEquation(
                function,
                bounds,
                accuracy,
                NonlinearEquationSolutionType.BISECTION_METHOD
        );
    }

    private void checkAllowedScope(Function function, Bounds bounds)
            throws NotAllowedScopeException {
        for (Interval notAllowedScope : function.getNotAllowedScope()) {
            if (notAllowedScope.isPoint()) continue;
            if (notAllowedScope.isIntersect(bounds)) {
                throw new NotAllowedScopeException();
            }
        }
    }
}
