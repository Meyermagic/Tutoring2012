package com.tutoring.apps;

import com.tutoring.libs.queue.LinkedQueue;
import com.tutoring.libs.queue.Queue;
import com.tutoring.libs.stack.LinkedStack;
import com.tutoring.libs.stack.Stack;
import com.tutoring.libs.token.Operator;
import com.tutoring.libs.token.TNumber;
import com.tutoring.libs.token.Token;

import java.util.Scanner;

import static com.tutoring.apps.PostfixCalculator.computeOperator;

public class InfixCalculator {
    public static void main(String[] args) {
        System.out.println("Welcome to the Infix Calculator!\n");

        //Initialize a scanner on the standard input (System.in)
        Scanner inputScanner = new Scanner(System.in);

        String line;
        while (true) {
            System.out.print("Enter an expression: ");

            //Get whatever the user entered as a string
            line = inputScanner.nextLine();
            if(line.isEmpty()) {
                //If the user enters an empty expression, exit.
                break;
            }

            //Otherwise tokenize the input expression
            Queue<Token> infixExpression = tokenize(line);

            //Convert the expression to postfix.
            Queue<Token> postfixExpression = infixToPostfix(infixExpression);

            //Compute the result.
            float result = postfixCompute(postfixExpression);

            System.out.printf("Result: %f\n\n", result);
        }
    }

    public static Queue<Token> tokenize(String expression) {
        Scanner scanner = new Scanner(expression);
        Queue<Token> infixExpression = new LinkedQueue<Token>();
        //10 + ( 3 + 2 * 4 ) * 3
        while (scanner.hasNext()) {
            if (scanner.hasNextFloat()) {
                //If we find a number, convert it to a TNumber token.
                infixExpression.push(new TNumber(scanner.nextFloat()));
            } else {
                //If we find something else (an operator), convert it to an Operator token.
                infixExpression.push(new Operator(scanner.next()));
            }
        }

        //End expression token.
        infixExpression.push(new Operator("E"));

        return infixExpression;
    }

    public static Queue<Token> infixToPostfix(Queue<Token> infix) {
        Queue<Token> postfix = new LinkedQueue<Token>();
        Stack<Operator> operatorStack = new LinkedStack<Operator>();

        //Begin expression token
        operatorStack.push(new Operator("S"));

        while (!infix.empty()) {
            Token current = infix.pop();

            if (current.isNumber()) {
                //If we find a number, add it directly to the output
                postfix.push(current);
            } else {
                //Current is an operator
                Operator currentOperator = (Operator) current;

                if (currentOperator.getSymbol() == '(') {
                    //Put open paren on then stack
                    operatorStack.push(currentOperator);
                } else if (currentOperator.getSymbol() == ')') {
                    //If we find a close paren in the expression, unwind the stack until we hit the matching open.
                    while (operatorStack.peek().getSymbol() != '(') {
                        postfix.push(operatorStack.pop());
                    }
                    //Pop off the remaining open paren.
                    operatorStack.pop();
                } else {
                    //Current is an operator besides a paren.
                    //
                    while (operatorStack.peek().preferToRight(currentOperator)) {
                        postfix.push(operatorStack.pop());
                    }
                    operatorStack.push(currentOperator);
                }
            }
        }
        return postfix;
    }

    public static float postfixCompute(Queue<Token> postfix) {
        Stack<Float> computeStack = new LinkedStack<Float>();

        while (!postfix.empty()) {
            Token current = postfix.pop();

            if (current.isNumber()) {
                computeStack.push(((TNumber) current).getValue());
            } else {
                computeOperator(computeStack, ((Operator) current).toString());
            }
        }

        return computeStack.pop();
    }
}
