package com.example.ahmad.hakimi.tab;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.Map;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.nearby;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ahmad on 12/24/2017.
 */

public class nearby_fragment extends Fragment {
    private Context context;
    String result_Agent_branch;

    public nearby_fragment ()
    {}

    public nearby_fragment (Context context)
    {

        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearby_fragments, container, false);
        Button hotel;
        Button cafee;
        Button resturant,clothes;
        resturant=(Button) rootView.findViewById(R.id.Rid);
        cafee=(Button) rootView.findViewById(R.id.Cid);
        hotel=(Button) rootView.findViewById(R.id.Hid);
        clothes=(Button) rootView.findViewById(R.id.CLid);
        LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
          //  Toast.makeText(context, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
           // showGPSDisabledAlertToUser();
        }

        resturant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                getLocation(3);

            }
        });
        cafee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                getLocation(2);

            }
        });
        clothes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                getLocation(1003);

            }
        });
        hotel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                getLocation(1);

            }
        });


        return rootView;
    }

    public void getLocation(final int Category_ID) { //here adjust map
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender((Activity) context);
                        result_Agent_branch = http.SEND_POST_Agent_location("http://188.165.159.59/testapp/dal/Login.asmx/getdata_agent_location", Category_ID);
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else
                    {
                        result_Agent_branch="";
                        Toast.makeText(context,"check the internet Connection",Toast.LENGTH_SHORT).show();


                    }
                    Log.d("String", result_Agent_branch);


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
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent_branch.contains("latitude")) {

                    //   x.cancel();
                   // Toast.makeText(context, "Welcome To Agent loc", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_Agent_branch);
                        int [] agent_id= new int[JA.length()];;
                        String []  name = new String [JA.length()];
                        double [] branch_lat= new double[JA.length()];
                        double [] branch_lon= new double[JA.length()];
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            agent_id[i] = (int) JO.get("agent_id");
                            name[i] = (String) JO.get("name");
                            branch_lat[i]= (double) JO.get("latitude");
                            branch_lon[i]= (double) JO.get("longitude");
                        }
                        Intent intent2 =new Intent(context,Map.class);
                        intent2.putExtra("agentID",agent_id);
                        intent2.putExtra("agentName",name);
                        intent2.putExtra("agentsLAt",branch_lat);
                        intent2.putExtra("agentLOn",branch_lon);
                        startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(context,"No Locations",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
   /* private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }*/
}
