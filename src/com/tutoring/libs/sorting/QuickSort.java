package com.tutoring.libs.sorting;


public class QuickSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        quicksort(array, 0, array.length-1);
    }

    private static <T extends Comparable<? super T>> void quicksort(T[] array, int low, int high) {
        //If we've finished bottomed out (or gone a bit off the edge), return
        if (low >= high) {
            return;
        }
        //Pivot on the middle of the array
        int pivot = low + ((high + 1) - low) / 2;
        //Parition the array and choose new pivot
        pivot = partition(array, low, high, pivot);
        //Recurse on the two sides of the pivot.
        quicksort(array, low, pivot-1);
        quicksort(array, pivot+1, high);
    }

    private static <T extends Comparable<? super T>> int partition(T[] array, int low, int high, int pivot) {
        //Store the pivot value for comparison
        T pivotValue = array[pivot];
        //Move the pivot to the right for storage.
        swap(array, pivot, high);
        //Set the new pivot (temporarily) to low
        pivot = low;
        //Loop over the sub-array
        for (int i = low; i < high; i++) {
            //If the value at current index is less than that at the new pivot
            if (array[i].compareTo(pivotValue) == -1) {
                //Then move it to the left side of the pivot
                //(By swapping it with the pivot)
                swap(array, i, pivot);
                //(Then incrementing the pivot)
                pivot++;
            }
        }
        //Put the original pivot value back where it should be.
        swap(array, pivot, high);
        //And return the new pivot position
        return pivot;
    }

    private static <T extends Comparable<? super T>> void swap(T[] array, int a, int b) {
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
