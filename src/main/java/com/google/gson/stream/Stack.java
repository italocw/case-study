package com.google.gson.stream;

import java.util.Arrays;

public class Stack {
    private int[] jsonScope = new int[32];
    private int occupancy = 0;

    /*
     * The path members. It corresponds directly to stack: At indices where the
     * stack contains an object (EMPTY_OBJECT, DANGLING_NAME or NONEMPTY_OBJECT),
     * pathNames contains the name at this scope. Where it contains an array
     * (EMPTY_ARRAY, NONEMPTY_ARRAY) pathIndices contains the current index in
     * that array. Otherwise the value is undefined, and we take advantage of that
     * by incrementing pathIndices when doing so isn't useful.
     */
    private String[] pathNames = new String[32];
    private int[] pathIndices = new int[32];

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
        pathIndices = Arrays.copyOf(pathIndices, newLength);
        pathNames = Arrays.copyOf(pathNames, newLength);
    }

    public int getScopeFromIndex(int index) {
        return jsonScope[index];
    }

    public void push(int scope) {
        jsonScope[occupancy] = scope;
        occupancy++;
    }

    public void moveToNextArrayBegin() {
        pathIndices[occupancy - 1] = 0;
    }

    public void increaseLastPathIndex() {
        pathIndices[occupancy - 1]++;
    }

    public void notifyArrayEnd() {
        moveBackward();
        increaseLastPathIndex();
    }

    public void notifyObjectEnd() {
        moveBackward();
        pathNames[occupancy] = null; // Free the last path name so that it can be garbage collected!
        pathIndices[occupancy - 1]++;
    }

    public void setLastPathName(String value) {
        pathNames[occupancy- 1] =value;
    }

    public int getPathIndex(int index) {
       return pathIndices[index];
    }

    public String getPathName(int index) {
        return pathNames[index];
    }
}
