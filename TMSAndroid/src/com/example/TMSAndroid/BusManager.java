package com.example.TMSAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import utility.LogOutManager;

public class BusManager extends Activity implements LogOutManager{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);
    }

    @Override
    public void logOut(View view) {
        Intent intent = new Intent(getApplicationContext(),MyActivity.class);
        startActivity(intent);
    }

    public void exit(View view){
        Intent intent = new Intent(getApplicationContext(),ConductorActivity.class);
        startActivity(intent);
    }

}
