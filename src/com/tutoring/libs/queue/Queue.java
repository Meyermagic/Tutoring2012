package com.tutoring.libs.queue;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/13/12
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Queue<E> {
    public void push(E element);

    public E pop();

    public E peek();

    public int size();

    public boolean empty();
}
