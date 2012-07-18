package com.tutoring.apps;

import com.tutoring.libs.hanoi.Towers;

public class TowersOfHanoi {
    public static void main(String[] args) {
        int numDiscs = 9;
        Towers hTowers = new Towers(numDiscs);
        hTowers.printTowers();
        moveRecursive(hTowers, 'A', 'C', numDiscs);
    }

    public static void moveRecursive(Towers towers, char from, char to, int num) {
        //If we're trying to move a single disc, just move it.
        if (num == 1) {
            boolean status = towers.moveDisc(from, to);
            if (!status) {
                throw new RuntimeException("Bad move attempted from tower " + from + " to " + to + ".");
            }
            towers.printTowers();
            return;
        }

        //Figure out the tower we aren't using
        char other = 'A';
        if (other == from || other == to) {
            other = 'B';
        }
        if (other == from || other == to) {
            other = 'C';
        }

        //Move n-1 from "from" to "other"
        moveRecursive(towers, from, other, num-1);

        //Move the remaining from "from" to "to"
        moveRecursive(towers, from, to, 1);

        //Move the n-1 left from "other" to "to"
        moveRecursive(towers, other, to, num-1);
    }
}
