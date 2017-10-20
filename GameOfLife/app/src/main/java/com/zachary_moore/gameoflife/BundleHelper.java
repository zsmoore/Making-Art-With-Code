package com.zachary_moore.gameoflife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

class BundleHelper {

    private static final String GAME_OF_LIFE = "gameOfLife";
    private static final String ROW_SIZE = "rowSize";
    private static final String COL_SIZE = "colSize";

    private static final String TAG = "BUNDLE";

    static GameOfLife restore(Bundle state) {
        GameOfLife gameOfLife = null;
        if (hasRowSize(state) && hasColSize(state) && hasRepresentation(state)) {
            gameOfLife = new GameOfLife(getRowSize(state), getColSize(state));
            gameOfLife.initializeGrid();
            restoreGame(getRepresentation(state), gameOfLife);
        }

        if (gameOfLife == null) {
            return new GameOfLife();
        } else {
            return gameOfLife;
        }
    }

    private static Integer[][] serializeGame(GameOfLife gameOfLife) {
        Pair<Integer, Integer> dimensions = gameOfLife.getGridDimensions();
        SingleCell[][] cellLoc = gameOfLife.getCellLoc();

        Integer[][] representation = new Integer[dimensions.first][dimensions.second];
        for (int i = 0; i < dimensions.first; i++) {
            for (int j = 0; j < dimensions.second; j++){
                representation[i][j] = cellLoc[i][j].getColor();
            }
        }

        return representation;
    }

    private static void restoreGame(Integer[][] representation, GameOfLife gameOfLife) {
        Pair<Integer, Integer> dimensions = gameOfLife.getGridDimensions();
        SingleCell[][] cellLoc = gameOfLife.getCellLoc();

        for (int i = 0; i < dimensions.first; i++) {
            for (int j = 0; j < dimensions.second; j++) {
                restoreCell(cellLoc[i][j], representation[i][j]);
            }
        }

        gameOfLife.setCellLoc(cellLoc);
    }

    private static void restoreCell(SingleCell cell, int representation) {
        if (representation == ColorUtils.DEAD) {
            cell.setDead();
        } else {
            cell.setLive();
        }
        ColorUtils.handleColor(cell, representation);
    }

    static void setBundle(Bundle state, GameOfLife gameOfLife) {
        Pair<Integer, Integer> dimensions = gameOfLife.getGridDimensions();
        state.putSerializable(GAME_OF_LIFE, BundleHelper.serializeGame(gameOfLife));
        state.putInt(ROW_SIZE, dimensions.first);
        state.putInt(COL_SIZE, dimensions.second);
    }

    private static boolean hasRowSize(Bundle state) {
        return state.get(ROW_SIZE) != null;
    }

    private static boolean hasColSize(Bundle state) {
        return state.get(COL_SIZE) != null;
    }

    private static boolean hasRepresentation(Bundle state) {
        return state.get(GAME_OF_LIFE) != null;
    }

    @Nullable
    private static Integer getRowSize(Bundle state) {
        return (Integer) state.get(ROW_SIZE);
    }

    @Nullable
    private static Integer getColSize(Bundle state) {
        return (Integer) state.get(COL_SIZE);
    }

    @Nullable
    private static Integer[][] getRepresentation(Bundle state) {
        return (Integer[][]) state.get(GAME_OF_LIFE);
    }
}
