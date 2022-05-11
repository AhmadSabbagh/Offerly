package com.example.ahmad.hakimi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerAdapterNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class resources extends AppCompatActivity {
String result_follow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));


    }

    public void addRes(View view) {
        startActivity(new Intent(resources.this,HomePage.class));
        finish();
    }

    public void editRes(View view) {
        if(isNetworkAvailable()) {
            getFollow();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void getFollow() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(resources.this);
                    result_follow = http.SEND_POST_get_news("http://188.165.159.59/testapp/dal/Login.asmx/getdata_folw");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_follow);


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

                if(result_follow.contains("name")) {

                    //   x.cancel();
                    //  Toast.makeText(getActivity(), "Welcome News", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_follow);
                        String [] name= new String[JA.length()];
                       int [] agent_id= new int [JA.length()];
                       ArrayList<String>agentName=new ArrayList<String>();

                        //  double [] Hot_offer_image=new double[JA.length()];;

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);
                            name[i] = (String) JO.get("name");
                            agent_id[i]= (int) JO.get("agent_id");
                            agentName.add(name[i]);
                        }
                    Intent intent2 = new Intent(resources.this,ListViewActivity.class);
                        intent2.putExtra("AgentFollowedName",agentName);
                        intent2.putExtra("agentFollowedID",agent_id);
                        startActivity(intent2);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  Toast.makeText(getBaseContext(),"No Resources Go to Add new Resources",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
