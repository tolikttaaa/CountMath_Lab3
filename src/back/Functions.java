package back;

public enum Functions implements Function {
    FUNCTION_1 {
        @Override
        public double get(double argument) {
            return Math.pow(argument, 3) - argument + 4;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public Function getDerivative() {
            return new DerivativeFunc() {
                @Override
                public double get(double argument) {
                    return 3 * Math.pow(argument, 2) - 1;
                }

                @Override
                public Interval[] getNotAllowedScope() {
                    return new Interval[0];
                }
            };
        }

        @Override
        public String toString() {
            return "x^3 - x + 4 = 0";
        }
    },
    FUNCTION_2 {
        @Override
        public double get(double argument) {
            return Math.pow(argument, 2) - 4;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public Function getDerivative() {
            return new DerivativeFunc() {
                @Override
                public double get(double argument) {
                    return 2 * argument;
                }

                @Override
                public Interval[] getNotAllowedScope() {
                    return new Interval[0];
                }
            };
        }

        @Override
        public String toString() {
            return "x^2 - 4 = 0";
        }
    },
    FUNCTION_3 {
        @Override
        public double get(double argument) {
            return 0.5d * argument - 3;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public Function getDerivative() {
            return (ConstantFunc) argument -> 0.5d;
        }

        @Override
        public String toString() {
            return "x/2 - 3 = 0";
        }
    }
}
