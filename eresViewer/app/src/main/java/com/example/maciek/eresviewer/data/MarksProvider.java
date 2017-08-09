package com.example.maciek.eresviewer.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.maciek.eresviewer.data.MarksContract.MarksEntry;

/**
 * Created by Adrian on 2017-07-15.
 */

public class MarksProvider extends ContentProvider {

    /**
     * URI matcher code for the content URI for a marks table
     */
    public static final int MARKS = 10;
    /**
     * URI matcher code for the content URI for a single mark in the table
     */
    public static final int MARK_ID = 11;
    /**
     * URI matcher object to match a context URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(MarksContract.CONTENT_AUTHORITY, MarksContract.PATH_MARKS, MARKS);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PETS_ID}. This URI is used to provide access to ONE single row
        // of the pets table.

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(MarksContract.CONTENT_AUTHORITY, MarksContract.PATH_MARKS + "/#", MARK_ID);
    }

    private MarksDbHelper mDbHelper;

    @Override
    public boolean onCreate() {

        mDbHelper = new MarksDbHelper(getContext());

        // ContentProvider methods.
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MARKS:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                cursor = database.query(MarksEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MARK_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = MarksEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(MarksEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        //Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MARKS:

                String name = contentValues.getAsString(MarksEntry.COLUMN_MARK_TITLE);
                if (name == null) {
                    throw new IllegalArgumentException("Mark needs a title!");
                }

                // TODO: Finish sanity checking the rest of the attributes in ContentValues


                // Get writeable database
                SQLiteDatabase database = mDbHelper.getWritableDatabase();
                long id = database.insert(MarksEntry.TABLE_NAME, null, contentValues);
                // If the ID is -1, then the insertion failed. Log an error and return null.
                if (id == -1) {
                    //Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }
                //Notify all listeners that the data has changed for the mark content URI
                // uri: content://com.example.maciek.eresviewer/marks
                getContext().getContentResolver().notifyChange(uri, null);


                // Return the new URI with the ID (of the newly inserted row) appended at the end
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MARKS:
                return updateMark(uri, contentValues, selection, selectionArgs);
            case MARK_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MarksEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return updateMark(uri, contentValues, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateMark(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // TODO: Finish sanity checking the rest of the attributes in ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(MarksEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case MARKS:
                rowsDeleted = database.delete(MarksEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    //Notify all listeners that the data has changed for the mark content URI
                    // uri: content://com.example.maciek.eresviewer/marks
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                // Delete all rows that match the selection and selection args
                return rowsDeleted;
            case MARK_ID:
                // Delete a single row given by the ID in the URI
                selection = MarksEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MarksEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    //Notify all listeners that the data has changed for the mark content URI
                    // uri: content://com.example.maciek.eresviewer/marks
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MARKS:
                return MarksEntry.CONTENT_LIST_TYPE;
            case MARK_ID:
                return MarksEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
