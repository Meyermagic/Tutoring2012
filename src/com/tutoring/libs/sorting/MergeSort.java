package com.tutoring.libs.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/21/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class MergeSort {
    @SuppressWarnings({"unchecked"})
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        realSort(array, 0, array.length, (T[]) new Comparable[array.length]);
    }

    private static <T extends Comparable<? super T>> void realSort(T[] array, int low, int high, T[] scratch) {
        //If we're a length-1 sub-array, we're already sorted.
        if (high - low == 1) {
            return;
        }
        //Sort left half
        realSort(array, low, low + (high - low) / 2, scratch);
        //Sort right half
        realSort(array, low + (high - low) / 2, high, scratch);

        //Merge into the scratch array.
        int i = low;
        int j = low + (high - low) / 2;
        int i_max = j;
        int j_max = high;
        int dest = low;

        //Merge until one side runs off the end
        while (i < i_max && j < j_max) {
            if (array[i].compareTo(array[j]) == -1) {
                scratch[dest] = array[i];
                i++;
            } else {
                scratch[dest] = array[j];
                j++;
            }
            dest++;
        }

        //Then copy the rest (the slow way, I guess. Easier to read.).
        while (i < i_max) {
            scratch[dest] = array[i];
            i++;
            dest++;
        }
        while (j < j_max) {
            scratch[dest] = array[j];
            j++;
            dest++;
        }

        //Copy back to the real array.
        System.arraycopy(scratch, low, array, low, high-low);
    }
}
