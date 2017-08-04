package com.example.maciek.eresviewer;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maciek.eresviewer.data.MarksContract.MarksEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Identifies loader being used in this component
    private static final int MARK_LOADER = 0;

    private EditText mMarkTitleEditText, mMyMarkEditText, mMarkMinEditText, mMarkAvgEditText, mMarkMaxEditText, mAmountOfMarksEditText;
    private Uri currentMarkUri;

    //Flag saying if any of EditText fields has been touched by user
    private boolean changed = false;
    //Listener changing @changed flag
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            changed = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        /*Getting intent sent to EditorActivity*/
        Intent intent = getIntent();
        /*Geting id data sent in intent*/
        currentMarkUri = intent.getData();
        /*EditorActivity has two modes, launched depending on whether any data got extracted from an intent.
        * If currnetMarkUri is null, it means intent has been sent without addidtional id info.*/
        if (currentMarkUri == null) {
            setTitle("Dodaj ocenę");
            invalidateOptionsMenu();
        } else {
            setTitle("Edycja oceny");
            getLoaderManager().initLoader(MARK_LOADER, null, this);
        }

        /*Find all relevant views that we will need to read user input from*/
        mMarkTitleEditText = (EditText) findViewById(R.id.edit_mark_title);
        mMyMarkEditText = (EditText) findViewById(R.id.edit_my_mark);
        mMarkMinEditText = (EditText) findViewById(R.id.edit_mark_min);
        mMarkAvgEditText = (EditText) findViewById(R.id.edit_mark_averenge);
        mMarkMaxEditText = (EditText) findViewById(R.id.edit_mark_max);
        mAmountOfMarksEditText = (EditText) findViewById(R.id.edit_amount_of_marks);

        /*Every EditText field gets a TouchListener to change flag status when user touched any text field*/
        mMarkTitleEditText.setOnTouchListener(mTouchListener);
        mMyMarkEditText.setOnTouchListener(mTouchListener);
        mMarkMinEditText.setOnTouchListener(mTouchListener);
        mMarkAvgEditText.setOnTouchListener(mTouchListener);
        mMarkMaxEditText.setOnTouchListener(mTouchListener);
        mAmountOfMarksEditText.setOnTouchListener(mTouchListener);
    }

    /**
     * Mark is added or edited
     */
    private void saveMark() {
        String markTitle = mMarkTitleEditText.getText().toString().trim();
        String myMark = mMyMarkEditText.getText().toString().trim();
        String markMin = mMarkMinEditText.getText().toString().trim();
        String markAvg = mMarkAvgEditText.getText().toString().trim();
        String markMax = mMarkMaxEditText.getText().toString().trim();
        String amountOfMarks = mAmountOfMarksEditText.getText().toString().trim();

        /*If all text fields remain empty, no action is taken*/
        if (TextUtils.isEmpty(markTitle) &&
                TextUtils.isEmpty(myMark) &&
                TextUtils.isEmpty(markMin) &&
                TextUtils.isEmpty(markAvg) &&
                TextUtils.isEmpty(markMax) &&
                TextUtils.isEmpty(amountOfMarks))
            return;
        /*Creating new ContentValues object, which will be used as parameter of ContentResolver's insert method*/
        ContentValues values = new ContentValues();

        /*If text field is empty, default value is put into ContentValues object*/
        if (!TextUtils.isEmpty(markTitle))
            values.put(MarksEntry.COLUMN_MARK_TITLE, markTitle);
        else values.put(MarksEntry.COLUMN_MARK_TITLE, "");
        if (!TextUtils.isEmpty(myMark))
            values.put(MarksEntry.COLUMN_MY_MARK, (int) (Float.parseFloat(myMark) * 100));
        else values.put(MarksEntry.COLUMN_MY_MARK, 0);
        if (!TextUtils.isEmpty(markMin))
            values.put(MarksEntry.COLUMN_LOWER_MARK, (int) (Float.parseFloat(markMin) * 100));
        else values.put(MarksEntry.COLUMN_LOWER_MARK, 0);
        if (!TextUtils.isEmpty(markAvg))
            values.put(MarksEntry.COLUMN_AVEREGE_MARK, (int) (Float.parseFloat(markAvg) * 100));
        else values.put(MarksEntry.COLUMN_AVEREGE_MARK, 0);
        if (!TextUtils.isEmpty(markMax))
            values.put(MarksEntry.COLUMN_HIGHER_MARK, (int) (Float.parseFloat(markMax) * 100));
        else values.put(MarksEntry.COLUMN_HIGHER_MARK, 0);
        if (!TextUtils.isEmpty(amountOfMarks))
            values.put(MarksEntry.COLUMN_AMOUNT_OF_MARKS, amountOfMarks);
        else values.put(MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

        // Adding new mark
        if (currentMarkUri == null) {
            Uri newUri = getContentResolver().insert(MarksEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
            // Editing mark
        } else {
            int updatedIndex = getContentResolver().update(currentMarkUri, values, null, null);
            // Show a toast message depending on whether or not the insertion was successful
            if (updatedIndex == 0) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Edycja nie powiodła się", //TODO: don't hardcode strings
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Zedytowałeś jak król", //TODO: don't hardcode strings
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save mark to database
                saveMark();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                if (!changed) {
                    /*If no changes took place home button navgates up*/
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }
                 /*Otherwise if there are unsaved changes, setup a dialog to warn the user.
                 Create a click listener to handle the user confirming that
                 changes should be discarded.*/
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };
                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.
                onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         /*Define a projection that specifies which columnts from database we will use*/
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
                currentMarkUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String title = data.getString(data.getColumnIndex(MarksEntry.COLUMN_MARK_TITLE));
            float myMark = data.getFloat(data.getColumnIndex(MarksEntry.COLUMN_MY_MARK)) / 100;
            float markMin = data.getFloat(data.getColumnIndex(MarksEntry.COLUMN_LOWER_MARK)) / 100;
            float markAvg = data.getFloat(data.getColumnIndex(MarksEntry.COLUMN_AVEREGE_MARK)) / 100;
            float markMax = data.getFloat(data.getColumnIndex(MarksEntry.COLUMN_HIGHER_MARK)) / 100;
            int amountOfMarks = data.getInt(data.getColumnIndex(MarksEntry.COLUMN_AMOUNT_OF_MARKS));

            mMarkTitleEditText.setText(title);
            mMyMarkEditText.setText(Float.toString(myMark));
            mMarkMinEditText.setText(Float.toString(markMin));
            mMarkAvgEditText.setText(Float.toString(markAvg));
            mMarkMaxEditText.setText(Float.toString(markMax));
            mAmountOfMarksEditText.setText(Integer.toString(amountOfMarks));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMarkTitleEditText.setText("");
        mMyMarkEditText.setText("");
        mMarkMinEditText.setText("");
        mMarkAvgEditText.setText("");
        mMarkMaxEditText.setText("");
        mAmountOfMarksEditText.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Na pewno chcesz wyjść stąd?");
        builder.setPositiveButton("Uwolnij mnie", discardButtonClickListener);
        builder.setNegativeButton("Zostaję", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Usuwanko?");
        builder.setPositiveButton("Jeszcze jak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteMark();
            }
        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteMark() {
        int rowsDeleted = getContentResolver().delete(currentMarkUri, null, null);

        // Show a toast message depending on whether or not the deletion was successful
        if (rowsDeleted == 0) {
            // If the new content URI is null, then there was an error with deletion.
            Toast.makeText(this, "Coś się nie usunęło",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the deletion was successful and we can display a toast.
            Toast.makeText(this, "łokieć, pięta, nie ma klienta",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!changed) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (currentMarkUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }


}
