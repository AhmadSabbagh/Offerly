package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.tab.hot_offer_tab;
import com.example.ahmad.hakimi.tab.nearby_fragment;
import com.example.ahmad.hakimi.tab.news_tab;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class First_Page extends AppCompatActivity   { //edited 21-1


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private LocationManager locationManager;
    private LocationListener listener;
    private ViewPager mViewPager;
    private static Boolean GPS=true;
    String result_follow;
    int [] agent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BottomBar bottomBar2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
       getFollow();



        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            if(GPS==true) {
                showGPSDisabledAlertToUser();
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNetworkAvailable()) {
                    switch (item.getItemId()) {
                        case R.id.id1://firstPage

                            break;
                        case R.id.navigation_home: //homePage
                            startActivity(new Intent(First_Page.this, HomePage.class));
                            finish();
                            break;
                        case R.id.navigation_notifications://NearBy
                            startActivity(new Intent(First_Page.this, hot_event_activity.class));
                            finish();
                            break;
                        case R.id.navigation_dashboard: //account
                            startActivity(new Intent(First_Page.this, product.class));
                            finish();
                            break;
                        case R.id.id2:
                            startActivity(new Intent(First_Page.this, NewFavoriteAvtivity.class));
                            finish();
                            break;

                    }
                }
                else
                {
                    Toast.makeText(First_Page.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //   Toast.makeText(getApplicationContext(),"\n " + location.getLongitude() + " " + location.getLatitude(),Toast.LENGTH_SHORT).show();
                //    t.append("\n " + location.getLongitude() + " " + location.getLatitude());


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

    }


    public int [] getFollow() {
        new AsyncTask<Void, Void, Void>() {

            int [] agent_id;
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(First_Page.this);
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
                          agent_id= new int [JA.length()];
                        ArrayList<String> agentName=new ArrayList<String>();

                        //  double [] Hot_offer_image=new double[JA.length()];;

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);
                            name[i] = (String) JO.get("name");
                            agent_id[i]= (int) JO.get("agent_id");
                            String x = agent_id[i]+"_news";
                            FirebaseMessaging.getInstance().subscribeToTopic(agent_id[i]+"_news");
                            agentName.add(name[i]);
                        }




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
        return agent_id;
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Allow",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Deny",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        GPS=false;
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first__page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(isNetworkAvailable()) {
                startActivity(new Intent(First_Page.this, ComplaintActivity.class));
            }
            return true;
        }
        else if (id == R.id.sugg) {
            if(isNetworkAvailable()) {
                startActivity(new Intent(First_Page.this, SuggestionFirstPage.class));
            }
            return true;
        }
        else if (id == R.id.logout) {
            AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
            attolSharedPreference.setKey("id","0");
            startActivity(new Intent(First_Page.this,MainActivity.class));
            finish();
            return true;
        }
        else if (id == R.id.myacc) {
            startActivity(new Intent(First_Page.this,Account.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch (position)
           {
               case 0 : Fragment newss =getNews();
                   return newss;
               case 1 : Fragment hotOffer = getHotOffer();
               return  hotOffer;
               case 2 : Fragment hotEvent= HotEvent();
               return hotEvent;
               default:
                   return null;
           }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.


        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);


    }
    public Fragment getNews ()
    {
        news_tab newss =new news_tab(this);
        return  newss;
    }
    public Fragment HotEvent ()
    {
        nearby_fragment events =new nearby_fragment (this);
        return  events;
    }
    public Fragment getHotOffer()
    {
        hot_offer_tab offer =new hot_offer_tab (this);
        return  offer;
    }

}
