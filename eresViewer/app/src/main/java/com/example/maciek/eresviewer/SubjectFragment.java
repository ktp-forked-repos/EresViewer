package com.example.maciek.eresviewer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maciek.eresviewer.data.MarksContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SubjectFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //Identifies loader being used in this component
    private static final int MARK_LOADER = 0;
    //Cursor adapter object creating list of marks from database cursors
    MarkCursorAdapter mCursorAdapter;
    private Uri contentUri;

    private String subjectName;

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_subject, container, false);
        //Find the ListView which will be populated with the data
        ListView listView = (ListView) rootView.findViewById(R.id.listview_mark);

        // Creating cursor adapter taking this activity as context and a null cursor
        mCursorAdapter = new MarkCursorAdapter(getActivity(), null);

        subjectName = getArguments().getString("name");
        contentUri = Uri.withAppendedPath(MarksContract.BASE_CONTENT_URI, MarksContract.PATH_MARKS);

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
                Uri currentMarkUri = ContentUris.withAppendedId(MarksContract.MarksEntry.CONTENT_URI, id);
                editorIntent.setData(currentMarkUri);
                startActivity(editorIntent);
                return true;
            }
        });

        //Start the loader
        getLoaderManager().initLoader(MARK_LOADER, null, this);

        return rootView;
    }

    /*Using a loader*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         /*Define a projection that specifies which columns from database we will use*/
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
