package com.example.TMSAndroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URL;

public class LoginActivity  extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void doLogin(View view){

        EditText username = (EditText) findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.editText1);
        Log.v("username=",username.getText().toString());
        Log.v("password=",password.getText().toString());
        String uname = username.getText().toString();
        String pass = password.getText().toString();

        callLoginApi(uname, pass);

//        Intent intent = new Intent(getApplicationContext(),ConductorActivity.class);
//        startActivity(intent);

    }

    private void callLoginApi(String username, String password) {

        String url =  "http://192.168.1.4:8080/Sample/api/login?username="+username+"&password="+password;
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        int statuscode = 0;
        try{
                response = client.execute(get);
                statuscode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.v("response = ", response.toString());
            Log.v("code = " , String.valueOf(statuscode));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.v("response = ", response.toString());
            Log.v("code = " , String.valueOf(statuscode));
        }

        Log.v("response = ", response.toString());
        Log.v("code = " , String.valueOf(statuscode));
    }


}
