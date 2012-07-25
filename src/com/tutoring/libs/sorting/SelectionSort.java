package com.tutoring.libs.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/21/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SelectionSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        int min;
        T temp;
        //We can skip the last element here because it'll be the biggest one.
        for (int j = 0; j < array.length - 1; j++) {
            min = j;
            for (int i = j + 1; i < array.length; i++) {
                if (array[i].compareTo(array[min]) == -1) {
                    min = i;
                }
            }
            //If min is j, we don't need to swap.
            if (min != j) {
                temp = array[min];
                array[min] = array[j];
                array[j] = temp;
            }
        }
    }
}
