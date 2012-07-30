package com.tutoring.apps;

import com.tutoring.libs.sorting.QuickSort;
import com.tutoring.libs.treemap.binarytree.BinaryTree;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/30/12
 * Time: 4:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTest {
    public static void main(String[] args) {
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        int count = 1000;
        Integer[] basic_array = new Integer[count];
        Random prng = new Random(42);
        for (int i = 0; i < count; i++) {
            basic_array[i] = prng.nextInt(count);
            tree.insert(basic_array[i], String.valueOf(basic_array[i]));
        }
        for (int i = 0; i < count*4; i++) {
            tree.remove(prng.nextInt(count));
        }
        tree.print();
    }
}
