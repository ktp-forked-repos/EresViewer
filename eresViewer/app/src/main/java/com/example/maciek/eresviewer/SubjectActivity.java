package com.example.maciek.eresviewer;

import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.example.maciek.eresviewer.data.MarksContract;
import com.example.maciek.eresviewer.data.MarksDbHelper;

import java.util.List;

/**
 * Created by Maciek on 09.07.2017.
 */

public class SubjectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ExpandableListView listView;
    private ExpandableListAdapter markAdapter;
    private List<Mark> marks;
    /**
     * Database helper that will provide acces to the database
     */
    private MarksDbHelper mDbHelper;

    //Identifies a paricular loader beint used in this component
    private static final int MARK_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /*Instantinate subclass of SQLiteOpenHelper and pass the context which is the current activity**/
        mDbHelper = new MarksDbHelper(this);


        initData();
        setContentView(R.layout.activity_subject);
        markAdapter = new ExpandableListAdapter(this, marks);

        listView = (ExpandableListView) findViewById(R.id.listview_mark);
        listView.setAdapter(markAdapter);

        //Kick off the loader
        getLoaderManager().initLoader(MARK_LOADER, null, this);


    }

    private void initData() {

        /*Creating ArrayList for marks loaded from database*/
        //marks = new ArrayList<>();

        /*Define a projection that specifies which columnts from database we will use*/
       /* String[] projection = {
                MarksContract.MarksEntry._ID,
                MarksContract.MarksEntry.COLUMN_MARK_TITLE,
                MarksContract.MarksEntry.COLUMN_MY_MARK,
                MarksContract.MarksEntry.COLUMN_LOWER_MARK,
                MarksContract.MarksEntry.COLUMN_AVEREGE_MARK,
                MarksContract.MarksEntry.COLUMN_HIGHER_MARK,
                MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS};
*/
        /*Make a query on the marks table*/
        /**Cursor cursor = db.query(
         MarksContract.MarksEntry.TABLE_NAME, // The table to query
         projection,                          // Columns to return
         null,                                // Columns for the WHERE clause
         null,                                // Values for the WHERE clause
         null,                                // Don't group the rows
         null,                                // Don't filter by row groups
         null);                              // The sort order
         */

        //Cursor cursor = getContentResolver().query(MarksContract.MarksEntry.CONTENT_URI, projection, null, null, null);

        /*try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry._ID);
            int markTitleColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MARK_TITLE);
            int myMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_MY_MARK);
            int lowerMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_LOWER_MARK);
            int averegeMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK);
            int higherMarkColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_HIGHER_MARK);
            int amountOfMarksColumnIndex = cursor.getColumnIndex(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                marks.add(new Mark(
                        cursor.getString(markTitleColumnIndex),
                        cursor.getFloat(myMarkColumnIndex)/100,
                        cursor.getFloat(lowerMarkColumnIndex)/100,
                        cursor.getFloat(averegeMarkColumnIndex)/100,
                        cursor.getFloat(higherMarkColumnIndex)/100,
                        cursor.getInt(amountOfMarksColumnIndex)
                ));

            }
        } finally {
            cursor.close();
        }*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         /*Define a projection that specifies which columnts from database we will use*/
        String[] projection = {
                MarksContract.MarksEntry._ID,
                MarksContract.MarksEntry.COLUMN_MARK_TITLE,
                MarksContract.MarksEntry.COLUMN_MY_MARK,
                MarksContract.MarksEntry.COLUMN_LOWER_MARK,
                MarksContract.MarksEntry.COLUMN_AVEREGE_MARK,
                MarksContract.MarksEntry.COLUMN_HIGHER_MARK,
                MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS};

        //This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                MarksContract.MarksEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //CursorAdapet.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //CursorAdapter.swapCursor(null);
    }
}
