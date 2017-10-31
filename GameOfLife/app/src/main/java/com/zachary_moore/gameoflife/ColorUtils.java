package com.zachary_moore.gameoflife;

import android.graphics.Color;
import android.util.SparseIntArray;

class ColorUtils {

    // Static Hex Color Representing dead
    static final int DEAD = Color.parseColor("#fbf1c7");

    private static final SparseIntArray colorToState = new SparseIntArray();
    static {
        colorToState.put(Color.parseColor("#fb4934"), 0);
        colorToState.put(Color.parseColor("#b8bb26"), 1);
        colorToState.put(Color.parseColor("#fabd2f"), 2);
        colorToState.put(Color.parseColor("#83a598"), 3);
        colorToState.put(Color.parseColor("#d3869b"), 4);
        colorToState.put(Color.parseColor("#8ec07c"), 5);
        colorToState.put(Color.parseColor("#fe8019"), 6);
        colorToState.put(Color.parseColor("#282828"), 7);
    }

    /**
     * State analyzer to determine what color our cell should be
     * @param colorState Current state in color rotation
     * @return Hex Color that our cell will be set to
     */
    static int getColor(int colorState) {
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

    /**
     * Will return the corresponding state for a color
     * @param color The color to find state of
     * @return colorState for specified color, 0 if not found
     */
    static int getColorState(int color) {
        return colorToState.get(color);
    }

    static void handleColor(SingleCell cell, int representation) {
        cell.setColorState(getColorState(representation));
    }
}
