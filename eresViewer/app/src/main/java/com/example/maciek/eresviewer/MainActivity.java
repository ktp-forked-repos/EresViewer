package com.example.maciek.eresviewer;

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

        //Todo: usunac te linie potem
        Preferences.removeCredentials(this);
    /*    if (!checkIfTheUserIsSignedIn()) {
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
    }

    private Boolean checkIfTheUserIsSignedIn() {
        if (Preferences.isLoggedIn(this))
            return true;
        else
            return false;

    }

}
