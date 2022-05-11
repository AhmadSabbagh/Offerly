package com.example.ahmad.hakimi;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerHotEvent;
import com.example.ahmad.hakimi.basicClass.MyCustomerHotOffer;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class hot_event_activity extends AppCompatActivity {
    int count1=0;
    private Context context;
    ListView eventLV,lv;
    Dialog dialog;
    String result_hot_Event,result_hot_Event_details,params_event_details,hot_event_url;
    String [] Hot_event_name  ;
    String Hot_event_Constant_tel ,Hot_event_mobile_tel ,Hot_event_address,result_info;
    String Hot_event_Methodof_pay;
    int [] Hot_event_id;
    private Handler mHandler;
    ImageView imeg;
    Byte [] myb;
    Boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_event_activity);
        eventLV=(ListView)findViewById(R.id.hot_list_event_id);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNetworkAvailable()) {
                    switch (item.getItemId()) {

                        case R.id.id1://firstPage
                            startActivity(new Intent(hot_event_activity.this, First_Page.class));
                            finish();

                            break;
                        case R.id.navigation_home: //homePage
                            startActivity(new Intent(hot_event_activity.this, HomePage.class));
                            finish();

                            break;
                        case R.id.navigation_notifications://NearBy
                            break;
                        case R.id.navigation_dashboard: //account
                            startActivity(new Intent(hot_event_activity.this, product.class));
                            finish();

                            break;
                        case R.id.id2:
                            startActivity(new Intent(hot_event_activity.this, NewFavoriteAvtivity.class));
                            finish();

                            break;
                    }
                    }
                else
                {
                    Toast.makeText(hot_event_activity.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
                }
                    return false;

            }
        });

        hotEvent();
        /////////////////////////////////////////////////

        //////////////////////////////////////////////////
        eventLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
             //   Toast.makeText(hot_event_activity.this,value,Toast.LENGTH_SHORT).show();
             //   Toast.makeText(hot_event_activity.this,""+position,Toast.LENGTH_SHORT).show();
                if(Hot_event_id!=null) {
               //     Toast.makeText(hot_event_activity.this,""+position,Toast.LENGTH_SHORT).show();
                    if(isNetworkAvailable()) {
                        hotEventDetails(Hot_event_id[position]);
                    }
                    else
                    {
                        Toast.makeText(hot_event_activity.this,"Check internet connection",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }
    public void hotEvent() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(hot_event_activity.this);
                    result_hot_Event = http.SEND_POST_Hot_offer("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hoteventheadr");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_hot_Event);


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

                if(result_hot_Event.contains("name")) {

                    //   x.cancel();
                    //   Toast.makeText(getActivity(), "Welcome To Hot event", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_hot_Event);
                        Hot_event_name= new String[JA.length()];
                        Hot_event_id=new int [JA.length()];
                        String [] url = new String[JA.length()];
                        ArrayList<String> name = new ArrayList<String>();
                        ArrayList<String>text = new ArrayList<String>();
                        ArrayList<String>urlPic = new ArrayList<String>();
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            Hot_event_name[i] = (String) JO.get("name");
                            Hot_event_id[i] = (int) JO.get("id");
                            url[i]="http://188.165.159.59/testapp/"+(String)JO.get("url");
                            name.add( Hot_event_name[i]);
                            text.add("");
                            urlPic.add(url[i]);
                        }

                        MyCustomerHotEvent list = new MyCustomerHotEvent (name,text,urlPic,hot_event_activity.this);
                        eventLV.setAdapter(list) ;
                        count1= list.getCount();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    //  Toast.makeText(getActivity(),"No Hot event found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    /*
    public void hotEvent1() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(hot_event_activity.this);
                    result_hot_Event = http.SEND_POST_Hot_offer("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hoteventheadr");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_hot_Event);


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

                if(result_hot_Event.contains("name")) {

                    try {
                        JSONArray JA = new JSONArray(result_hot_Event);

                        status=getNotificationStatus();

                        if(count1<JA.length() && status==true)
                        {
                            Intent intent = new Intent(hot_event_activity.this, First_Page.class);
                            PendingIntent pintent = PendingIntent.getActivity(hot_event_activity.this, (int) System.currentTimeMillis(), intent, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            NotificationManager notificationmgr = (NotificationManager)hot_event_activity.this.getSystemService(NOTIFICATION_SERVICE);
                            Notification notif = new Notification.Builder(hot_event_activity.this)
                                    .setSmallIcon(R.drawable.com_facebook_button_icon)
                                    .setContentTitle("New Hot Event for you")
                                    .setContentText("")
                                    .setContentIntent(pintent)
                                    .build();
                            notificationmgr.notify(0,notif);
                            count1=JA.length();
                            // hotEvent();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    //  Toast.makeText(getActivity(),"No Hot event found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
*/
    public void hotEventDetails(final int hot_evet_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(hot_event_activity.this);
                    result_hot_Event_details = http.SEND_POST_Hot_Event_details("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hotevent",hot_evet_id);

                    Log.d("String", result_hot_Event_details);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("hotevent_id", hot_evet_id);

                    params_event_details = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_hot_Event_details.contains("name")) {


                    double lat ,lon;
                    try {
                        JSONObject JO = new JSONObject(result_hot_Event_details);

                        Hot_event_mobile_tel = (String) JO.get("hotevent_text");
                       hot_event_url = "http://188.165.159.59/testapp/"+(String)JO.get("url_picture");
                        lat = (double) JO.get("latitude");
                        lon = (double) JO.get("longitude");

                       Intent intent2 = new Intent(hot_event_activity.this,hot_event_details.class);
                        intent2.putExtra("hotevent_text",Hot_event_mobile_tel);
                        intent2.putExtra("url",hot_event_url);
                        intent2.putExtra("lat",lat);
                        intent2.putExtra("lon",lon);
                        intent2.putExtra("id",hot_evet_id);
                        startActivity(intent2);
                       // Toast.makeText(hot_event_activity.this,Hot_event_Methodof_pay,Toast.LENGTH_SHORT).show();





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(hot_event_activity.this,"No Hot event Details found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
/*
    public Boolean getNotificationStatus() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(hot_event_activity.this);
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
                        // notificationButton.setText("Turn Off Notification");
                        status=true;

                    }
                    else if(result_info.contains("turn of"))
                    {
                        //  notificationButton.setText("Turn On Notification");
                        status=false;
                    }
                    else
                    {
                      //  Toast.makeText(hot_event_activity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();
        return status;
    }
  */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    }

