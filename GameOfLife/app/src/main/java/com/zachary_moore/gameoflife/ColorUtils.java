package com.zachary_moore.gameoflife;

import android.graphics.Color;

class ColorUtils {

    // Static Hex Color Representing dead
    static final int DEAD = Color.parseColor("#fbf1c7");

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
}
