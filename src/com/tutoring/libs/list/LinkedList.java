package com.tutoring.libs.list;


import java.util.NoSuchElementException;


public class LinkedList<E> implements List<E> {
    class LinkedListNode {
        public LinkedListNode toFront, toBack;
        public E value;

        public LinkedListNode() {
            toFront = null;
            toBack = null;
            value = null;
        }
        public LinkedListNode(LinkedListNode toBack, E value, LinkedListNode toFront) {
            this.toFront = toFront;
            this.toBack = toBack;
            this.value = value;
        }
    }

    private LinkedListNode front, back, current;
    private int current_index;
    private int length;

    public LinkedList() {
        front = null;
        back = null;
        current = null;
        current_index = -1;
        length = 0;
    }

    private void seekTo(int index) {
        //This can be optimized to seek front the front of back in some cases.
        int seek_distance = index - current_index;
        //Seek "forward" (to front, or 0 index)
        while (seek_distance < 0) {
            current = current.toFront;
            current_index--;
            seek_distance++;
        }
        //Seek "backward" (to back, or length-1 index)
        while (seek_distance > 0) {
            current = current.toBack;
            current_index++;
            seek_distance--;
        }
    }

    private void addOnly(E element) {
        front = new LinkedListNode(null, element, null);
        back = front;
        current = front;
        current_index = 0;
        length = 1;
    }

    private E removeOnly() {
        E temp = front.value;
        front = null;
        back = null;
        current = null;
        current_index = -1;
        length = 0;
        return temp;
    }

    public void add(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }

        if (length == 0) {
            addOnly(element);
            return;
        }
        seekTo(index);
        LinkedListNode newNode = new LinkedListNode(current, element, current.toFront);
        current.toFront = newNode;
        current = newNode;
        length++;
    }

    public void addFirst(E element) {
        if (length == 0) {
            addOnly(element);
            return;
        }
        LinkedListNode newFront = new LinkedListNode(front, element, null);
        front.toFront = newFront;
        front = newFront;
        length++;
        current_index++;
    }

    public void addLast(E element) {
        if (length == 0) {
            addOnly(element);
            return;
        }
        LinkedListNode newBack = new LinkedListNode(null, element, back);
        back.toBack = newBack;
        back = newBack;
        length++;
    }

    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        seekTo(index);
        return current.value;
    }

    public E getFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return front.value;
    }

    public E getLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return back.value;
    }

    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        if (length == 1) {
            return removeOnly();
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == length-1) {
            return removeLast();
        }
        seekTo(index);
        current.toFront.toBack = current.toBack;
        current.toBack.toFront = current.toFront;
        E temp = current.value;
        current = current.toFront;
        current_index--;
        length--;
        return temp;
    }

    public E removeFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        if (length == 1) {
            return removeOnly();
        }
        front.toBack.toFront = null;
        E temp = front.value;
        front = front.toBack;
        length--;
        current = front;
        current_index = 0;
        return temp;
    }

    public E removeLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        if (length == 1) {
            return removeOnly();
        }
        back.toFront.toBack = null;
        E temp = back.value;
        back = back.toFront;
        length--;
        current = back;
        current_index = length - 1;
        return temp;
    }

    public E set(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        seekTo(index);
        E temp = current.value;
        current.value = element;
        return temp;
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() {
        front = null;
        back = null;
        current = null;
        current_index = -1;
        length = 0;
    }
}
