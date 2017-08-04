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

import com.example.maciek.eresviewer.data.MarksContract;
import com.example.maciek.eresviewer.data.MarksContract.MarksEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MARK_LOADER = 0;
    private EditText mMarkTitleEditText;
    private EditText mMyMarkEditText;
    private EditText mMarkMinEditText;
    private EditText mMarkAvgEditText;
    private EditText mMarkMaxEditText;
    private EditText mAmountOfMarksEditText;
    private Uri mCurrentMarkUri;

    private boolean changed = false;

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

        Intent intent = getIntent();
        Uri currentMarkUri = intent.getData();
        mCurrentMarkUri = currentMarkUri;
        if (currentMarkUri == null) {
            setTitle("Dodaj ocenę");
            invalidateOptionsMenu();
        } else {
            setTitle("Edycja oceny");
            //Start the loader
            getLoaderManager().initLoader(MARK_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mMarkTitleEditText = (EditText) findViewById(R.id.edit_mark_title);
        mMyMarkEditText = (EditText) findViewById(R.id.edit_my_mark);
        mMarkMinEditText = (EditText) findViewById(R.id.edit_mark_min);
        mMarkAvgEditText = (EditText) findViewById(R.id.edit_mark_averenge);
        mMarkMaxEditText = (EditText) findViewById(R.id.edit_mark_max);
        mAmountOfMarksEditText = (EditText) findViewById(R.id.edit_amount_of_marks);

        mMarkTitleEditText.setOnTouchListener(mTouchListener);
        mMyMarkEditText.setOnTouchListener(mTouchListener);
        mMarkMinEditText.setOnTouchListener(mTouchListener);
        mMarkAvgEditText.setOnTouchListener(mTouchListener);
        mMarkMaxEditText.setOnTouchListener(mTouchListener);
        mAmountOfMarksEditText.setOnTouchListener(mTouchListener);
    }

    private void saveMark() {
        String markTitle = mMarkTitleEditText.getText().toString().trim();
        String myMark = mMyMarkEditText.getText().toString().trim();
        String markMin = mMarkMinEditText.getText().toString().trim();
        String markAvg = mMarkAvgEditText.getText().toString().trim();
        String markMax = mMarkMaxEditText.getText().toString().trim();
        String amountOfMarks = mAmountOfMarksEditText.getText().toString().trim();

        if (TextUtils.isEmpty(markTitle) &&
                TextUtils.isEmpty(myMark) &&
                TextUtils.isEmpty(markMin) &&
                TextUtils.isEmpty(markAvg) &&
                TextUtils.isEmpty(markMax) &&
                TextUtils.isEmpty(amountOfMarks))
            return;


        ContentValues values = new ContentValues();

        if (!TextUtils.isEmpty(markTitle))
            values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, markTitle);
        else values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "");

        if (!TextUtils.isEmpty(myMark))
            values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, (int) (Float.parseFloat(myMark) * 100));
        else values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 0);

        if (!TextUtils.isEmpty(markMin))
            values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, (int) (Float.parseFloat(markMin) * 100));
        else values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 0);

        if (!TextUtils.isEmpty(markAvg))
            values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, (int) (Float.parseFloat(markAvg) * 100));
        else values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 0);

        if (!TextUtils.isEmpty(markMax))
            values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, (int) (Float.parseFloat(markMax) * 100));
        else values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 0);

        if (!TextUtils.isEmpty(amountOfMarks))
            values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, amountOfMarks);
        else values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

        if (mCurrentMarkUri == null) {
            Uri newUri = getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);

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
        } else {
            int updatedIndex = getContentResolver().update(mCurrentMarkUri, values, null, null);
            // Show a toast message depending on whether or not the insertion was successful
            if (updatedIndex == 0) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Edycja nie powiodła się",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Zedytowałeś jak król",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
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
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
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
                mCurrentMarkUri,
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
        int rowsDeleted = getContentResolver().delete(mCurrentMarkUri, null, null);

        // Show a toast message depending on whether or not the insertion was successful
        if (rowsDeleted == 0) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "Coś się nie usunęło",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
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
        if (mCurrentMarkUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }


}
