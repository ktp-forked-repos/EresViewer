package com.example.maciek.eresviewer;

import java.util.ArrayList;

/**
 * Created by Maciek on 17.07.2017.
 */

public class Subject {
    /**
     * nazwa przedmiotu, który opisuje klasa
     */
    private String subjectName;
    /**
     * Lista ocen
     */
    private ArrayList<Mark> marks;

    public Subject(String subjectName){
        this.subjectName=subjectName;
        marks=new ArrayList<>();
    }

    public void addMark(Mark mark){
        marks.add(mark);
    }
    public ArrayList<Mark> getMarks(){ return marks; }

}
