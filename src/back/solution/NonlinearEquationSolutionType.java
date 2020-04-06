package back.solution;

public enum NonlinearEquationSolutionType {
    BISECTION_METHOD("Bisection method"),
    METHOD_OF_CHORDS("Method of chords"),
    NEWTON_METHOD("Newton's method"),
    ITERATIVE_METHOD("Iterative method");

    private String methodName;

    NonlinearEquationSolutionType(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
