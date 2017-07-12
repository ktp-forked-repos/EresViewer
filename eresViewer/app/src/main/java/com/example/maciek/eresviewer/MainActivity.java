package com.example.maciek.eresviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ArrayList<SubjectActivity> subjectsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView marks=(TextView)findViewById(R.id.marksView);

        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marksIntent = new Intent(MainActivity.this, SubjectActivity.class);

                startActivity(marksIntent);


            }
        });
       /* connectWithEres(); */
        URL url=new URL("https://studia.elka.pw.edu.pl/");
        new SynchronizeWithEresTask().execute("https://studia.elka.pw.edu.pl/",1, subjectsList);
    }


}
