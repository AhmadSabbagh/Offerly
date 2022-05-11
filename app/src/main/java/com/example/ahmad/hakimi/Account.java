package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Account extends AppCompatActivity  {
    private BottomBar bottomBar1;
    static Boolean notification;
    Button notificationButton;
    String result_info,result_info1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        notificationButton=(Button)findViewById(R.id.NotificationID);


        if(isNetworkAvailable()) {
            getNotificationStatus();
        }


        getNotificationStatus();

    }

 public  static Boolean getNotificationresult()
 {
     return  notification;
 }
    public void forgetPass(View view) {
        startActivity(new Intent(Account.this,ForgetPasswordPage.class));

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void share(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String body="yourBody";
        String sub = "yourSub";
        intent.putExtra(Intent.EXTRA_SUBJECT,sub);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(Intent.createChooser(intent,"Share using"));


    }

    public void getmyinfo(View view) {
       startActivity(new Intent(Account.this,information.class));

    }

    public void editResourc(View view) {
        startActivity(new Intent(Account.this,resources.class));
    }

    public void edtNoti(View view) {
        if(notificationButton.getText().toString().contains("Turn Off Notification"))
        {
           //true
            if(isNetworkAvailable()) {
                editNotification("true");
            }
            else
            {
                Toast.makeText(Account.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
        else if (notificationButton.getText().toString().contains("Turn On Notification"))
        {
            //false
            if(isNetworkAvailable()) {
                editNotification("false");

            }
            else
            {
                Toast.makeText(Account.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void getNotificationStatus() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Account.this);
                    result_info = http.SEND_POST_get_customer_info("http://188.165.159.59/testapp/dal/Login.asmx/getdata_notficate");
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


                try {
                    JSONObject Jo = new JSONObject(result_info);
                     if(result_info.contains("turn_on"))
                     {
                         notificationButton.setText("Turn Off Notification");

                     }
                     else if(result_info.contains("turn of"))
                     {
                         notificationButton.setText("Turn On Notification");

                     }
                     else
                     {
                       //  Toast.makeText(Account.this,"Wrong",Toast.LENGTH_SHORT).show();
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void editNotification(final String status) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Account.this);
                    result_info1 = http.SEND_POST_edit_noti("http://188.165.159.59/testapp/dal/Login.asmx/remove_notfication",status);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_info1);


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


                try {
                    JSONObject Jo = new JSONObject(result_info);
                    if(result_info1.contains("succ"))
                    {
                        notificationButton.setText("Turn On Notification");
                        notification=false;
                    }
                    else if(result_info1.contains("on"))
                    {
                        notificationButton.setText("Turn Off Notification");
                        notification=true;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
