package com.tutoring.apps;

import com.tutoring.libs.sorting.InsertionSort;
import com.tutoring.libs.sorting.MergeSort;
import com.tutoring.libs.sorting.SelectionSort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/25/12
 * Time: 3:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class SortingContest {
    public static void main(String[] args) {
        Random prng = new Random();
        Integer[] test_array1 = new Integer[30];
        Integer[] test_array2 = new Integer[30];
        Integer[] test_array3 = new Integer[30];
        for (int i = 0; i < 30; i++) {
            int temp = prng.nextInt(50);
            test_array1[i] = temp;
            test_array2[i] = temp;
            test_array3[i] = temp;
        }
        SelectionSort.sort(test_array1);
        MergeSort.sort(test_array2);
        Arrays.sort(test_array3);
        for (int i = 0; i < 30; i++) {
            System.out.println(test_array2[i]);
            if (! (test_array1[i].equals(test_array2[i]) && test_array2[i].equals(test_array3[i]))) {
                System.out.println("Whoops!");
            }
        }
    }
}
