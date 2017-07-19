package com.example.maciek.eresviewer;

/**
 * Created by Maciek on 09.07.2017.
 */

public class Mark {
    /**
     * zmienna przechowująca informację o tym za co jest dana ocena jest
     **/
    private String markTitle;
    /**
     * Ocena użytkownika
     */
    private float myMark;
    /**
     * Minimalna ocena
     */
    private float lowerMark;
    /**
     * Srednia ocena z całego roku
     */
    private float averageMark;
    /**
     * Maksymalna ocena
     */
    private float higherMark;
    /**
     * Liczba ocen
     */
    private int amountOfMarks;

   //gettery

    public String getMarkTitle() { return markTitle; }
    public float getMyMark() { return myMark; }
    public float getAverageMark() {return averageMark; }
    public float getHigherMark() { return higherMark; }
    public int getAmountOfMarks() { return amountOfMarks; }
    public float getLowerMark() { return lowerMark; }

    Mark(String markTitle, float myMark, float lowerMark,float averageMark,float higherMark, int amountOfMarks){
        this.markTitle=markTitle;
        this.myMark=myMark;
        this.lowerMark=lowerMark;
        this.averageMark=averageMark;
        this.higherMark=higherMark;
        this.amountOfMarks=amountOfMarks;
    }

}
