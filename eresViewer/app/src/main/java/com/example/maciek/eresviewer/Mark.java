package com.example.maciek.eresviewer;

/**
 * Created by Maciek on 09.07.2017.
 */

public class Mark {

    private String markTitle;
    private float myMark;
    private float lowerMark;
    private float averageMark;
    private float higherMark;
    private int amountOfMarks;

    Mark(String markTitle, float myMark, float lowerMark, float averageMark, float higherMark, int amountOfMarks) {
        this.markTitle = markTitle;
        this.myMark = myMark;
        this.lowerMark = lowerMark;
        this.averageMark = averageMark;
        this.higherMark = higherMark;
        this.amountOfMarks = amountOfMarks;
    }

    public String getMarkTitle() {
        return markTitle;
    }

    public float getMyMark() {
        return myMark;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public float getHigherMark() {
        return higherMark;
    }

    public int getAmountOfMarks() {
        return amountOfMarks;
    }

    public float getLowerMark() {
        return lowerMark;
    }
}
