package back.solution;

public class NonlinearEquationSolutionResult {
    private double argument;
    private double value;
    private long iterationsCount;

    public NonlinearEquationSolutionResult(double argument, double value, long iterationsCount) {
        this.argument = argument;
        this.value = value;
        this.iterationsCount = iterationsCount;
    }

    @Override
    public String toString() {
        return "Answer: " + argument + "\n" +
                "Function values: " + value + "\n" +
                "Count of iterations: " + iterationsCount + "\n";
    }
}
