package com.example.maciek.eresviewer;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maciek.eresviewer.data.MarksContract.MarksEntry;


/**
 * Created by Maciek on 09.07.2017.
 */

public class SubjectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Identifies loader being used in this component
    private static final int MARK_LOADER = 0;
    //Cursor adapter object creating list of marks from database cursors
    MarkCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        //Find the ListView which will be populated with the data
        ListView listView = (ListView) findViewById(R.id.listview_mark);
        // Creating cursor adapter taking this activity as context and a null cursor
        mCursorAdapter = new MarkCursorAdapter(this, null);
        //Attaching adapter to the listView
        listView.setAdapter(mCursorAdapter);
        //Adding onItemClickListener so items of the list will expand when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View rozwijane = view.findViewById(R.id.details);
                //Expanding list elements by hiding part of it
                if (rozwijane.getVisibility() == View.GONE) {
                    rozwijane.setVisibility(View.VISIBLE);
                }
                else {
                    //expandedChildList.set(arg2, false);
                    rozwijane.setVisibility(View.GONE);
                }
            }
        });
        //Adding onItemLongClickLisener so long pressing list item sends intent to editor activity
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editorIntent = new Intent(SubjectActivity.this, EditorActivity.class);
                //Appending id of long-pressed item to database URI
                Uri currentMarkUri = ContentUris.withAppendedId(MarksEntry.CONTENT_URI, id);
                editorIntent.setData(currentMarkUri);
                startActivity(editorIntent);
                return true;
            }
        });

        //Start the loader
        getLoaderManager().initLoader(MARK_LOADER, null, this);

    }
    /*Using a loader*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         /*Define a projection that specifies which columns from database we will use*/
        String[] projection = {
                MarksEntry._ID,
                MarksEntry.COLUMN_MARK_TITLE,
                MarksEntry.COLUMN_MY_MARK,
                MarksEntry.COLUMN_LOWER_MARK,
                MarksEntry.COLUMN_AVEREGE_MARK,
                MarksEntry.COLUMN_HIGHER_MARK,
                MarksEntry.COLUMN_AMOUNT_OF_MARKS};

        //This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                MarksEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
