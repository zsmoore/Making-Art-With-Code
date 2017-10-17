package com.zachary_moore.gameoflife;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import processing.core.PApplet;

public class GameOfLife extends PApplet {

    // Number of cells in a row in our canvas
    private int rowSize;
    // Number of cells in a col in our canvas
    private int colSize;
    // Height of each cell in our canvas
    private float cellHeight;
    // Width of each cell in our canvas
    private float cellWidth;
    // If our program has started our conway mutations
    private boolean isStarted;
    // If we are doing a single step
    private boolean isStep;

    // Grid representation of all of the cells in our canvas
    private SingleCell[][] cellLoc;

    private final String TAG = this.getClass().getSimpleName();

    /**
     * Default constructor for GameOfLife, sets row and col to 35
     */
    GameOfLife() {
        this.colSize = 35;
        this.rowSize = 35;
        isStarted = false;
        isStep = false;
    }

    /**
     * Constructor for GameOfLife, Allows custom grid size
     * @param colSize Number of cells in columns
     * @param rowSize Number of cells in rows
     */
    GameOfLife(int colSize, int rowSize) {
        this.colSize = colSize;
        this.rowSize = rowSize;
        isStarted = false;
        isStep = false;
    }

    /**
     * Needed method to set size of screen before setup
     */
    public void settings() {
        fullScreen();
    }

    /**
     * Our processing setup, initialize our data, create our cells
     */
    public void setup() {
        noStroke();

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

                // Setup x and y for next cell. If over our bounds update x and y
                y += cellHeight;
                if (y + cellHeight > height) {
                    y = 0;
                    x += cellWidth;
                }
            }
        }

        // After all our cells have been created set their neighbors.
        setNeighbors();
    }

    /**
     * Refreshing draw method from processing
     */
    public void draw() {
        // If we are started lower framerate to make mutations slower
        if (isStarted) {
            frameRate(15);
            conway();
        } else if (mousePressed) {
            // If we are not started and the mouse is pressed handle is
            mouseClicked();
        } else if(isStep) {
            // If we are stepping only perform a single update with controlling bool
            conway();
            isStep = false;
        }
        refresh();
    }

    /**
     * Starts our conway mutations
     */
    void startConway() {
        isStarted = true;
    }

    /**
     * Steps our conway a single time
     */
    void stepOne() {
        isStep = true;
    }

    /**
     * Accessor for cellHeight
     * @return the Height of a single cell in our canvas
     */
    float getCellHeight() {
        return cellHeight;
    }

    /**
     * Accessor for cellWidth
     * @return the Width of a single cell in our canvas
     */
    float getCellWidth() {
        return cellWidth;
    }

    /**
     * Reset our canvas and all the cells within it.
     * Set our start variable to false
     */
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


    /**
     * On mouseClick activate a cell to be live
     */
    @Override
    public void mouseClicked() {
        super.mouseClicked();
        int col = mouseY / (int) cellHeight;
        int row = mouseX / (int) cellWidth;

        // Since we need an int for our grid and our cells use floats,
        // if we are outside our grid then decrement once to be at end of grid.
        if (col == colSize) {
            col --;
        }
        if (row == rowSize) {
            row --;
        }

        cellLoc[row][col].toggleLive();
    }

    /**
     * Refresh all of our cell's display
     */
    private void refresh() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                cellLoc[i][j].display();
            }
        }
    }

    /**
     * Set the neighbors for each of our cells in the grid
     */
    private void setNeighbors() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                cellLoc[i][j].setNeighbors(getNeighbors(cellLoc[i][j]));
            }
        }
    }

    private ArrayList<SingleCell> getNeighbors(SingleCell toGet) {
        // Initial Capacity to 8 since that is our max possible amount of neighbors
        ArrayList<SingleCell> neighbors = new ArrayList<>(8);

        // Take advantage of our point representation to easily get a list of each neighbor
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

    /**
     * Main method to activate conway mutation
     */
    private void conway() {
        // For each point we have determined to get toggled, toggle
        for (Point toToggle : togglePoints()) {
            cellLoc[toToggle.x][toToggle.y].toggleLive();
        }
    }

    /**
     * Use our conway rules to get all points that need to be toggled dead or alive
     * @return ArrayList of all the points in our grid to have their alive state toggled
     */
    private ArrayList<Point> togglePoints() {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                SingleCell cell = cellLoc[i][j];

                // Get the conway type of the cell based on the cell's neighbors
                ConwayType.Conway conwayType = cell.getConway();

                // Based on our type of conway state, eval cell's live state
                // and either add to our points to be toggled or let survive
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