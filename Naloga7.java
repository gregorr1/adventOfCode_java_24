import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Naloga7 {
    public static List<String> getInput() {
        String path = "input/input7.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga7_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/7 */

        List<String> input = getInput();
        long sum = 0;

        for (String line : input) {
            String[] substrings = line.split("\\s");
            List<Long> numbers = new ArrayList<>();
            boolean correct = false;
            long result = 0;

            // Assign the number containing ':' to the result and add the rest of numbers to a list
            for (String substring : substrings) {
                if (substring.contains(":")) {
                    try {
                        result = Long.parseLong(substring.replace(":", ""));    
                    } catch (Exception e) {
                        System.out.println("Cannot parse result: " + substring);
                    }
                } else {
                    try {
                        numbers.add(Long.parseLong(substring));
                    } catch (Exception e) {
                        System.out.println("Cannot add the number to stack: " + substring);
                    }
                }
            }
            
            int operatorsSize = numbers.size() - 1;
            int totalCombinations = (int)Math.pow(2, operatorsSize);
            for (int i = 0; i < totalCombinations; i++) {
                char[] operators = new char[operatorsSize];
                for (int j = 0; j < operatorsSize; j++) {
                    if ((i & (1 << j)) != 0) {
                        operators[j] = '*';
                    } else {
                        operators[j] = '+';
                    }
                }
                if (evaluateWithoutPrecedence(numbers, operators) == result) {
                    correct = true;
                }
            }

            if (correct) {
                sum += result;
            }
        }

        System.out.println("The result is " + sum);        
    }

    public static void naloga7_2() {
        List<String> input = getInput();
        long sum = 0;

        for (String line : input) {
            String[] substrings = line.split("\\s");
            List<Long> numbers = new ArrayList<>();
            boolean correct = false;
            long result = 0;
            for (String substring : substrings) {
                if (substring.contains(":")) {
                    try {
                        result = Long.parseLong(substring.replace(":", ""));    
                    } catch (Exception e) {
                        System.out.println("Cannot parse result: " + substring);
                    }
                } else {
                    try {
                        numbers.add(Long.parseLong(substring));
                    } catch (Exception e) {
                        System.out.println("Cannot add number to stack: " + substring);
                    }
                }
            }
            
            int operatorsSize = numbers.size() - 1;
            int totalCombinations = (int)Math.pow(3, operatorsSize);
            char[] possibleOperators = {'+', '*', '|'};
            for (int i = 0; i < totalCombinations; i++) {
                char[] operators = new char[operatorsSize];
                int currentCombination = i;
                for (int j = 0; j < operatorsSize; j++) {
                    operators[j] = possibleOperators[currentCombination % 3];
                    currentCombination /= 3;
                }
                if (evaluateWithoutPrecedence(numbers, operators) == result) {
                    correct = true;
                }
            }

            if (correct) {
                sum += result;
            }
        }

        System.out.println("The result is " + sum);
    }

    public static long evaluateWithoutPrecedence (List<Long> numbers, char[] operators) {
        long currentNumber = 0;
        try {
            currentNumber = numbers.get(0);    
        } catch (Exception e) {
            System.out.println("The list of numbers is empty!");
            return currentNumber;
        }

        for (int i = 0; i < operators.length; i++) {
            currentNumber = applyOperator(operators[i], currentNumber, numbers.get(i + 1));
        }

        return currentNumber;
    }

    // This is a function that does not need to be used for solving tasks, but uses correct order of operations for given equations
    public static long evaluateExpression (Stack<Long> numbers, char[] operators) {
        // Stacks to store operands and operators
        @SuppressWarnings("unchecked")
        Stack<Long> stackNumbers = (Stack<Long>)numbers.clone();
        Stack<Character> stackOperators = new Stack<>();
        for (Character operator : operators) {
            stackOperators.push(operator);
        }

        char lastOperator;
        long lastNumber = 0;
        
        try {
            lastNumber = stackNumbers.pop();
            lastOperator = stackOperators.pop();
        } catch (Exception e) {
            System.out.println("No numbers or operators to calculate with.");
            return lastNumber;
        }
        
        // Process any remaining operators in the stack
        while (!stackOperators.isEmpty()) {
            if (hasPrecedence(lastOperator, stackOperators.peek())) {
                stackNumbers.push(applyOperator(lastOperator, lastNumber, stackNumbers.pop()));
                lastNumber = stackNumbers.pop();
                lastOperator = stackOperators.pop();
            } else {
                stackNumbers.push(applyOperator(stackOperators.pop(), stackNumbers.pop(), stackNumbers.pop()));
            }
        }
        stackNumbers.push(applyOperator(lastOperator, lastNumber, stackNumbers.pop()));
        
        // The result is the only remaining element in the values stack
        return stackNumbers.pop();
    }

    // Function to check if operator1 has higher precedence than operator2
    private static boolean hasPrecedence (char operator1, char operator2) {
        return (operator1 == '*' || operator2 == '+');
    }

    // Function to apply the operator to two operands
    private static long applyOperator(char operator, long a, long b) {
        switch (operator) {
            case '+':
                return a + b;
            case '*':
                return a * b;
            case '|':
                String concat = String.valueOf(a) + String.valueOf(b);
                return Long.parseLong(concat);
        }
        return 0;
    }
}
