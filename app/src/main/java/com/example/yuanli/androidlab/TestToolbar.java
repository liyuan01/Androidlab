package com.example.yuanli.androidlab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TestToolbar extends AppCompatActivity {
    String someString ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
       int id = mi.getItemId();

        switch (id) {
            case R.id.action_one:
                if(someString!= null) {
                    Snackbar.make(getWindow().getDecorView(),someString,Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();
                }else{
                Snackbar.make(getWindow().getDecorView(),"seleted item 1",Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();}
               // Log.d("Toolbar", "Option 1 selected: ");
                break;
            case R.id.action_two:
                final AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle("Do you want to go back?");
                builder.setCancelable(true);
// Add the buttons
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                      finish();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                Log.d("Toolbar", "Option 2 selected: ");
                break;
            case R.id.action_three:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(TestToolbar.this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();
           View view= inflater.inflate(R.layout.custom_dialog,null);
                final EditText et = (EditText)view.findViewById(R.id.message);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder1.setView(view)
                        // Add action buttons
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
someString = et.getText().toString();
                                Snackbar.make(getWindow().getDecorView(),someString,Snackbar.LENGTH_LONG)
                                        .setAction("Action",null).show();

                                dialog.cancel();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dialog = builder1.create();
                dialog.show();
                Log.d("Toolbar", "Option 3 selected: ");
                break;

     }

        return true;
    }






}



