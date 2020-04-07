package back.solution.gauss;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LinearEquation implements Gauss<Double, LinearEquation> {
    private List<Double> equation = new ArrayList<>();

    public LinearEquation(ArrayList<Double> multipliers, Double b) {
        this.equation = new ArrayList<>(multipliers);
        this.equation.add(b);
    }

    public List<Double> getEquation() {
        return equation;
    }

    @Override
    public int size() {
        return equation.size();
    }

    @Override
    public void addEquation(LinearEquation item) {
        ListIterator<Double> i = equation.listIterator();
        ListIterator<Double> j = item.getEquation().listIterator();

        for(; i.hasNext() && j.hasNext();) {
            Double a = i.next();
            Double b = j.next();
            i.set(a + b);
        }
    }

    @Override
    public void mul(Double coefficient) {
        for(ListIterator<Double> i = equation.listIterator(); i.hasNext();) {
            Double next = i.next();
            i.set(next * coefficient);
        }
    }

    @Override
    public Double findCoefficient(Double a, Double b) {
        if (a == 0.0d) {
            return 1.0d;
        }
        return -b / a;
    }

    @Override
    public Double at(int index) {
        return equation.get(index);
    }
}