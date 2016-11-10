package com.example.yuanli.androidlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    SharedPreferences sharedPref ;
    EditText loginNameET;
    Button buttonlogin;
    String loginEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void  onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

        loginNameET= (EditText)findViewById(R.id.editlogin);
        buttonlogin = (Button)findViewById(R.id.button2);
        sharedPref  = this.getSharedPreferences(ACTIVITY_NAME, MODE_PRIVATE);
        loginEmail = sharedPref.getString("EMAIL","email@domain.com");


        loginNameET.setText(loginEmail);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  e=loginNameET.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("EMAIL",e);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void  onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }


    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}




