package com.example.maciek.eresviewer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
<<<<<<< HEAD
        if(!isItFirstCall(savedInstanceState)){
            if(savedInstanceState.getBoolean("isLogging", false))
            showProgressDialog();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        EditText loginTextView=(EditText)this.findViewById(R.id.login_view);
        EditText passwordTextView=(EditText)this.findViewById(R.id.passwordEditText);
        Preferences.saveString(getString(R.string.singin_form_login),(loginTextView).getText().toString(),this);
        Preferences.saveString(getString(R.string.singin_form_password),(passwordTextView).getText().toString(),this);
=======
>>>>>>> parent of de51a80... Merge branch 'master' into subject_configuration
    }

    @Override
    public void onClick(View v) {
        //Todo: dodac parametryzacje
        Preferences.saveCredentials(this, ((EditText)this.findViewById(R.id.login_view)).getText().toString(), ((EditText)this.findViewById(R.id.passwordEditText)).getText().toString());
        loggingProgress=ProgressDialog.show(this, "Logowanie", "Czekaj", true);
        LoginTask lt=new LoginTask(this);
        //lt.addGotResponseCodeListener(this);
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
}
