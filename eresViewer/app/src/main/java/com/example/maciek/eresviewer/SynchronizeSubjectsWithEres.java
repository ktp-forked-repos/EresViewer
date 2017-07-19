package com.example.maciek.eresviewer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by Maciek on 12.07.2017.
 */

public abstract class SynchronizeSubjectsWithEres extends downloadHTMLPageTask<URL, Void, Void> {

    //Tymczasowa zmienna przechowująca pobrane przedmioty
    private ArrayList<Subject> subjects;

    public SynchronizeSubjectsWithEres(){
        subjects=new ArrayList<>();
    }


    /**
     * metoda wydobywająca listę przedmiotów użytkownika ze strony głównej
     */
    private List<String> extractSubjects() {
        Document mainPage=this.downloadHTMLPage("https://studia.elka.pw.edu.pl/pl/17L/");
        Elements subjects=mainPage.select("[class=nagl]").select("[colspan=2]");
        subjects.remove(0);
        return subjects.eachText();
    }



}
