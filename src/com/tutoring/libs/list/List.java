package com.tutoring.libs.list;


public interface List<E> {
    //Add methods
    public void add(int index, E element);
    public void addFirst(E element);
    public void addLast(E element);
    //Get methods
    public E get(int index);
    public E getFirst();
    public E getLast();
    //Remove methods
    public E remove(int index);
    public E removeFirst();
    public E removeLast();
    //Set methods
    public E set(int index, E element);
    //Other
    public int size();
    public boolean isEmpty();
    public void clear();
}
