package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Suggestion extends AppCompatActivity {
    EditText AgentName,AgentPhone,AgentEmail,YourEmail,YourPhone;

String result_Sugg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        AgentName =(EditText)findViewById(R.id.AgentNameId);
        AgentPhone =(EditText)findViewById(R.id.AgentPhoneId);
        AgentEmail =(EditText)findViewById(R.id.AgentEmailId);
        YourEmail =(EditText)findViewById(R.id.YourEmailId);
        YourPhone =(EditText)findViewById(R.id.YourPhoneId);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

    }

    public void SendSugg(View view) {
        if(isNetworkAvailable()) {
            send_sugg();
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void send_sugg() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Suggestion.this);
                    result_Sugg = http.SEND_POST_Sugg("http://188.165.159.59/testapp/dal/Login.asmx/add_suggest",AgentName.getText().toString()
                    ,AgentEmail.getText().toString(),AgentPhone.getText().toString(),YourEmail.getText().toString(),YourPhone.getText().toString());


                    Log.d("String", result_Sugg);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {



                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_Sugg.contains("add suggest to succ")){

                    //   x.cancel();
                    Toast.makeText(Suggestion.this, "Thank you for suggestion", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Suggestion.this,First_Page.class));
                    finish();

                    // JSONObject jb = null;


                }
                else
                {
                    Toast.makeText(Suggestion.this,"Invalid information Or Already Added",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
