package com.example.maciek.eresviewer;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.maciek.eresviewer.data.MarksContract;

import java.util.ArrayList;

public class Subject {
    /**
     * nazwa przedmiotu, który opisuje klasa
     */
    private String subjectName;
    private String shortSubjectName;
    private Uri contentUri = Uri.withAppendedPath(MarksContract.BASE_CONTENT_URI, MarksContract.PATH_MARKS);
    private Context context;
    /**
     * Lista ocen
     */
    private ArrayList<Mark> marks;

    public Subject(String subject) {
        subjectName = subject;
        shortSubjectName = subject.substring(0, subject.indexOf('.'));
        marks = new ArrayList<>();

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

    /*public void createTestMark() {
        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, shortSubjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "Test: " + subjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 3);
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

        context.getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);
        createMarksFromDb();
        fragment.markAdapter.notifyDataSetChanged();
        fragment.onRefresh();
    }*/


}
