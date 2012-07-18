package com.tutoring.libs.stack;

import java.util.NoSuchElementException;

public class ArrayStack<E> implements Stack<E> {
    private int top;
    private E[] array;

    @SuppressWarnings({"unchecked"})
    public ArrayStack() {
        top = 0;
        array = (E[]) new Object[1];
    }

    @SuppressWarnings({"unchecked"})
    public ArrayStack(int capacity) {
        top = 0;
        array = (E[]) new Object[capacity];
    }

    @Override
    public void push(E element) {
        //If we're full when we want to push
        if (top == array.length) {
            @SuppressWarnings({"unchecked"})
            //Make a new array twice as big.
            E[] newArray = (E[]) new Object[2 * array.length];

            //Copy the old values over to it
            System.arraycopy(array, 0, newArray, 0, array.length);

            //And replace the reference to our old array with the new one.
            array = newArray;
        }

        array[top] = element;
        top++;
    }

    @Override
    public E pop() throws NoSuchElementException {
        if (top == 0) {
            throw new NoSuchElementException("Can't pop from empty stack.");
        }

        top--;
        return array[top];
    }

    @Override
    public E peek() throws NoSuchElementException {
        if (top == 0) {
            throw new NoSuchElementException("Can't peek at empty stack.");
        }

        return array[top - 1];
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public boolean empty() {
        return (top == 0);
    }
}
