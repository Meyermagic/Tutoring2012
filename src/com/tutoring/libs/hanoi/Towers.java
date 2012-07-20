package com.tutoring.libs.hanoi;

import com.tutoring.libs.stack.ArrayStack;
import com.tutoring.libs.stack.Stack;


public class Towers {
    //Towers A, B, and C
    private Stack<Integer> A, B, C;
    private int discCount;

    public Towers(int discs) {
        //Store the number of discs
        discCount = discs;

        //Initialize the towers as stacks of integers
        A = new ArrayStack<Integer>(discs);
        B = new ArrayStack<Integer>(discs);
        C = new ArrayStack<Integer>(discs);

        //Push all the discs onto the first tower
        for (int i = discs; i > 0; i--) {
            A.push(i);
        }
    }

    public boolean moveDisc(char source, char destination) {
        if (source == destination) {
            //If we're trying to move to and from the same tower, return failure without doing anything
            return false;
        }

        Stack<Integer> from;
        Stack<Integer> to;
        switch(source) {
            case 'A':
                from = A; break;
            case 'B':
                from = B; break;
            case 'C':
                from = C; break;
            default:
                //If we got a bad tower letter, return failure
                return false;
        }
        switch(destination) {
            case 'A':
                to = A; break;
            case 'B':
                to = B; break;
            case 'C':
                to = C; break;
            default:
                //If we got a bad tower letter, return failure
                return false;
        }

        if (from.empty()) {
            //If we are trying to move from an empty tower, return failure
            return false;
        }

        //This short circuits, so we shouldn't ever try to peek at an empty destination stack.
        if (to.empty() || from.peek() < to.peek()) {
            to.push(from.pop());
            return true;
        }

        //If the previous test failed (we are trying to move a large disc onto a small one), return failure
        return false;
    }

    private static String repeat(String str, int n) {
        //Make a string of n nulls, then replace all nulls with the string to repeat
        //Clever solution from http://stackoverflow.com/a/4903603/1416657
        return new String(new char[n]).replace("\0", str);
    }

    private String[] towerString(Stack<Integer> tower) {
        //Width is discCount * 2 + 5
        String[] output = new String[discCount+2];

        //Bottom row of tower
        output[discCount+1] = " " + repeat("_", discCount * 2 + 3) + " ";

        //Put all the discs onto another stack for a bit.
        int numOnTower = tower.size();
        Stack<Integer> tempStack = new ArrayStack<Integer>(numOnTower);
        while (!tower.empty()) {
            tempStack.push(tower.pop());
        }

        int disc;
        //All the rows filled with discs
        for (int i = 0; i < numOnTower; i++) {
            disc = tempStack.peek();

            output[discCount - i] = " " +
                    repeat(" ", discCount + 1 - disc) + repeat("=", disc)
                    + "|" +
                   repeat("=", disc) + repeat(" ", discCount + 1 - disc)
                    + " ";

            tower.push(tempStack.pop());
        }

        //All the empty rows (including the top).
        for (int i = 0; i <= discCount - numOnTower; i++) {
            output[i] = " " + repeat(" ", discCount + 1) + "|" + repeat(" ", discCount + 1) + " ";
        }

        return output;
    }

    public void printTowers() {
        String[] towerA = towerString(A);
        String[] towerB = towerString(B);
        String[] towerC = towerString(C);

        for (int i = 0; i < towerA.length; i++) {
            System.out.println(towerA[i] + towerB[i] + towerC[i]);
        }
    }
}
