package com.google.gson.stream;

import java.util.Arrays;

public class Stack {
    private int[] jsonScope = new int[32];
    private int occupancy =0;

    public Stack() {
        jsonScope[occupancy] = JsonScope.EMPTY_DOCUMENT;
        occupancy++;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public int getCurrentScope() {
        return jsonScope[occupancy - 1];
    }

    public void setCurrentScope(int scope) {
        jsonScope[occupancy - 1] = scope;
    }

    public void moveBackward() {
        occupancy--;
    }

    public void moveForward() {
        occupancy++;
    }

    public boolean isFull() {
        boolean isFull = occupancy == jsonScope.length;
        return isFull;
    }

    public void close() {
        jsonScope[0] = JsonScope.CLOSED;
        occupancy = 1;
    }

    public void duplicate() {
        int newLength = occupancy * 2;
        jsonScope = Arrays.copyOf(jsonScope, newLength);
    }

    public int getScopeFromIndex(int index) {
        return jsonScope[index];
    }

    public void setNewTop(int scope) {
        jsonScope[occupancy] =scope;
    }
}
