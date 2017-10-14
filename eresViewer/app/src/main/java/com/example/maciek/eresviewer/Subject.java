package com.example.maciek.eresviewer;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.maciek.eresviewer.data.MarksContract;

import java.util.ArrayList;

public class Subject {
    public MarkAdapter markAdapter;
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

    public Subject(String subject, Context context) {
        subjectName = subject;
        this.context = context;
        shortSubjectName = subject.substring(0, subject.indexOf('.'));
        marks = new ArrayList<>();
        markAdapter = new MarkAdapter(context, marks);

        //createTestMark();
        createMarksFromDb();
    }

    public void addMark(Mark mark) {
        marks.add(mark);

        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, shortSubjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, mark.getMarkTitle());
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, mark.getMyMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, mark.getLowerMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, mark.getAverageMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, mark.getHigherMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, mark.getAmountOfMarks());

        context.getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);
        markAdapter.notifyDataSetChanged();
    }

    public void editMark(Mark oldMark, Mark newMark) {
        oldMark.editMark(newMark);

        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, shortSubjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, newMark.getMarkTitle());
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, newMark.getMyMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, newMark.getLowerMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, newMark.getAverageMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, newMark.getHigherMark() * 100);
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, newMark.getAmountOfMarks());

        int updatedIndex = context.getContentResolver().update(ContentUris.withAppendedId(MarksContract.MarksEntry.CONTENT_URI, oldMark.getDatabaseID()), values, null, null);
        markAdapter.notifyDataSetChanged();

    }

    public void deleteMark(Mark mark) {
    }
    public Context getContext(){ return context;}


    public void compareDownloadedMarks(ArrayList<Mark> downloaded) {
        boolean found;
        for (Mark newMark : downloaded) {
            found = false;
            for (Mark oldMark : marks) {
                if (oldMark.getMarkTitle().equals(newMark.getMarkTitle())) {
                    found = true;
                    if (!compareMarks(oldMark,newMark)) editMark(oldMark, newMark);
                }
            }
            if (!found) addMark(newMark);
        }

    }

    private boolean compareMarks(Mark mark1, Mark mark2) {
        if (mark1.getMyMark() == mark2.getMyMark() &&
                mark1.getLowerMark() == mark2.getLowerMark() &&
                mark1.getAverageMark() == mark2.getAverageMark() &&
                mark1.getHigherMark() == mark2.getHigherMark() &&
                mark1.getAmountOfMarks() == mark2.getAmountOfMarks()) return true;
        else return false;
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

    public void createTestMark() {
        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, shortSubjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "Test: " + subjectName);
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 3);
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 0);
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

        context.getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);
        markAdapter.notifyDataSetChanged();
    }

    private void createMarksFromDb() {
        String[] projection = {
                MarksContract.MarksEntry.COLUMN_SUBJECT,
                MarksContract.MarksEntry._ID,
                MarksContract.MarksEntry.COLUMN_MARK_TITLE,
                MarksContract.MarksEntry.COLUMN_MY_MARK,
                MarksContract.MarksEntry.COLUMN_LOWER_MARK,
                MarksContract.MarksEntry.COLUMN_AVEREGE_MARK,
                MarksContract.MarksEntry.COLUMN_HIGHER_MARK,
                MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS};

        String selection = "subject = '" + shortSubjectName + "';";
        Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, null, null);

        // Find the marks columns that we're interested in
        int markID = cursor.getColumnIndex(MarksContract.MarksEntry._ID);
        int markTitleColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MARK_TITLE);
        int myMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MY_MARK);
        int minMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_LOWER_MARK);
        int avgMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK);
        int maxMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_HIGHER_MARK);
        int amountOfMarksColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS);

        //getMarks().clear();
        while (cursor.moveToNext()) {
            getMarks().add(new Mark(cursor.getInt(markID),
                    cursor.getString(markTitleColumnIndex),
                    cursor.getFloat(myMarkColumnIndex) / 100,
                    cursor.getFloat(minMarkColumnIndex) / 100,
                    cursor.getFloat(avgMarkColumnIndex) / 100,
                    cursor.getFloat(maxMarkColumnIndex) / 100,
                    cursor.getInt(amountOfMarksColumnIndex)));
            Log.v("Subject", "Stworzono ocenę z " + subjectName);
        }
        cursor.close();
    }


}
