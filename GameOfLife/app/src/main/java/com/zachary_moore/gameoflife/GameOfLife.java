package com.zachary_moore.gameoflife;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import processing.core.PApplet;

public class GameOfLife extends PApplet {

    private int rowSize;
    private int colSize;
    private float cellHeight;
    private float cellWidth;
    private boolean isStarted;
    private boolean isStep;

    private SingleCell[][] cellLoc;
    private final String TAG = this.getClass().getSimpleName();

    GameOfLife () {
        this.colSize = 35;
        this.rowSize = 35;
        isStarted = false;
        isStep = false;
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        noStroke();
        background(blue(115));

        cellHeight = (float) height / rowSize;
        cellWidth = (float) width / colSize;
        cellLoc = new SingleCell[rowSize][colSize];

        float x = 0;
        float y = 0;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                SingleCell cell = new SingleCell(x, y, this);
                cell.setLocation(i, j);

                cellLoc[i][j] = cell;

                y += cellHeight;
                if (y + cellHeight > height) {
                    y = 0;
                    x += cellWidth;
                }
            }
        }

        setNeighbors();
    }

    public void draw() {
        if (isStarted) {
            frameRate(15);
            conway();
        } else if (mousePressed) {
            mouseClicked();
        } else if(isStep) {
            conway();
            isStep = false;
        }
        refresh();
    }

    void startConway() {
        isStarted = true;
    }

    void stepOne() {
        isStep = true;
    }

    float getCellHeight() {
        return cellHeight;
    }

    float getCellWidth() {
        return cellWidth;
    }

    void reset() {
        isStarted = false;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (cellLoc[i][j].isLive()) {
                    cellLoc[i][j].toggleLive();
                }
            }
        }
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        int col = mouseY / (int) cellHeight;
        int row = mouseX / (int) cellWidth;

        if (col == colSize) {
            col --;
        }

        if (row == rowSize) {
            row --;
        }

        cellLoc[row][col].toggleLive();
    }

    private void refresh() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                cellLoc[i][j].display();
            }
        }
    }

    private void setNeighbors() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                cellLoc[i][j].setNeighbors(getNeighbors(cellLoc[i][j]));
            }
        }
    }

    private ArrayList<SingleCell> getNeighbors(SingleCell toGet) {
        //Initial Capacity to 8 since that is our max
        ArrayList<SingleCell> neighbors = new ArrayList<>(8);

        Point baseLocation = toGet.getLocation();
        for (int i = Math.max(baseLocation.x - 1, 0); i <= Math.min(baseLocation.x + 1, rowSize - 1); i++) {
            for (int j = Math.max(baseLocation.y - 1, 0); j <= Math.min(baseLocation.y + 1, colSize - 1); j++) {
                if (!(i == baseLocation.x && j == baseLocation.y)) {
                    neighbors.add(cellLoc[i][j]);
                }
            }
        }
        return neighbors;
    }

    private void conway() {
        for (Point toToggle : togglePoints()) {
            cellLoc[toToggle.x][toToggle.y].toggleLive();
        }
    }

    private ArrayList<Point> togglePoints() {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                SingleCell cell = cellLoc[i][j];
                ConwayType.Conway conwayType = cell.getConway();
                switch(conwayType) {
                    case UNDERPOPULATION:
                        if (cell.isLive()) result.add(new Point(i, j));
                        break;
                    case SURVIVAL:
                        break;
                    case REPRODUCTION:
                        result.add(new Point(i, j));
                        break;
                    case OVERPOPULATION:
                        if (cell.isLive()) result.add(new Point(i,j));
                        break;
                }
            }
        }
        return result;
    }
}