package com.example.maciek.eresviewer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maciek.eresviewer.data.MarksContract;

import java.util.ArrayList;

public class SubjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //Identifies loader being used in this component
    //private static final int MARK_LOADER = 0;
    //Cursor adapter object creating list of marks from database cursors
    //MarkCursorAdapter mCursorAdapter;

    private Subject subject;
    private Uri contentUri;
    private String subjectName;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<Mark> marks_downloaded = new ArrayList<Mark>();

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectName = getArguments().getString("name");
        subject = new Subject(subjectName, getContext());

        //Fejkowe pobrane z serwera oceny
        marks_downloaded.add(new Mark("Test 1",1,1,1,1,1));
        marks_downloaded.add(new Mark("Tost",2,2,2,2,2));
        marks_downloaded.add(new Mark("Test 3",3,3,3,3,3));
        marks_downloaded.add(new Mark("Chleb",3,0,0,0,0));

        //createMarksFromDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_subject, container, false);
        //Find the ListView which will be populated with the data
        ListView listView = (ListView) rootView.findViewById(R.id.listview_mark);

        // Creating cursor adapter taking this activity as context and a null cursor
        // mCursorAdapter = new MarkCursorAdapter(getActivity(), null);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        contentUri = Uri.withAppendedPath(MarksContract.BASE_CONTENT_URI, MarksContract.PATH_MARKS);



        /*Attaching adapter to the listView*/
        // listView.setAdapter(mCursorAdapter);
        listView.setAdapter(subject.markAdapter);

        //Adding onItemClickListener so items of the list will expand when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View rozwijane = view.findViewById(R.id.details);
                //Expanding list elements by hiding part of it
                if (rozwijane.getVisibility() == View.GONE) {
                    rozwijane.setVisibility(View.VISIBLE);
                } else {
                    //expandedChildList.set(arg2, false);
                    rozwijane.setVisibility(View.GONE);
                }
            }
        });

        //Adding onItemLongClickLisener so long pressing list item sends intent to editor activity
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editorIntent = new Intent(getActivity(), EditorActivity.class);
                //Appending id of long-pressed item to database URI
                //TODO: That doesn't work this way!
                Uri currentMarkUri = ContentUris.withAppendedId(MarksContract.MarksEntry.CONTENT_URI, id);
                editorIntent.setData(currentMarkUri);
                editorIntent.putExtra("subjectTitle", subjectName);
                startActivity(editorIntent);
                return true;
            }
        });

        //Start the loader
        // getLoaderManager().initLoader(MARK_LOADER, null, this);

        /*
        * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        * performs a swipe-to-refresh gesture.
         */

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                      /*  Toast toast = Toast.makeText(getContext(), "Odświeżanko", Toast.LENGTH_SHORT);
                        toast.show();
                        ContentValues values = new ContentValues();
                        values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, subjectName);
                        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "Refresh");
                        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 100);
                        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 200);
                        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 300);
                        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 400);
                        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 5);

                        getActivity().getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);*/

                        //TODO: porównanie listy ocen przedmiotu z nowo pobranymi ocenami

                        subject.compareDownloadedMarks(marks_downloaded);


                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return rootView;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public void onRefresh() {

    }

}
    /* method calls setRefreshing(false) when it has finished updating the data.
    * Calling this method instructs the SwipeRefreshLayout to remove the progress
    * indicator and update the view contents.*/

   /* *//*Using a loader*//*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         *//*Define a projection that specifies which columns from database we will use*//*
        String[] projection = {
                MarksContract.MarksEntry.COLUMN_SUBJECT,
                MarksContract.MarksEntry._ID,
                MarksContract.MarksEntry.COLUMN_MARK_TITLE,
                MarksContract.MarksEntry.COLUMN_MY_MARK,
                MarksContract.MarksEntry.COLUMN_LOWER_MARK,
                MarksContract.MarksEntry.COLUMN_AVEREGE_MARK,
                MarksContract.MarksEntry.COLUMN_HIGHER_MARK,
                MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS};

        String selection = "subject = '" + subjectName + "';";

        //This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(),
                contentUri,
                projection,
                selection,
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
*/