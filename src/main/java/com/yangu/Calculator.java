package com.yangu;

import java.text.DecimalFormat;
import java.util.Stack;

public class Calculator {
    private Stack<Double> numberStack = new Stack<>();
    private Stack<Instruction> instructionsStack = new Stack<>();
    private int currentTokenIndex = 0;

    /**
     * Entry point of calculating a RPN expression and pushes the result into the numberStack
     * @param input valid RPN expression
     * @throws CalculatorException
     */
    public void calculate(String input) throws CalculatorException {
        eval(input, false);
    }

    /**
     * Evals a RPN expression and pushes the result into the numberStack
     * @param input           valid RPN expression
     * @param isUndoOperation indicates if the operation is an undo operation.
     *                        undo operations use the same evaluation functions as the standard ones
     *                        but they are not pushed into instructionsStack
     * @throws CalculatorException
     */
    private void eval(String input, boolean isUndoOperation) throws CalculatorException {
        if (input == null) {
            throw new CalculatorException("Input null!");
        }
        currentTokenIndex = 0;
        String[] result = input.split("\\s");
        for (String r : result) {
            currentTokenIndex++;
            processToken(r, isUndoOperation);
        }
    }

    /**
     * Processes each token
     * @param token           RPN token
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processToken(String token, boolean isUndoOperation) throws CalculatorException {
        Double value = processNumber(token);
        if (value == null) {
            // not a number
            processOperator(token, isUndoOperation);
        } else {
            // it's a digit
            numberStack.push(Double.parseDouble(token));
            if (!isUndoOperation) {
                instructionsStack.push(null);
            }
        }
    }

    /**
     * Parse a token to a double type number
     * @param str             RPN token
     * @throws NumberFormatException
     */
    private Double processNumber(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * Parse an Operation and do calculation
     * @param op              RPN valid operator
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processOperator(String op, boolean isUndoOperation) throws CalculatorException {

        // searching for the operator
        Operator operator = Operator.getOperator(op);
        if (operator == null) {
            throw new CalculatorException("Invalid operator!");
        }

        // clear value stack and instructions stack
        if (operator == Operator.CLEAR) {
            numberStack.clear();
            instructionsStack.clear();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Operator.UNDO) {
            if (instructionsStack.isEmpty()) {
                System.out.println("Currently no operations to undo");
            } else {
                Instruction lastInstruction = instructionsStack.pop();
                if (lastInstruction == null) {
                    numberStack.pop();
                } else {
                    eval(lastInstruction.getReverseInstruction(), true);
                }
            }
            return;
        }

        // Checking that there are enough operand for the operation
        if (operator.getOperandsNumber() > numberStack.size()) {
            throw new CalculatorException(
                    String.format("operator %s (position: %d): insucient parameters", op, currentTokenIndex * 2 - 1));
        }

        // getting operands
        Double firstOperand = numberStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? numberStack.pop() : null;

        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);

        if (result != null) {
            numberStack.push(result);
            if (!isUndoOperation) {
                instructionsStack.push(new Instruction(Operator.getOperator(op), firstOperand));
            }
        }
    }

    /**
     * Returns the values numberStack
     */
    public Stack<Double> getNumberStack() {
        return numberStack;
    }

    /**
     * Helper method to return a specific item in the numberStack
     * @param index index of the element to return
     */
    public Double getNumberStackItem(int index) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##########");
        return Double.valueOf(decimalFormat.format(numberStack.get(index)));
    }

    /**
     * Returns the values instructionsStack
     */
    public Stack<Instruction> getInstructionStack() {
        return instructionsStack;
    }

    /**
     * Helper method to return a specific item in the instructionsStack
     * @param index index of the element to return
     */
    public Instruction getInstructionStackItem(int index) {
        return instructionsStack.get(index);
    }
}
