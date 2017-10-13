package com.zachary_moore.gameoflife;

import android.graphics.Point;

import java.util.ArrayList;;

import processing.core.PApplet;

public class GameOfLife extends PApplet {

    private int rowSize;
    private int colSize;
    private int cellHeight;
    private int cellWidth;
    private boolean isStarted = false;

    private SingleCell[][] cellLoc;
    private final String TAG = this.getClass().getSimpleName();

    GameOfLife (int xNum, int yNum) {
        this.rowSize = yNum;
        this.colSize = xNum;
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        noStroke();
        background(blue(115));
        frameRate(15);

        cellHeight = height / rowSize;
        cellWidth = width / colSize;
        cellLoc = new SingleCell[rowSize][colSize];


        int x = 0;
        int y = 0;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                SingleCell cell = new SingleCell(cellHeight, cellWidth, this, x, y);
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
            conway();
        } else if (mousePressed) {
            mouseClicked();
        }
        refresh();
    }

    void startConway() {
        isStarted = true;
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        int col = mouseY / cellHeight;
        int row = mouseX / cellWidth;
        cellLoc[row][col].toggleLive();
    }

    void refresh() {
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
        for (int y = Math.max(baseLocation.y - 1, 0); y <= Math.min(baseLocation.y + 1, rowSize - 1); y++) {
            for (int x = Math.max(baseLocation.x - 1, 0); x <= Math.min(baseLocation.x + 1, colSize - 1); x++) {
                if (!(y == baseLocation.y && x == baseLocation.x)) {
                    neighbors.add(cellLoc[y][x]);
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
                        if (cell.isLive()) result.add(new Point(i,j));
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