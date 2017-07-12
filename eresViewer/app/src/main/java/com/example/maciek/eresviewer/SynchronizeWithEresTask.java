package com.example.maciek.eresviewer;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.Subject;

/**
 * Created by Maciek on 12.07.2017.
 */

public class SynchronizeWithEresTask extends AsyncTask<URL,Integer, ArrayList<SubjectActivity> > {
@Override
    protected ArrayList<SubjectActivity> doInBackground(URL... url){
        connectWithEres();
        return null;
    }

    protected void onPostExecute(ArrayList<SubjectActivity> subjectsArray){

    }

    public void connectWithEres(URL url){
        try {

            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            int responseCode=connection.getResponseCode();

            BufferedReader br= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            StringBuilder responseOutput=new StringBuilder();

            while((line=br.readLine())!=null)
                responseOutput.append(line);

            String response=responseOutput.toString();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
