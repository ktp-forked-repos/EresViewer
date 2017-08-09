package com.example.maciek.eresviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity{
    private ArrayList<String> subjectsList;
    private Spinner s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> subjects=new ArrayList<>();
        TextView marks=(TextView)findViewById(R.id.marksView);
        //Todo: usunac te linie potem
        Preferences.removeCredentials(this);
        if(!checkIfTheUserIsSignedIn()){
            Intent loginIntent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }




       //new SynchronizeSubjectsWithEres(this).execute(subjects);

        //Odtąd do wycięcia po dodaniu przedmiotów do bazy
        /*
        // dodawanie przedmiotów do obiektu typu Spinner
        s=(Spinner) findViewById(R.id.subjects_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,subjects);
        subjects.add(0, "no selection");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //dodawanie zdarzenia otwarcia karty przedmiotu po kliknięciu
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(!parent.getSelectedItem().toString().equals("no selection")) {
                        Intent subjectIntent = new Intent(MainActivity.this, SubjectActivity.class);
                        subjectIntent.putExtra("subject name", parent.getSelectedItem().toString());
                        startActivity(subjectIntent);
                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });

        //odświeżanie przedmiotu
        //rst.addListeners(this);

        //TUTAJ SKONCZYC WYCINANIE
        */
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marksIntent = new Intent(MainActivity.this, SubjectActivity.class);
                startActivity(marksIntent);
            }
        });


        TextView add=(TextView)findViewById(R.id.addView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });
    }

    private Boolean checkIfTheUserIsSignedIn() {
        if(Preferences.isLoggedIn(this))
            return true;
        else
            return false;

    }


}
