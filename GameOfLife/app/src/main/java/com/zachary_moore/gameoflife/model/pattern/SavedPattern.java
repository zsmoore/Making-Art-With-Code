package com.zachary_moore.gameoflife.model.pattern;


import com.zachary_moore.gameoflife.model.core.SingleCell;

public class SavedPattern {

    private SingleCell[][] patternRep;

    private String patternName;

    SavedPattern(SingleCell[][] patternRep, String patternName) {
        this.patternRep = patternRep;
        this.patternName = patternName;
    }



}
