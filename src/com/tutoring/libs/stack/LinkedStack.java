package com.tutoring.libs.stack;

import java.util.NoSuchElementException;

public class LinkedStack<E> implements Stack<E> {
    private class LinkedStackNode {
        public E value;
        public LinkedStackNode link;
        public LinkedStackNode(E _value, LinkedStackNode _link) {
            value = _value;
            link = _link;
        }
    }

    private LinkedStackNode head;
    private int size;

    public LinkedStack() {
        //These are the default values, but we'll set them explicitly for clarity
        head = null;
        size = 0;
    }

    @Override
    public void push(E element) {
        //Attach new head
        head = new LinkedStackNode(element, head);

        //Increment size
        size++;
    }

    @Override
    public E pop() throws NoSuchElementException {
        //If the stack is empty, throw an error.
        if (size == 0) {
            throw new NoSuchElementException("Can't pop from empty stack.");
        }

        //Store the value from the node at the top of the stack
        E temp = head.value;

        //Replace head with next item in stack, or null.
        head = head.link;

        //Decrement size
        size--;

        //Return the value from the top of the stack
        return temp;
    }

    @Override
    public E peek() throws NoSuchElementException {
        //If the stack is empty, throw an error.
        if (size == 0) {
            throw new NoSuchElementException("Can't peek at an empty stack.");
        }

        return head.value;
    }

    @Override
    public int size() {
        //Simply return the size (number of elements in stack)
        return size;
    }

    @Override
    public boolean empty() {
        //If size is 0, return true. Else, return false.
        return (size == 0);
    }
}
