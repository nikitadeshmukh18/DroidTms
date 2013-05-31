package com.example.TMSAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import utility.LogOutManager;

public class ConductorActivity extends Activity implements LogOutManager{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor);
    }

    public void controlBus(View view){
        Intent intent = new Intent(getApplicationContext(),BusManager.class);
        startActivity(intent);

    }


    @Override
    public void logOut(View view) {
        Intent intent = new Intent(getApplicationContext(),MyActivity.class);
        startActivity(intent);
    }
}
