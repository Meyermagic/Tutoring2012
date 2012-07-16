package com.tutoring.libs.token;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/16/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class TNumber extends Token {
    private float value;

    public TNumber(float value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public float getValue() {
        return value;
    }
}
