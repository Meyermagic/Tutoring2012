package com.tutoring.libs.stack;

public interface Stack<E> {
    public void push(E element);

    public E pop();

    public E peek();

    public int size();

    public boolean empty();
}
