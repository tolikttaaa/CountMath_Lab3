package back;

import back.solution.DerivativeNonlinearEquation;
import back.solution.NonlinearEquation;

import java.util.ArrayList;

public class NonlinearEquations {
    public static final NonlinearEquation EQUATION_1 = new NonlinearEquation() {
        @Override
        public double getValue(ArrayList<Double> arguments) {
            return arguments.get(1) - Math.exp(arguments.get(0));
        }

        @Override
        public ArrayList<NonlinearEquation> getDerivatives() {
            ArrayList<NonlinearEquation> res = new ArrayList<>();
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return -Math.exp(arguments.get(0));
                }
            });
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 1;
                }
            });
            return res;
        }

        @Override
        public ArrayList<Pair<Double, Double>> getPlotData() {
            //TODO
            return null;
        }

        @Override
        public String toString() {
            return "x2 - e^x1 = 0";
        }
    };

    public static final NonlinearEquation EQUATION_2 = new NonlinearEquation() {
        @Override
        public double getValue(ArrayList<Double> arguments) {
            return Math.pow(arguments.get(0), 2) + 16d * Math.pow(arguments.get(1), 2) - 16d;
        }

        @Override
        public ArrayList<NonlinearEquation> getDerivatives() {
            ArrayList<NonlinearEquation> res = new ArrayList<>();
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 2d * arguments.get(0);
                }
            });
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 32d * arguments.get(1);
                }
            });
            return res;
        }

        @Override
        public ArrayList<Pair<Double, Double>> getPlotData() {
            //TODO
            return null;
        }

        @Override
        public String toString() {
            return "x1^2 + 16 * x2^2 - 16 = 0";
        }
    };

    public static final NonlinearEquation EQUATION_3 = new NonlinearEquation() {
        @Override
        public double getValue(ArrayList<Double> arguments) {
            return arguments.get(1) - Math.pow(arguments.get(0), 2);
        }

        @Override
        public ArrayList<NonlinearEquation> getDerivatives() {
            ArrayList<NonlinearEquation> res = new ArrayList<>();
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return -2d * arguments.get(0);
                }
            });
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 1;
                }
            });
            return res;
        }

        @Override
        public ArrayList<Pair<Double, Double>> getPlotData() {
            //TODO
            return null;
        }

        @Override
        public String toString() {
            return "x2 - x1^2 = 0";
        }
    };

    //FIXME: remove

    public static final NonlinearEquation EQUATION_TEST_1 = new NonlinearEquation() {
        @Override
        public double getValue(ArrayList<Double> arguments) {
            return arguments.get(0) +  3d * Math.log10(arguments.get(0)) - Math.pow(arguments.get(1), 2);
        }

        @Override
        public ArrayList<NonlinearEquation> getDerivatives() {
            ArrayList<NonlinearEquation> res = new ArrayList<>();
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 1d + 3 / arguments.get(0) / Math.log(10);
                }
            });
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return -2d * arguments.get(1);
                }
            });
            return res;
        }

        @Override
        public ArrayList<Pair<Double, Double>> getPlotData() {
            //TODO
            return null;
        }

        @Override
        public String toString() {
            return "x1 + 3*lg x1 - x2^2 = 0";
        }
    };

    public static final NonlinearEquation EQUATION_TEST_2 = new NonlinearEquation() {
        @Override
        public double getValue(ArrayList<Double> arguments) {
            return 2d * Math.pow(arguments.get(0), 2) - arguments.get(0) * arguments.get(1) - 5d * arguments.get(0) + 1d;
        }

        @Override
        public ArrayList<NonlinearEquation> getDerivatives() {
            ArrayList<NonlinearEquation> res = new ArrayList<>();
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return 4d * arguments.get(0) - arguments.get(1) - 5d;
                }
            });
            res.add(new DerivativeNonlinearEquation() {
                @Override
                public double getValue(ArrayList<Double> arguments) {
                    return -arguments.get(0);
                }
            });
            return res;
        }

        @Override
        public ArrayList<Pair<Double, Double>> getPlotData() {
            //TODO
            return null;
        }

        @Override
        public String toString() {
            return "2*x1^2 - x1*x2 - 5*x1 + 1 = 0";
        }
    };

}
