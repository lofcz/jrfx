package com.company;

/**
 *  Class Point
 *  Barebone implementation of a 2D int vector
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Point {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point() {

    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
