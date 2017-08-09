package com.example.maciek.eresviewer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.maciek.eresviewer.data.MarksContract;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Identifies loader being used in this component
    private static final int MARK_LOADER = 0;
    //Cursor adapter object creating list of marks from database cursors
    MarkCursorAdapter mCursorAdapter;

    ArrayList<SubjectFragment> subjectFragments = new ArrayList<SubjectFragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        createSubjects();

        RefreshSubjectTask rst= new RefreshSubjectTask(this);
        String[] dataForConnection={"https://studia.elka.pw.edu.pl/pl/17L/"+getSubjectName()+"/info/",
                getSubjectName()};
        rst.execute(dataForConnection);

        /*Creates toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Creates floating action button, sending snackbar message*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Kliknaleś guziczek", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        /*Creates action bar toggle*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*Creates view, sliding from left after clicking action bar toggle*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, subjectFragments.get(0))
                .commit();

    }
    private String getSubjectName(){
        Intent intent=getIntent();
        return intent.getStringExtra("subject name");
    }

    /*Drawer behaviour after pressing back key*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Creating options menu
     *
     * @param menu Activity menu object
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navView.getMenu();
        SubMenu subjects_menu = m.addSubMenu("Przedmioty");
        subjects_menu.setGroupCheckable(0, true, true);
        for (String str : MainActivity.subjects) {
            subjects_menu.add(0, MainActivity.subjects.indexOf(str), 0, str).setCheckable(true);
        };
        //subjects_menu.getItem(0).setChecked(true);
        return true;
    }


    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String subject_title = item.toString();
        int id = item.getItemId();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, subjectFragments.get(id))
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createSubjects() {
        String name;

        for (String str : MainActivity.subjects) {
            Bundle args = new Bundle();
            name = str.substring(0, str.indexOf('.'));
            args.putString("name", name);

            SubjectFragment fragment = new SubjectFragment();
            fragment.setArguments(args);

            subjectFragments.add(fragment);

           /* ContentValues values = new ContentValues();
            values.put(MarksContract.MarksEntry.COLUMN_SUBJECT, name);
            values.put(MarksContract.MarksEntry.COLUMN_MARK_TITLE, "Test: " + str);
            values.put(MarksContract.MarksEntry.COLUMN_MY_MARK, 0);
            values.put(MarksContract.MarksEntry.COLUMN_LOWER_MARK, 0);
            values.put(MarksContract.MarksEntry.COLUMN_AVEREGE_MARK, 0);
            values.put(MarksContract.MarksEntry.COLUMN_HIGHER_MARK, 0);
            values.put(MarksContract.MarksEntry.COLUMN_AMOUNT_OF_MARKS, 0);

            getContentResolver().insert(MarksContract.MarksEntry.CONTENT_URI, values);*/
        }
    }

    public void refresh(Subject sub) {
        for(Mark mark : sub.getMarks())
            marks.add(mark);
        markAdapter.notifyDataSetChanged();
    }
}
