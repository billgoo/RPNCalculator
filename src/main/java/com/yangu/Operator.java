package com.yangu;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    // build reference
    ADDITION("+", "-", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand + firstOperand;
        }
    },

    SUBTRACTION("-", "+", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand - firstOperand;
        }
    },

    MULTIPLICATION("*", "/", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand * firstOperand;
        }
    },

    DIVISION("/", "*", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            if (firstOperand == 0)
                throw new CalculatorException("Divisor cannot be 0.");
            return secondOperand / firstOperand;
        }
    },

    SQRT("sqrt", "square", 1) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return Math.sqrt(firstOperand);
        }
    },

    UNDO("undo", null, 0) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", null, 0) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    // added for reverse sqrt op
    SQUARE("square", "sqrt", 1) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return Math.pow(firstOperand, 2.0);
        }
    };

    // using map for a constant lookup cost
    private static final Map<String, Operator> lookup = new HashMap<>();

    static {
        for (Operator o : values()) {
            lookup.put(o.getSymbol(), o);
        }
    }

    // init for class method
    private String symbol;
    private String reverseOp;
    private int operandsNumber;

    Operator(String symbol, String reverseOp, int operandsNumber) {
        this.symbol = symbol;
        this.reverseOp = reverseOp;
        this.operandsNumber = operandsNumber;
    }

    public static Operator getOperator(String value) {
        // get available operator from operators list
        return lookup.get(value);
    }

    public abstract Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException;

    public String getSymbol() {
        return symbol;
    }

    public String getReverseOp() {
        return reverseOp;
    }

    public int getOperandsNumber() {
        return operandsNumber;
    }

    @Override
    public String toString() {
        return symbol;
    }
}