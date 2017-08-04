package com.example.maciek.eresviewer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.maciek.eresviewer.data.MarksContract;

/**
 * Created by Adrian on 2017-08-03.
 */

public class MarkCursorAdapter extends CursorAdapter {

    public MarkCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_group, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.markName);
        TextView myMarkTextView = (TextView) view.findViewById(R.id.myMark);
        TextView minMarkTextView = (TextView) view.findViewById(R.id.lowerMark);
        TextView avgMarkTextView = (TextView) view.findViewById(R.id.averageMark);
        TextView maxMarkTextView = (TextView) view.findViewById(R.id.higherMark);
        TextView amountOfMarksTextView = (TextView) view.findViewById(R.id.amountOfMarks);

        // Find the marks columns that we're interested in
        int markTitleColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MARK_TITLE);
        int myMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MY_MARK);
        int minMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_LOWER_MARK);
        int avgMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK);
        int maxMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_HIGHER_MARK);
        int amountOfMarksColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS);

        // Update the TextViews with the attributes for the current mark
        nameTextView.setText(cursor.getString(markTitleColumnIndex));
        myMarkTextView.setText(String.valueOf(cursor.getFloat(myMarkColumnIndex) / 100));
        minMarkTextView.setText(String.valueOf(cursor.getFloat(minMarkColumnIndex) / 100));
        avgMarkTextView.setText(String.valueOf(cursor.getFloat(avgMarkColumnIndex) / 100));
        maxMarkTextView.setText(String.valueOf(cursor.getFloat(maxMarkColumnIndex) / 100));
        amountOfMarksTextView.setText(String.valueOf(cursor.getInt(amountOfMarksColumnIndex)));

    }

}
