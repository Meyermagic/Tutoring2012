package com.tutoring.apps;

import com.tutoring.libs.list.LinkedList;
import com.tutoring.libs.list.List;


public class ListTest {
    public static void main(String[] args) {
        List<Integer> testList = new LinkedList<Integer>();
        for(int i = 0; i < 30; i++) {
            testList.addLast(i+5);
        }
        testList.set(2, 100);
        testList.set(10, 1000);
        testList.set(5, 50);
        testList.remove(5);
        for (int i = 0; !testList.isEmpty(); i++) {
            System.out.printf("%d: %d\n", i, testList.removeFirst());
        }
    }
}
