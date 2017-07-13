package com.example.maciek.eresviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maciek on 09.07.2017.
 */

public class SubjectActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter markAdapter;
    private List<Mark> marks;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
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

*/
        initData();

        setContentView(R.layout.activity_subject);

        // MarkAdapter markAdapter=new MarkAdapter(this,marks);
        markAdapter = new ExpandableListAdapter(this, marks, listHash);
        // ListView listView=(ListView) findViewById(R.id.listview_mark);
        listView = (ExpandableListView) findViewById(R.id.listview_mark);
        // listView.setAdapter(markAdapter);
        listView.setAdapter(markAdapter);

    }

    private void initData() {
        marks = new ArrayList<>();
        listHash = new HashMap<>();

        marks.add(new Mark("Kolokwium 1", 5,5,5,5,50));
        marks.add(new Mark("Kolokwium 2", 5,5,5,5,51));
        marks.add(new Mark("Kolokwium 3", 5,5,5,5,52));

        List<String> jeden = new ArrayList<>();
        jeden.add("This is expandable listView");

        List<String> dwa = new ArrayList<>();
        dwa.add("Rozszerzamy");
        dwa.add("ile");
        dwa.add("chcemy");

        List<String> trzy = new ArrayList<>();
        trzy.add("Rozszerzamy");
        trzy.add("ile");
        trzy.add("chcemy");

        listHash.put(marks.get(0).getMarkTitle(), jeden);
        listHash.put(marks.get(1).getMarkTitle(), dwa);
        listHash.put(marks.get(2).getMarkTitle(), trzy);

    }
}
