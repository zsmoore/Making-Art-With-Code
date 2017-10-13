package com.zachary_moore.gameoflife;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

class SingleCell {

    private int height;
    private int width;
    private int x;
    private int y;
    private Point location;
    private GameOfLife gameOfLife;
    private ArrayList<SingleCell> neighbors;
    private boolean live;
    private final String TAG = this.getClass().getSimpleName();

    SingleCell(int height, int width, int x, int y, GameOfLife gameOfLife) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.gameOfLife = gameOfLife;
        this.neighbors = new ArrayList<>(8);
        this.live = false;
    }

    void display() {
        if (live) {
            gameOfLife.fill(115);
        } else {
            gameOfLife.fill(0);
        }
        gameOfLife.rect(this.x, this.y, width, height);

    }

    boolean isLive() {
        return live;
    }

    void toggleLive() {
        live = !live;
    }

    void setLocation(int row, int col) {
        location = new Point(row, col);
    }

    Point getLocation() {
        return location;
    }

    void setNeighbors(ArrayList<SingleCell> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<SingleCell> getNeighbors() {
        return neighbors;
    }

    ConwayType.Conway getConway() {
        int count = 0;
        for (SingleCell cell : neighbors) {
            if (cell.live) {
                count += 1;
            }
        }
        Log.d(TAG, "Count = " + count);

        switch (count) {
            case 0:
            case 1:
                return ConwayType.Conway.UNDERPOPULATION;
            case 2:
                return ConwayType.Conway.SURVIVAL;
            case 3:
                if (live) {
                    return ConwayType.Conway.SURVIVAL;
                } else {
                    return ConwayType.Conway.REPRODUCTION;
                }
            default:
                return ConwayType.Conway.OVERPOPULATION;
        }
    }
}
