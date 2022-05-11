package com.example.ahmad.hakimi.basicClass;

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
import android.util.Log;
import android.widget.Toast;

import com.example.ahmad.hakimi.First_Page;
import com.example.ahmad.hakimi.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FcmMessage extends FirebaseMessagingService {
    String result_info;
    Boolean status;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("Response >>>>", new Gson().toJson(remoteMessage.getData()));
        Log.e("null >>>>", new Gson().toJson(remoteMessage.getNotification()));
        getNotificationStatus(remoteMessage);



    }

    public Boolean getNotificationStatus(final RemoteMessage remoteMessage) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender(FcmMessage.this);
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
                      //  status=true;
                        String title = remoteMessage.getData().get("title");
                        String body = remoteMessage.getData().get("body");

                            Intent intent = new Intent(FcmMessage.this, First_Page.class);
                            PendingIntent pintent = PendingIntent.getActivity(FcmMessage.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            NotificationManager notificationmgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            Notification notif = new Notification.Builder(FcmMessage.this)
                                    .setSmallIcon(R.drawable.iconnewone)
                                    .setContentTitle(title)
                                    .setContentText(body)
                                    .setContentIntent(pintent)
                                    .build();
                            notificationmgr.notify(0, notif);


                    }
                    else if(result_info.contains("turn of"))
                    {
                        //  notificationButton.setText("Turn On Notification");
                        status=false;
                    }
                    else
                    {
                        Toast.makeText(FcmMessage.this,"Wrong",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                super.onPostExecute(aVoid);

            }
        }.execute();
        return status;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
