package com.example.maciek.eresviewer.data;

import android.provider.BaseColumns;

/**
 * Created by Adrian on 2017-07-14.
 */

// Kontrakt bazy danych
public final class MarksContract {

    private MarksContract(){}

    public static abstract class MarksEntry implements BaseColumns {

        public static final String TABLE_NAME = "marks";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MARK_TITLE = "mark_title";
        public static final String COLUMN_MY_MARK = "my_mark";
        public static final String COLUMN_LOWER_MARK = "lower_mark";
        public static final String COLUMN_AVEREGE_MARK = "averege_mark";
        public static final String COLUMN_HIGHER_MARK = "higher_mark";
        public static final String COLUMN_AMOUNT_OF_MARKS = "amount_of_marks";

    }
}
