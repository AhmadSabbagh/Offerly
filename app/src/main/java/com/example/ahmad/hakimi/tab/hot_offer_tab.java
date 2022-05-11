package com.example.ahmad.hakimi.tab;

/**
 * Created by ahmad on 12/3/2017.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.Account;
import com.example.ahmad.hakimi.First_Page;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.basicClass.MyCustomerHotOffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class hot_offer_tab extends Fragment {

public   int count=0;
    String result_hot_offer,result_info;
    String [] Hot_offer_name;
    ListView offerLIST;
private Context context;
    private Handler mHandler;
    Boolean status;
    public hot_offer_tab (Context context)
    {

        this.context=context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hot_offer, container, false);

        offerLIST=(ListView)rootView.findViewById(R.id.offerLV);
        hotOffer();



        return rootView;
    }
    public void hotOffer() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender(getActivity());
                        result_hot_offer = http.SEND_POST_Hot_offer("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hotoffer");
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else
                    {
                        result_hot_offer="";
                    }
                    Log.d("String", result_hot_offer);


                } catch (IOException e) {
                    Log.d("tag", "Couldn't check internet connection", e);
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

                if(result_hot_offer.contains("name")) {

                    //   x.cancel();
                   // Toast.makeText(getActivity(), "Welcome To Hot Offer", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_hot_offer);
                        Hot_offer_name= new String[JA.length()];
                        String [] hot_offer_text =  new String[JA.length()];
                        String [] url =  new String[JA.length()];
                        ArrayList<String>name = new ArrayList<String>();
                        ArrayList<String>text = new ArrayList<String>();
                        ArrayList<String>urlPic = new ArrayList<String>();
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);
                            Hot_offer_name[i] = (String) JO.get("name");
                            hot_offer_text[i]=(String) JO.get("text");
                            url[i]="http://188.165.159.59/testapp/"+(String) JO.get("url_picture");
                            name.add(Hot_offer_name[i]);
                            text.add(hot_offer_text[i]);
                            urlPic.add(url[i]);

                        }
                        MyCustomerHotOffer list = new MyCustomerHotOffer (name,text,urlPic,context);
                        offerLIST.setAdapter(list) ;
                       count= list.getCount();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                 //   Toast.makeText(getActivity(),"No Hot offer found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    /*
    public void hotOffer1() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender(getActivity());
                        result_hot_offer = http.SEND_POST_Hot_offer("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hotoffer");
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else
                    {
                        result_hot_offer="";
                    }
                    Log.d("String", result_hot_offer);


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

                if(result_hot_offer.contains("name")) {

                    //   x.cancel();
                    // Toast.makeText(getActivity(), "Welcome To Hot Offer", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_hot_offer);
                        Hot_offer_name= new String[JA.length()];;
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        status=getNotificationStatus();


                        if(count<JA.length() && status==true )
                        {
                            Intent intent = new Intent(context, First_Page.class);
                            PendingIntent pintent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            NotificationManager notificationmgr = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                            Notification notif = new Notification.Builder(context)
                                    .setSmallIcon(R.drawable.com_facebook_button_icon)
                                    .setContentTitle("New Hot Offer for you")
                                    .setContentText("")
                                    .setContentIntent(pintent)
                                    .build();
                            notificationmgr.notify(0,notif);
                            count=JA.length();
                          //  hotOffer();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    //   Toast.makeText(getActivity(),"No Hot offer found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    */

    public Boolean getNotificationStatus() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender((Activity) context);
                        result_info = http.SEND_POST_get_customer_info("http://188.165.159.59/testapp/dal/Login.asmx/getdata_notficate");
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else {
                        result_info="";}
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
                        Toast.makeText(context,"Wrong",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();
return  status;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
