package com.example.maciek.eresviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maciek on 24.07.2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog loggingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button=(Button)findViewById(R.id.loginButton);
        button.setOnClickListener(this);
        if(!isItFirstCall(savedInstanceState)){
            if(savedInstanceState.getBoolean("isLogging", false))
            showProgressDialog();
        }


    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        if(loggingProgress!=null && loggingProgress.isShowing())
        outState.putBoolean("isLogging", true);
    }
    private Boolean isItFirstCall(Bundle bundle){
        if(bundle!= null)
            return false;
        else return true;
    }
    private void showProgressDialog(){
        loggingProgress=ProgressDialog.show(this, "Logowanie", "Czekaj", true);
    }
    @Override
    public void onClick(View v) {
        //Todo: zapisuje sie haslo przed zalogowaniem
        Preferences.saveCredentials(((EditText)this.findViewById(R.id.login_view)).getText().toString(), ((EditText)this.findViewById(R.id.passwordEditText)).getText().toString(), this);
        showProgressDialog();
        LoginTask lt=new LoginTask(this);
        lt.execute();
    }
    public ProgressDialog getProgressDialog(){ return loggingProgress; }
    //@Override
    public void manTheCode(final int responseCode) {

                switch(responseCode){
                    case 200:
                        this.finish();
                        break;
                    case 401:
                        TextView infoLabel=(TextView)findViewById(R.id.InfoTextView);
                        infoLabel.setVisibility(View.INVISIBLE);
                        break;
            }

    }
    @Override
    public void onBackPressed(){
        Intent homeIntent=new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);

    }
}
