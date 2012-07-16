package com.tutoring.libs.queue;


import java.util.NoSuchElementException;

public class LinkedQueue<E> implements Queue<E> {
    private class LinkedQueueNode {
        public E value;
        public LinkedQueueNode link;
        public LinkedQueueNode(E _value, LinkedQueueNode _link) {
            value = _value;
            link = _link;
        }
    }

    private LinkedQueueNode front;
    private LinkedQueueNode back;
    private int size;

    public LinkedQueue() {
        //These are the default values, but we'll set them explicitly for clarity
        front = null;
        back = null;
        size = 0;
    }

    @Override
    public void push(E element) {
        if (size == 0) {
            back = new LinkedQueueNode(element, null);
            front = back;
            size++;
            return;
        }
        back.link = new LinkedQueueNode(element, null);
        back = back.link;
        size++;
    }

    @Override
    public E pop() throws NoSuchElementException {
        //If the queue is empty, throw an error.
        if (size == 0) {
            throw new NoSuchElementException("Can't pop from empty stack.");
        }

        //Store the value from the node at the top of the stack
        E temp = front.value;

        //Replace front with next item in stack, or null.
        front = front.link;

        //Decrement size
        size--;

        if (size == 0) {
            back = null;
        }

        //Return the value from the top of the stack
        return temp;
    }

    @Override
    public E peek() throws NoSuchElementException {
        //If the queue is empty, throw an error.
        if (size == 0) {
            throw new NoSuchElementException("Can't peek at an empty stack.");
        }

        return front.value;
    }

    @Override
    public int size() {
        //Simply return the size (number of elements in queue)
        return size;
    }

    @Override
    public boolean empty() {
        //If size is 0, return true. Else, return false.
        return (size == 0);
    }
}