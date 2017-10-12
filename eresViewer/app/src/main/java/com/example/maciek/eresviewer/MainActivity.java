package com.example.maciek.eresviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> subjects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayList<String> subjects = new ArrayList<>();
        TextView marks = (TextView) findViewById(R.id.marksView);


        if (!checkIfTheUserIsSignedIn()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

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
        final Activity act=this;
        TextView clearCredentials=(TextView)findViewById(R.id.clearCredentialsView);
        clearCredentials.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Preferences.removeCredentials(act);
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
    @Override
    public void onResume(){
        super.onResume();
        if (!checkIfTheUserIsSignedIn()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    private Boolean checkIfTheUserIsSignedIn() {
        if (Preferences.isLoggedIn(this))
            return true;
        else
            return false;

    }

}
