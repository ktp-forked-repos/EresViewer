package com.example.maciek.eresviewer;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.ArrayAdapter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Maciek on 19.07.2017.
 */
public class RefreshSubjectTask extends downloadHTMLPageTask<String, Void, Subject>{

    private SubjectActivity refreshedActivity;

    public RefreshSubjectTask(SubjectActivity activity){
        super(activity);
        refreshedActivity=activity;
    }
    protected Subject doInBackground(String... str){
        Document downloadedPage=this.downloadHTMLPage(str[0]);
        return createSubject(downloadedPage, str[1]);
    }
    protected Subject createSubject(Document document, String subjectName){
        Elements titlesOfActivities=document.select("[class=nagl]").select("[colspan]");
        Elements subjectMarks=document.select("[class=zaw0],[class=zaw1]");
        ArrayList<String>titles=new ArrayList<>();
        titles.addAll(titlesOfActivities.eachText());
        titles.remove(0);
        ArrayList<String> marks=new ArrayList<>();
        fillMissingFields(subjectMarks);
        marks.addAll(subjectMarks.eachText());


        Subject createdSubject=new Subject(subjectName);
        for(int i=3; i<titles.size(); i++){
            int j=5*i;
            Mark mark=new Mark(titles.get(i), Float.parseFloat(marks.get(j)),Float.parseFloat(marks.get(j+1)),
                    Float.parseFloat(marks.get(j+2)),Float.parseFloat(marks.get(j+3)),Integer.parseInt(marks.get(j+4)));
            createdSubject.addMark(mark);
        }
        return createdSubject;
    }
    @Override
    protected void onPostExecute(Subject sub){
        //refreshedActivity.refresh(sub);
    }

    private void fillMissingFields(Elements marks){
        Element e;
        for(int i=0; i<marks.size(); i++) {
            e = marks.get(i);
            if (e.toString().contains("&nbsp;"))
                e.text("-1");
        }
    }

}
