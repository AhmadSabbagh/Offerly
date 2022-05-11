package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class nearby extends AppCompatActivity {
    int Category_ID;
    String result_Agent_branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

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
                switch (item.getItemId())
                {
                    case R.id.id1 ://firstPage
                        startActivity(new Intent(nearby.this,First_Page.class));
                        break;
                    case R.id.navigation_home : //homePage
                        startActivity(new Intent(nearby.this,HomePage.class));
                        break;
                    case R.id.navigation_notifications ://NearBy
                        break;
                    case R.id.navigation_dashboard : //account
                        startActivity(new Intent(nearby.this,Account.class));
                        break;
                    case R.id.id2 :startActivity(new Intent(nearby.this,NewFavoriteAvtivity.class));
                        break;

                }
                return false;
            }
        });



            }

    public void findResturant(View view) {
        Category_ID=3;
        getLocation(Category_ID);
    }

    public void findHotel(View view) {
        Category_ID=1;
        getLocation(Category_ID);
    }

    public void findClothes(View view) {
        Category_ID=1003;
        getLocation(Category_ID);
    }

    public void findCafees(View view) {
        Category_ID=2;
        getLocation(Category_ID);
    }

    public void getLocation(final int Category_ID) { //here adjust map
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender(nearby.this);
                        result_Agent_branch = http.SEND_POST_Agent_location("http://188.165.159.59/testapp/dal/Login.asmx/getdata_agent_location", Category_ID);
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else {
                        result_Agent_branch = "";
                        Toast.makeText(nearby.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(getBaseContext(), "Welcome To Agent loc", Toast.LENGTH_SHORT).show();
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
                          //  Toast.makeText(nearby.this,agent_id[i]+"  "+name[i]+"  "+branch_lat[i]+ "  "+branch_lon[i],Toast.LENGTH_SHORT).show();
                        }
                        Intent intent2 =new Intent(nearby.this,Map.class);
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
                //    Toast.makeText(getBaseContext(),"No Hot offer found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
