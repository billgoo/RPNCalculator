package com.yangu;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        Calculator calculator = new Calculator();

        System.out.println("Enter RPN expression, or 'exit' to exit the program.");

        while (true) {
            String inputString = console.readLine(">  ");
            if ("exit".equals(inputString)) {
                // if we get an exit symbol then we exit the input loop
                return;
            } else {
                try {
                    calculator.calculate(inputString);
                } catch (CalculatorException ce) {
                    System.out.println(ce.getMessage());
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##########");
                Stack<Double> stack = calculator.getNumberStack();

                System.out.print("stack:  ");
                for (Double number : stack) {
                    System.out.print(decimalFormat.format(number));
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}
