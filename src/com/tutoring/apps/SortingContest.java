package com.tutoring.apps;

import com.tutoring.libs.sorting.*;

import java.util.Arrays;
import java.util.Random;

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
        QuickSort.sort(test_array1);
        HeapSort.sort(test_array2);
        Arrays.sort(test_array3);
        for (int i = 0; i < 30; i++) {
            System.out.println(test_array2[i]);
            if (! (test_array1[i].equals(test_array2[i]) && test_array2[i].equals(test_array3[i]))) {
                System.out.println("Whoops!");
            }
        }
    }
}
