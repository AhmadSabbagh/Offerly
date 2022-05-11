package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class information extends AppCompatActivity {
    ListView informationList ;
    String [] myInfo;
    String result_info;
    TextView Email,Phone,Country,Adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //informationList=(ListView)findViewById(R.id.infoList);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        Email=(TextView) findViewById(R.id.emailID);
        Phone=(TextView) findViewById(R.id.phoneID);
        Country=(TextView) findViewById(R.id.coid);
        Adress=(TextView) findViewById(R.id.adressid);

if(isNetworkAvailable()) {
    getInfo();
}
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void getInfo() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(information.this);
                    result_info = http.SEND_POST_get_customer_info("http://188.165.159.59/testapp/dal/Login.asmx/getdata_customer");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_info);


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

                  //{"usernam":"aaa@mail.com","phone":"77777","nationality":"jordn","addrss":"amman",
                // "brith_day":"\/Date(-62135596800000)\/","marriage_dat":"\/Date(-62135596800000)\/","gender":null,"age":0}{"d":null}
                    try {
                        JSONObject Jo = new JSONObject(result_info);

                          //  Toast.makeText(information.this, "Welcome News", Toast.LENGTH_SHORT).show();
                            email=Jo.getString("usernam");
                            phone=Jo.getString("phone");
                            nationality=Jo.getString("nationality");
                            address=Jo.getString("addrss");
                            infolist = new String[]{email, phone, nationality, address};
                            if(email=="null")
                            {
                                getFaceInfo();
                            }
                            else {
                                Email.setText(email);
                                Phone.setText(phone);
                                Country.setText(nationality);
                                Adress.setText(address);

                            }








                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getFaceInfo() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(information.this);
                    result_info = http.SEND_POST_get_customer_info("http://188.165.159.59/testapp/dal/Login.asmx/getdata_customer_face");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_info);


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

                //{"usernam":"aaa@mail.com","phone":"77777","nationality":"jordn","addrss":"amman",
                // "brith_day":"\/Date(-62135596800000)\/","marriage_dat":"\/Date(-62135596800000)\/","gender":null,"age":0}{"d":null}
                try {
                    JSONObject Jo = new JSONObject(result_info);
                    if(Jo.getString("usernam")!=null)
                    {
                        //  Toast.makeText(information.this, "Welcome News", Toast.LENGTH_SHORT).show();
                        email=Jo.getString("usernam");
                        phone=Jo.getString("phone");
                        nationality=Jo.getString("nationality");
                        address=Jo.getString("addrss");
                        infolist = new String[]{email, phone, nationality, address};
                        Email.setText(email);
                        Phone.setText(phone);
                        Country.setText("-----------");

                        Adress.setText("------------");


                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
