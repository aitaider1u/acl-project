package com.dala.mapz.utils;

import static java.lang.Math.PI;

public enum Orientation
{
    UP("UP", 0, 0, 1, 0f),
    DOWN("DOWN", 2, 0, -1, (float)PI),
    LEFT("LEFT", 1, -1, 0, (float)PI/2f),
    RIGHT("RIGHT", 3, 1, 0, (float)-PI/2f);
    private String name;
    private final int index;
    private final int x;
    private final int y;
    private final float angle;
    Orientation(String name, int i, int x, int y, float angle) {
        this.name = name;
        this.index = i;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public Orientation inv() {
        if(this.equals(LEFT))
            return RIGHT;
        if(this.equals(RIGHT))
            return LEFT;
        if(this.equals(UP))
            return DOWN;
        return UP;
    }

    public Orientation getSide() {
        if(this.equals(UP) || this.equals(DOWN))
            return LEFT;
        return UP;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
