package com.example.maciek.eresviewer;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Maciek on 06.08.2017.
 */
/*interface GotResponseCodeListener{
        public void manTheCode(int responseCode);
        }*/

public class LoginTask extends downloadHTMLPageTask<Void, Void, Integer>  {

   // private GotResponseCodeListener listener;

    public LoginTask(Activity loginAct){
        super(loginAct);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Activity loginActivity=(Activity)this.appContext;
        this.downloadHTMLPage(appContext.getString(R.string.main_page_URL));
        return this.gerResponseCode();
    }
   // public void addGotResponseCodeListener(GotResponseCodeListener listener){ this.listener=listener; }

    @Override
    protected void onPostExecute(Integer responseCode){
        LoginActivity loginActivity=(LoginActivity)this.appContext;
        TextView info_TV=(TextView)loginActivity.findViewById(R.id.InfoTextView);
        switch(responseCode){
            case 200:
                loginActivity.finish();
                break;
            case 401:
                loginActivity.getProgressDialog().hide();
                info_TV.setVisibility(View.VISIBLE);
                Preferences.removeCredentials(appContext);
                break;
        }
    }
}
