package com.tutoring.libs.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/21/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class InsertionSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        T saved;

        //Index of the element we're pulling out (as saved)
        int gap;

        //Loop over each index starting at 1
        //(we can assume the first one is already inserted)
        for (int i = 1; i < array.length; i++) {
            //Pull out the element at i.
            saved = array[i];
            //Save the index.
            gap = i;
            //If we're not at the left edge
            //and
            //The element before us is bigger
            while (gap > 0 && array[gap - 1].compareTo(saved) == 1) {
                //Drop the bigger element into our spot
                array[gap] = array[gap-1];
                //And move the gap over
                gap--;
            }
            array[gap] = saved;
        }
    }
}
