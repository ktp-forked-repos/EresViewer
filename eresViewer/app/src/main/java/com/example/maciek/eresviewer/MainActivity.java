package com.example.maciek.eresviewer;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.maciek.eresviewer.data.MarksContract;

import java.io.DataOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> subjects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView marks=(TextView)findViewById(R.id.marksView);
        //connectWithEres();
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marksIntent = new Intent(MainActivity.this, SubjectActivity.class);
                startActivity(marksIntent);
            }
        });

        TextView add = (TextView) findViewById(R.id.addView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });

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

    }

    public void connectWithEres() {
        try {
            URL url = new URL("https://studia.elka.pw.edu.pl/");

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            int responseCode = connection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
