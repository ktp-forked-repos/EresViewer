package com.example.maciek.eresviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

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

        TextView add=(TextView)findViewById(R.id.addView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });

    }

    public void connectWithEres(){
        try {
            URL url = new URL("https://studia.elka.pw.edu.pl/");

            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            int responseCode=connection.getResponseCode();
          } catch (MalformedURLException e) {
        e.printStackTrace();
         }
        catch (java.io.IOException e) {
         e.printStackTrace();
        }

    }
}
