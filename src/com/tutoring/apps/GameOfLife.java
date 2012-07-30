package com.tutoring.apps;

import sun.java2d.SunGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/30/12
 * Time: 5:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameOfLife extends Canvas {
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        Dimension size = getSize();

        int w =  size.width;
        int h =  size.height;

        Random r = new Random();

        for (int i=0; i<1000; i++) {
            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
            g2d.drawLine(x, y, x, y);
        }
    }
    public static void main(String[] args) {
        GameOfLife canvas = new GameOfLife();
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(250, 200);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
    }
}
