package com.example.maciek.eresviewer;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import com.example.maciek.eresviewer.data.MarksContract;

import java.util.ArrayList;

/**
 * Created by Maciek on 17.07.2017.
 */

public class Subject {
    /**
     * nazwa przedmiotu, który opisuje klasa
     */
    private String subjectName;
    private String shortSubjectName;
    /**
     * Lista ocen
     */
    private ArrayList<Mark> marks;

    private SubjectFragment fragment;

    public Subject(String subject) {
        subjectName = subject;
        shortSubjectName = subject.substring(0, subject.indexOf('.'));
        marks = new ArrayList<>();
        fragment = new SubjectFragment();

        Bundle args = new Bundle();
        args.putString("name", shortSubjectName);
        fragment.setArguments(args);
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getShortSubjectName() {
        return shortSubjectName;
    }

    public SubjectFragment getFragment() {
        return fragment;
    }

    public void createTestMark(Context context) {
        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, shortSubjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "Test: " + subjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

        context.getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);
    }

}
