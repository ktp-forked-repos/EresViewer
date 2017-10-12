package com.example.maciek.eresviewer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.maciek.eresviewer.data.MarksContract.MarksEntry;
import com.example.maciek.eresviewer.data.MarksContract.SubjectsEntry;

/**
 * Created by Adrian on 2017-07-14.
 */

public class MarksDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "marks.db";

    public MarksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MarksEntry.TABLE_NAME + " (" +
                        MarksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MarksEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                        MarksEntry.COLUMN_MARK_TITLE + " TEXT NOT NULL," +
                        MarksEntry.COLUMN_MY_MARK + " INTEGER," +
                        MarksEntry.COLUMN_LOWER_MARK + " INTEGER," +
                        MarksEntry.COLUMN_HIGHER_MARK + " INTEGER," +
                        MarksEntry.COLUMN_AVEREGE_MARK + " INTEGER," +
                        MarksEntry.COLUMN_AMOUNT_OF_MARKS + " INTEGER);" +
                "CREATE TABLE " + SubjectsEntry.TABLE_NAME + " (" +
                        SubjectsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SubjectsEntry.COLUMN_SUBJECT_TITLE + " TEXT NOT NULL," +
                        SubjectsEntry.COLUMN_SUBJECT_TITLE_FULL + " TEXT NOT NULL," +
                        SubjectsEntry.COLUMN_MARK_3 + "INTEGER" +
                        SubjectsEntry.COLUMN_MARK_3_5 + "INTEGER" +
                        SubjectsEntry.COLUMN_MARK_4 + "INTEGER" +
                        SubjectsEntry.COLUMN_MARK_4_5 + "INTEGER" +
                        SubjectsEntry.COLUMN_MARK_5 + "INTEGER" +
                        SubjectsEntry.COLUMN_NUMBER_OF_TESTS + "INTEGER" +
                        SubjectsEntry.COLUMN_NUMBER_OF_EXAMS + "INTEGER" +
                        SubjectsEntry.COLUMN_NUMBER_OF_PROJECTS + "INTEGER" +
                        SubjectsEntry.COLUMN_NUMBER_OF_EXTRA + "INTEGER" +
                        SubjectsEntry.COLUMN_NUMBER_OF_LABS + "INTEGER" +
                        SubjectsEntry.COLUMN_MIN_TEST_POINTS + "INTEGER" +
                        SubjectsEntry.COLUMN_MIN_EXTRA_POINTS + "INTEGER" +
                        SubjectsEntry.COLUMN_MIN_PROJECTS_POINTS + "INTEGER" +
                        SubjectsEntry.COLUMN_MIN_LABS_POINTS + "INTEGER);";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MarksEntry.TABLE_NAME + "," + SubjectsEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
