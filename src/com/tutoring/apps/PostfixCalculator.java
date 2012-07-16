package com.tutoring.apps;

import com.tutoring.libs.stack.LinkedStack;
import com.tutoring.libs.stack.Stack;

import java.util.Scanner;

public class PostfixCalculator {
    public static void computeOperator(Stack<Float> stack, String operator) {
        //Be careful with the order you pop things off here.
        //Choose an order and stick with it.
        float b = stack.pop();
        float a = stack.pop();
        float result;

        //If we had more operators, we wouldn't use cascading if/else statements
        if (operator.equals("+")) {
            result = a + b;
        } else if (operator.equals("-")) {
            result = a - b;
        } else if (operator.equals("*")) {
            result = a * b;
        } else if (operator.equals("/")) {
            result = a / b;
        } else if (operator.equals("^")) {
            result = (float) Math.pow((double) a, (double) b);
        } else {
            //We've encountered an unknown operator.
            //We'll ignore it and move on, pushing the operands back onto the stack.
            //Be careful you push the numbers back in the right order.
            stack.push(a);
            stack.push(b);

            //Return here so we don't push a zero onto the stack.
            return;
        }
        stack.push(result);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Postfix Calculator!\n");

        //Initialize a scanner on the standard input (System.in)
        Scanner inputScanner = new Scanner(System.in);
        Stack<Float> calcStack = new LinkedStack<Float>();

        Scanner lineScanner;
        String line;
        while (true) {
            System.out.print("Enter an expression: ");

            //Get whatever the user entered as a string
            line = inputScanner.nextLine();
            if(line.isEmpty()) {
                //If the user enters an empty expression, exit and print out the rest of the stack.
                break;
            }
            //Otherwise, initialize a scanner on the expression string.
            lineScanner = new Scanner(line);

            //Iterate over all the tokens in the expression
            while (lineScanner.hasNext()) {
                if (lineScanner.hasNextFloat()) {
                    //If we find a number, push it onto the stack
                    calcStack.push(lineScanner.nextFloat());
                } else {
                    //If we find something else (an operator), call computeOperator.
                    computeOperator(calcStack, lineScanner.next());
                }
            }

            lineScanner.close();

            //Once we're done with the expression, print out whatever is on top
            //(but don't remove it, so the user can do multi-part computations)
            System.out.printf("Result: %f\n", calcStack.peek());

            //Tell the user how much is left on the stack. In case they forgot, I guess.
            if (calcStack.size() == 1) {
                System.out.print("The previous result is the only number on the stack.\n\n");
            } else {
                System.out.printf("There are %d numbers on the stack, including the previous result.\n\n", calcStack.size());
            }
        }

        //If the stack isn't empty, pop things off and print them out until it is.
        if (!calcStack.empty()) {
            System.out.println("Dumping the rest of the stack...");

            while (!calcStack.empty()) {
                System.out.println(calcStack.pop());
            }
        }
    }
}
