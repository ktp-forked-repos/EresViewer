package com.example.maciek.eresviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciek on 09.07.2017.
 */

public class SubjectActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter markAdapter;
    private List<Mark> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_subject);
        markAdapter = new ExpandableListAdapter(this, marks);
        listView = (ExpandableListView) findViewById(R.id.listview_mark);
        listView.setAdapter(markAdapter);
    }

    private void initData() {
        marks = new ArrayList<>();
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
    }
}
