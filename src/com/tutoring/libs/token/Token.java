package com.tutoring.libs.token;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/16/12
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Token {
    public abstract boolean isNumber();
    public abstract boolean isOperator();
    public abstract String toString();
}
