package com.example.ahmad.hakimi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComplaintActivity extends AppCompatActivity {
EditText compEmail,emplaint;
String result_Complaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        emplaint=(EditText)findViewById(R.id.CompID);
        compEmail=(EditText)findViewById(R.id.CompEmailID);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
    }

    public void sendComp(View view) {
        if(isNetworkAvailable())
        Complaint();

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void Complaint() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(ComplaintActivity.this);
                    result_Complaint = http.SEND_POST_Complaint("http://188.165.159.59/testapp/dal/Login.asmx/add_complaint",
                            compEmail.getText().toString(),emplaint.getText().toString());
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Complaint);


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

                if(result_Complaint.contains("succ")) {

                    //   x.cancel();
                    Toast.makeText(ComplaintActivity.this, "Your Complaint Successfuly Added", Toast.LENGTH_SHORT).show();
                    finish();
                    // JSONObject jb = null;

                    }


                else
                {
                    Toast.makeText(ComplaintActivity.this,"Check your info",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
