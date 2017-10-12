package com.example.maciek.eresviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<SubjectFragment> fragmentsList = new ArrayList<>();
    ArrayList<String> subjects = new ArrayList<String>();
    int activeSubjectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        /*Lista przedmiotów wpisana z ręki*/
        subjects.add("CYPS.B");
        subjects.add("ELIU.B");
        subjects.add("ELIUL.B");
        subjects.add("FOT.A");
        subjects.add("JAP3.A");
        subjects.add("LPTC.A");
        subjects.add("PR.B");
        subjects.add("PROZE.A");
        subjects.add("PTC.B");
        subjects.add("RDC.A");
        subjects.add("TINE.A");
        subjects.add("WF4.A");

        /*Populate empty list of SubjectFragments*/
        createFragments();

       /* GetMarksTask rst = new GetMarksTask(this);
        String[] dataForConnection = {"https://studia.elka.pw.edu.pl/pl/17L/" + getSubjectName() + "/info/",
                getSubjectName()};
        rst.execute(dataForConnection);*/

        /*Creates toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Creates floating action button*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editorIntent = new Intent(SubjectActivity.this, EditorActivity.class);
                editorIntent.putExtra("subjectTitle", fragmentsList.get(activeSubjectIndex).getSubject().getShortSubjectName());
                startActivity(editorIntent);
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

        /*Display default fragment*/
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsList.get(activeSubjectIndex))
                .commit();
    }

    private void createFragments() {
        for (String str : subjects) {
            //Subject subject = new Subject(str, getApplicationContext());
            SubjectFragment fragment = new SubjectFragment();

            /*Way of constructing custom fragment*/
            Bundle args = new Bundle();
            args.putString("name", str);
            fragment.setArguments(args);

            //subject.createTestMark();
            fragmentsList.add(fragment);
        }
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
        for (String str : subjects) {
            subjects_menu.add(0, subjects.indexOf(str), 0, str).setCheckable(true);
        }
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
        activeSubjectIndex = item.getItemId();

        /*Display fragment represented by clicked navigation menu item*/
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsList.get(activeSubjectIndex))
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
