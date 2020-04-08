package back.solution;

import back.Pair;

import java.util.ArrayList;

public class SystemOfNonlinearEquationsSolutionResult {
    private ArrayList<Double> arguments;
    private long iterationsCount;

    public SystemOfNonlinearEquationsSolutionResult(ArrayList<Double> arguments, long iterationsCount) {
        this.arguments = arguments;
        this.iterationsCount = iterationsCount;
    }

    public Pair<Double, Double> get2Result() {
        return new Pair<>(arguments.get(0), arguments.get(1));
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Answer: ( ").append(arguments.get(0));

        for (int i = 1; i < arguments.size(); i++) {
            res.append(", ").append(arguments.get(i));
        }

        res.append(" )\nCount of iterations: ").append(iterationsCount).append("\n");
        return res.toString();
    }
}
