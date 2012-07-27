package com.tutoring.libs.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/25/12
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class HeapSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        //Put the array into heap order (max first, children less)
        heapify(array);

        //End is the index of the last place in the list we haven't sorted yet
        //(The index after the end of the heap)
        for (int end = array.length - 1; end > 0; end--) {
            //Move the max element to the back of the array
            swap(array, end, 0);

            //Fix the heap
            siftDown(array, 0, end-1);
        }
    }

    private static <T extends Comparable<? super T>> void heapify(T[] array) {
        for (int start = parent(array.length - 1); start >= 0; start--) {
            siftDown(array, start, array.length - 1);
        }
    }

    private static <T extends Comparable<? super T>> void siftDown(T[] array, int start, int end) {
        int root = start;
        //While root has at least one child not past the end of the heap
        while (left_child(root) <= end) {
            //Find biggest child and swap up:

            //Start with left child
            int child = left_child(root);

            //Biggest so far
            int biggest = root;

            //If root is smaller than left child
            if (array[biggest].compareTo(array[child]) == -1) {
                biggest = child;
            }

            //If right child exists and is bigger than biggest so far
            if (child+1 <= end && array[biggest].compareTo(array[child+1]) == -1) {
                biggest = child + 1;
            }

            //Check if either of the children was bigger.
            if (biggest != root) {
                swap(array, root, biggest);
                root = biggest;
            } else {
                return;
            }
        }
    }

    private static <T extends Comparable<? super T>> void swap(T[] array, int a, int b) {
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private static int parent(int index) {
        return (index - 1) / 2;
    }

    private static int left_child(int index) {
        return 2 * index + 1;
    }

    private static int right_child(int index) {
        return 2 * index + 2;
    }
}
