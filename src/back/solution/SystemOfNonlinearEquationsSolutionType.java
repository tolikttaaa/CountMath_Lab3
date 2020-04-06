package back.solution;

public enum SystemOfNonlinearEquationsSolutionType {
    NEWTON_METHOD("Newton's method"),
    ITERATIVE_METHOD("Iterative method");

    private String methodName;

    SystemOfNonlinearEquationsSolutionType(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
