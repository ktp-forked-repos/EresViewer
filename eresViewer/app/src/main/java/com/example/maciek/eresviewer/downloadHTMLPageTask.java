package com.example.maciek.eresviewer;

import android.os.AsyncTask;
import android.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Maciek on 17.07.2017.
 */

public abstract class downloadHTMLPageTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result>  {
    /**
     * metoda zwracająca obiekt typu Dokument potrzebny do łatwego parsowania plików
     * HTML za pomocą biblioteki Jsoup
     */
    public Document downloadHTMLPage(String pageAsString) {
        Document doc=new Document("");
        try {
            URL url1 = new URL(pageAsString);
            HttpsURLConnection connection=(HttpsURLConnection)url1.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String username="mkobiere";
            String password="PLNYselena1";
            byte[] message = (username+":"+password).getBytes("UTF-8");
            String encoded = Base64.encodeToString(message, Base64.DEFAULT);
            connection.setRequestProperty("Authorization", "Basic "+encoded);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            int responseCode=connection.getResponseCode();

            BufferedReader br= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            StringBuilder responseOutput=new StringBuilder();

            while((line=br.readLine())!=null)
                responseOutput.append(line);

            String response=responseOutput.toString();
            doc= Jsoup.parse(response);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
