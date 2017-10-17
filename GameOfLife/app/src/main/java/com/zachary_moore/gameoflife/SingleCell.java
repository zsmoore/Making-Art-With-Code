package com.zachary_moore.gameoflife;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

class SingleCell {

    private float height;
    private float width;
    private float x;
    private float y;
    private int colorState;
    private Point location;
    private GameOfLife gameOfLife;
    private ArrayList<SingleCell> neighbors;
    private boolean live;
    private final String TAG = this.getClass().getSimpleName();
    private static final int DEAD = Color.parseColor("#fbf1c7");

    SingleCell(float x, float y, GameOfLife gameOfLife) {
        this.height = gameOfLife.getCellHeight();
        this.width = gameOfLife.getCellWidth();
        this.x = x;
        this.y = y;
        this.gameOfLife = gameOfLife;
        this.neighbors = new ArrayList<>(8);
        this.live = false;
        this.colorState = -1;
    }

    void display() {
        if (live) {
            gameOfLife.fill(getColor());
        } else {
            gameOfLife.fill(DEAD);
        }
        gameOfLife.rect(this.x, this.y, width, height);
    }

    boolean isLive() {
        return live;
    }

    void changeSize(int newHeight, int newWidth) {
        this.height = newHeight;
        this.width = newWidth;
    }

    void changeHeight(int newHeight) {
        this.height = newHeight;
    }

    void changeWidth(int newWidth) {
        this.width = newWidth;
    }

    void toggleLive() {
        live = !live;
        if (live) colorState = colorState == 7 ? 0 : colorState + 1;
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

    private int getColor() {
        switch (colorState) {
            case 0:
                return Color.parseColor("#fb4934");
            case 1:
                return Color.parseColor("#b8bb26");
            case 2:
                return Color.parseColor("#fabd2f");
            case 3:
                return Color.parseColor("#83a598");
            case 4:
                return Color.parseColor("#d3869b");
            case 5:
                return Color.parseColor("#8ec07c");
            case 6:
                return Color.parseColor("#fe8019");
            default:
                return Color.parseColor("#282828");
        }
    }
}
