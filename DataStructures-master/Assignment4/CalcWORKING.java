/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 4
*/
import java.util.Scanner;

/**
Client implementation of an RPN calculator using the Stack<T> interface.
*/
public final class Calc {

    //The stack used to store integers
    //Could be switched to an ArrayStack and would work the same
    private static Stack<Integer> numbers = new ListStack<Integer>();

    private Calc() {}

    /**
    Lets the driver know if the user input is an operand.
    @param String input, user input
    @return boolean True if input is an operand, false if not
    */
    private static boolean isOperand(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
    Lets the driver know if the user input is an operator +,-,/,*.
    @param String input, user input
    @return boolean True if input is an operator, false if not
    */
    private static boolean isOperator(String input) {

        switch (input) {
            case "+": case "-": case "*": case "/":
                return true;
            default:
                return false;
        }

    }

    /**
    Performs an operation by popping two integers and performing the
    user specified operation.
    @param String input, the user input
    @throws EmptyStackException if trying to use top() on an empty stack
    */
    private static void performOperation(String input)
        throws EmptyStackException {

        int num1;
        int num2;

        try {
            num1 = numbers.top();
            numbers.pop();
        } catch (EmptyStackException e) {
            System.err.println("?You do not have enough integers in the Stack");
            return;
        }

        try {
            num2 = numbers.top();
            numbers.pop();
        } catch (EmptyStackException e) {
            System.err.println("?You do not have enough integers in the Stack");
            numbers.push(num1);
            return;
        }

        switch (input) {
            case "+":
                numbers.push(num2 + num1);
                break;
            case "-":
                numbers.push(num2 - num1);
                break;
            case "*":
                numbers.push(num2 * num1);
                break;
            case "/":
                if (num1 == 0) {
                    System.err.println("?Cannot divide by 0");
                } else {
                    numbers.push(num2 / num1);
                }
                break;
            default:
                break;
        }
        return;
    }

    /**
    main allows the user to calculate operations via an RPN
    calculator.
    @param args command line arguments
    @throws EmptyStackException when performing top() on an
    empty stack
    */
    public static void main(String[] args) throws EmptyStackException {

        Scanner in = new Scanner(System.in);
        String input;

        do {

            System.out.print(">");
            input = in.next();

            if (isOperand(input)) {
                numbers.push(Integer.parseInt(input));
            } else if (isOperator(input)) {
                performOperation(input);
            } else if (input.equals("?")) {
                System.out.println(numbers);
            } else if (input.equals(".")) {
                try {
                    System.out.println(numbers.top());
                    numbers.pop();
                } catch (EmptyStackException e) {
                    System.err.println(
                        "?You do not have integers in the Stack");
                }
            } else if (input.equals("!")) {
                break;
            } else {
                System.err.println("?Your input is invalid. Please try again.");
            }

        } while (!input.equals("!"));
    }
}