package com.example.maciek.eresviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        //Todo: usunac te linie potem
        Preferences.removeCredentials(this);
    /*    if (!checkIfTheUserIsSignedIn()) {
=======
        //ArrayList<String> subjects = new ArrayList<>();
        TextView marks = (TextView) findViewById(R.id.marksView);


        if (!checkIfTheUserIsSignedIn()) {
>>>>>>> master
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }*/

        //ArrayList<String> subjects = new ArrayList<>();
        TextView marks = (TextView) findViewById(R.id.marksView);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marksIntent = new Intent(MainActivity.this, SubjectActivity.class);
                startActivity(marksIntent);
            }
        });

        TextView config = (TextView) findViewById(R.id.configView);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(configIntent);
            }
        });
<<<<<<< HEAD
=======
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
>>>>>>> master
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
