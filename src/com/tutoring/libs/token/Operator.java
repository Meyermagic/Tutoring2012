package com.tutoring.libs.token;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/16/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Operator extends Token {
    //+, -, /, *, ^, (, ), S, E
    private char operatorSymbol;
    private boolean leftAssociative;
    private int precedence;

    public Operator(String symbol) {
        operatorSymbol = symbol.charAt(0);
        precedence = getPrecedence();
        leftAssociative = getAssociativity();
    }

    private int getPrecedence() {
        int precedence = 0;
        //All other symbols are precedence 0
        switch (operatorSymbol) {
            case '+':
            case '-':
                precedence = 1; break;
            case '*':
            case '/':
                precedence = 2; break;
            case '^':
                precedence = 3; break;
        }
        return precedence;
    }

    private boolean getAssociativity() {
        boolean leftAssociative = false;
        //All others are right associative
        switch (operatorSymbol) {
            case '+':
            case '-':
            case '*':
            case '/':
                leftAssociative = true; break;
        }
        return leftAssociative;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(operatorSymbol);
    }

    public char getSymbol() {
        return operatorSymbol;
    }

    public boolean preferToRight(Operator right) {
        //If we're higher precedence, prefer us
        if (this.precedence > right.precedence) {
            return true;
        }
        //If right is higher precedence, prefer it
        if (right.precedence > this.precedence) {
            return false;
        }
        //If we're left associative, prefer us (because we're on the left)
        if (this.leftAssociative) {
            return true;
        }
        //Otherwise, prefer right.
        return false;
    }
}
