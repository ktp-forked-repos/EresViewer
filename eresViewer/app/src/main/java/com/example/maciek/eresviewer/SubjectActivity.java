package com.example.maciek.eresviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Maciek on 09.07.2017.
 */

public class SubjectActivity extends AppCompatActivity {

    public ArrayList<Mark> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        marks=new ArrayList<>();
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));
        marks.add(new Mark("Kolokwium1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium3", 5,5,5,5,52));


        setContentView(R.layout.activity_subject);

        MarkAdapter markAdapter=new MarkAdapter(this,marks);

        ListView listView=(ListView) findViewById(R.id.listview_mark);
        listView.setAdapter(markAdapter);


    }
}
