package com.zachary_moore.gameoflife.model.core;

import android.graphics.Point;

import com.zachary_moore.gameoflife.model.util.ColorUtils;
import com.zachary_moore.gameoflife.model.util.enums.ConwayType;

import java.util.ArrayList;

public class SingleCell {

    // Cell Height
    private float height;
    // Cell Width
    private float width;
    // Cell x position in gameOfLife canvas
    private float x;
    // Cell Y position in gameOfLife canvas
    private float y;
    // Current state in the color rotation
    private int colorState;
    // X Y Location in gameOfLife data representation
    private Point location;
    // GameOfLife object this cell is tied to
    private GameOfLife gameOfLife;
    // ArrayList of neighbors this cell has in the gameOfLife canvas
    private ArrayList<SingleCell> neighbors;
    // State of cell if it is alive or not
    private boolean live;


    private final String TAG = this.getClass().getSimpleName();

    /**
     * Constructor for SingleCell
     * @param x X position on gameOfLife canvas. Used for drawing
     * @param y Y position on gameOfLife canvas. Used for drawing
     * @param gameOfLife gameOfLife to be used for drawing on
     */
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

    /**
     * Display method.  If alive fill with color else use dead color.
     * Uses processing rect to draw at objects x and y with width and height.
     */
    void display() {
        if (live) {
            gameOfLife.fill(ColorUtils.getColor(colorState));
        } else {
            gameOfLife.fill(ColorUtils.DEAD);
        }
        gameOfLife.rect(this.x, this.y, width, height);
    }

    /**
     * Accessor to tell if cell is alive or not
     * @return True if cell is living and colored
     */
    boolean isLive() {
        return live;
    }

    /**
     * Change size of cell object
     * @param newHeight new Height of cell
     * @param newWidth new Width of cell
     */
    void changeSize(float newHeight, float newWidth) {
        this.height = newHeight;
        this.width = newWidth;
    }

    /**
     * Change the height of the cell
     * @param newHeight new Height of the cell
     */
    void changeHeight(float newHeight) {
        this.height = newHeight;
    }

    /**
     * Change the width of the cell
     * @param newWidth new Width of the cell
     */
    void changeWidth(float newWidth) {
        this.width = newWidth;
    }

    /**
     * Change the cell from alive to dead or dead to alive.
     * If we toggle to live, update our cell color
     */
    void toggleLive() {
        live = !live;
        if (live) colorState = colorState == 7 ? 0 : colorState + 1;
    }

    public void setDead() {
        live = false;
    }

    public void setLive() {
        live = true;
    }

    public void setColorState(int state) {
        colorState = state;
    }

    void reset() {
        colorState = -1;
        setDead();
    }

    /**
     * Resets a cell's color state back to initial state
     */
    void resetColor() {
        colorState = -1;
    }
    /**
     * Set location of cell in gameOfLife grid representation
     * @param row Row in grid that our cell is at
     * @param col Col in grid that our cell is at
     */
    void setLocation(int row, int col) {
        location = new Point(row, col);
    }

    /**
     * Accessor for our cell's location in the gameOfLife data rep grid
     * @return A Point object with the x and y of the cell in the gameOfLife grid
     */
    Point getLocation() {
        return location;
    }

    /**
     * Color return method used for serialization
     * @return The current color of the cell
     */
    public int getColor() {
        if (live) {
            return ColorUtils.getColor(colorState);
        } else {
            return ColorUtils.DEAD;
        }
    }

    /**
     * Setter method for our cell's neighbors
     * @param neighbors ArrayList of cells that represents our cell's neighbors
     */
    void setNeighbors(ArrayList<SingleCell> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Accessor method for our cell's neighbors
     * @return ArrayList of cells that represent our cell's neighbors
     */
    public ArrayList<SingleCell> getNeighbors() {
        return neighbors;
    }

    /**
     * Get our current conway state
     * @return ConwayType representing what conway state our cell is in
     */
    ConwayType.Conway getConway() {
        int count = 0;

        // Count up the number of live cells in our neighbors
        for (SingleCell cell : neighbors) {
            if (cell.live) {
                count += 1;
            }
        }

        // Based on how many live neighbors we have evaluate our state
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
