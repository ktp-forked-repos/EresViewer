package com.example.maciek.eresviewer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maciek.eresviewer.data.MarksContract;

public class EditorActivity extends AppCompatActivity {

    private EditText mMarkTitleEditText;
    private EditText mMyMarkEditText;
    private EditText mMarkMinEditText;
    private EditText mMarkAvgEditText;
    private EditText mMarkMaxEditText;
    private EditText mAmountOfMarksEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mMarkTitleEditText = (EditText) findViewById(R.id.edit_mark_title);
        mMyMarkEditText = (EditText) findViewById(R.id.edit_my_mark);
        mMarkMinEditText = (EditText) findViewById(R.id.edit_mark_min);
        mMarkAvgEditText = (EditText) findViewById(R.id.edit_mark_averenge);
        mMarkMaxEditText = (EditText) findViewById(R.id.edit_mark_max);
        mAmountOfMarksEditText = (EditText) findViewById(R.id.edit_amount_of_marks);
    }

    private void insertMark(){
        String markTitle = mMarkTitleEditText.getText().toString().trim();
        float myMark = Float.parseFloat(mMyMarkEditText.getText().toString().trim());
        float markMin = Float.parseFloat(mMarkMinEditText.getText().toString().trim());
        float markAvg = Float.parseFloat(mMarkAvgEditText.getText().toString().trim());
        float markMax = Float.parseFloat(mMarkMaxEditText.getText().toString().trim());
        int amountOfMarks = Integer.parseInt(mAmountOfMarksEditText.getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE,markTitle);
        values.put(MarksContract.MarksEntry.COLUMN_MY_MARK,(int)(myMark*100));
        values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK,(int)(markMin*100));
        values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK,(int)(markAvg*100));
        values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK,(int)(markMax*100));
        values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS,amountOfMarks);

        Uri newUri = getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI,values);

        getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI,values);

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
                insertMark();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
