package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class ActiveEmail extends AppCompatActivity {
      String result_active,params_active;
      EditText emailAc, codeAc;
      Button sendAc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_email);
        emailAc=(EditText)findViewById(R.id.emailActive);
        codeAc=(EditText)findViewById(R.id.codeActive);
        sendAc=(Button)findViewById(R.id.buttonActive);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));



    }

    public void activeBut(View view) {
        if(isNetworkAvailable()) {
            sendRequest_active();
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void sendRequest_active() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(ActiveEmail.this);
                    result_active = http.SEND_Active("http://188.165.159.59/testapp/dal/Login.asmx/activemail", emailAc.getText().toString(), codeAc.getText().toString());
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email  ", emailAc.getText().toString());
                    jsonObject.put("code", codeAc.getText().toString());

                    params_active = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_active.contains("active succ")) {

                   int ID;
                    Toast.makeText(getBaseContext(), "Activated", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONObject job =  new JSONObject(result_active);
                        ID= (int)job.get("customer_id") ;
                      //   ID = new JSONObject(result_active).getString("customer_id");

                        Intent intent1=new Intent(ActiveEmail.this,First_Page.class);
                        AttolSharedPreference mySharedPreference = new AttolSharedPreference(ActiveEmail.this);
                        mySharedPreference.setKey("id", String.valueOf(ID));
                        startActivity(intent1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"Invalid Activation code or Email",Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(getBaseContext(),MainActivity.class));
                  //  x.cancel();
                    //finish();
                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
