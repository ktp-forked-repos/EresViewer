package com.example.maciek.eresviewer;

import android.content.Context;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by Maciek on 12.07.2017.
 */

public class SynchronizeSubjectsWithEres extends downloadHTMLPageTask<ArrayList<String>, Void, Void> {

    //Tymczasowa zmienna przechowująca pobrane przedmioty
    private ArrayList<Subject> subjects;


    public SynchronizeSubjectsWithEres(Context context){
        super(context);
        subjects=new ArrayList<>();

    }

     protected Void doInBackground(ArrayList<String>... subjects){
        subjects[0].addAll(extractSubjects(Integer.toString(R.string.main_page_URL)));
         return null;
     }

    /**
     * metoda wydobywająca listę przedmiotów użytkownika ze strony głównej
     */
    private List<String> extractSubjects(String page) {
        Document mainPage=this.downloadHTMLPage(page);
        Elements subjects=mainPage.select("[class=nagl]").select("[colspan=2]");
        subjects.remove(0);
        return subjects.eachText();
    }



}
