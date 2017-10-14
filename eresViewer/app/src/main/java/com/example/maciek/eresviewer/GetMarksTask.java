package com.example.maciek.eresviewer;

import android.content.Context;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Maciek on 19.07.2017.
 */
public class GetMarksTask extends downloadHTMLPageTask<ArrayList<Mark>, Void, Void>{

    //private SubjectActivity refreshedActivity;
    private String page;
    private String subjectName;

    public GetMarksTask(String page, String subjectName, Context context){
        super(context);
        this.page=page;
        this.subjectName=subjectName;
    }
    protected Void doInBackground(ArrayList<Mark>... marks){
        Document downloadedPage=this.downloadHTMLPage(page);
        extractMarks(marks[0],downloadedPage, subjectName);
        return null;
    }
    protected void extractMarks(ArrayList<Mark>marks,Document document, String subjectName){
        Elements titlesOfActivities=document.select("[class=nagl]").select("[colspan]");
        Elements subjectMarks=document.select("[class=zaw0],[class=zaw1]");
        ArrayList<String>titles=new ArrayList<>();
        titles.addAll(titlesOfActivities.eachText());
        titles.remove(0);
        ArrayList<String> allMarks=new ArrayList<>();
        fillMissingFields(subjectMarks);
        allMarks.addAll(subjectMarks.eachText());
        for(int i=3; i<titles.size(); i++) {
            int j = 5 * i;
            Mark mark = new Mark(0,titles.get(i), Float.parseFloat(allMarks.get(j)), Float.parseFloat(allMarks.get(j + 1)),
                    Float.parseFloat(allMarks.get(j + 2)), Float.parseFloat(allMarks.get(j + 3)), Integer.parseInt(allMarks.get(j + 4)));
            marks.add(mark);
        }
    }
    protected void onPostExecute(Subject sub){
        //refreshedActivity.refresh(sub);
        //TODO: Powiadomienie SubjectFragment o zakończeniu asynctaska
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
