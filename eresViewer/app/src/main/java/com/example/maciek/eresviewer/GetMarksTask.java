package com.example.maciek.eresviewer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Maciek on 19.07.2017.
 */
public class GetMarksTask extends downloadHTMLPageTask<ArrayList<Mark>, Void, ArrayList<Mark>>{


    private Subject subject;

    public GetMarksTask(Subject subject){
        super(subject.getContext());
        this.subject=subject;

    }
    protected ArrayList<Mark> doInBackground(ArrayList<Mark>... marks){
        String page=preparePage();
        Document downloadedPage=this.downloadHTMLPage(page);
        extractMarks(marks[0],downloadedPage, subject.getSubjectName());
        return marks[0];
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
            Mark mark = new Mark(titles.get(i), Float.parseFloat(allMarks.get(j)), Float.parseFloat(allMarks.get(j + 1)),
                    Float.parseFloat(allMarks.get(j + 2)), Float.parseFloat(allMarks.get(j + 3)), Integer.parseInt(allMarks.get(j + 4)));
            marks.add(mark);
        }
    }
    private String preparePage(){
        String page=subject.getContext().getString(R.string.myMarksPage);
        page=page.replaceAll("term", "17L");
        page=page.replaceAll("subject", subject.getSubjectName());
        return page;
    }
    protected void onPreExecute(){
      //  ((SwipeRefreshLayout)((Activity)context).findViewById(R.id.swiperefresh)).setRefreshing(true);
    }
    protected void onPostExecute(ArrayList<Mark> result){
        ((SwipeRefreshLayout)((Activity)subject.getContext()).findViewById(R.id.swiperefresh)).setRefreshing(false);
        subject.compareDownloadedMarks(result);
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
