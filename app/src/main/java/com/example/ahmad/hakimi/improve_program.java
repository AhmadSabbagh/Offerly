package com.example.ahmad.hakimi;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class improve_program extends AppCompatActivity {
EditText ed;
String result_improve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_program);
        ed=(EditText)findViewById(R.id.improveTextID);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
    }

    public void suggBu(View view) {
        improve();
    }
    public void improve() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(improve_program.this);
                    result_improve = http.SEND_POST_improve("http://188.165.159.59/testapp/dal/Login.asmx/improve",ed.getText().toString());
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_improve);


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
                String email,phone,nationality,address;
                String [] infolist ={};
                    if(result_improve.contains("succ"))
                    {
                          Toast.makeText(improve_program.this,"Thank you we will contact you soon",Toast.LENGTH_SHORT).show();
                          finish();
                    }
                    else
                    {
                        Toast.makeText(improve_program.this,"Please try another time",Toast.LENGTH_SHORT).show();
                        finish();

                    }





                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
