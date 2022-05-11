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
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    String result_hot_offer,result_sub_category;
    String params_sub_category;
     int Category_ID;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        // BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNetworkAvailable()) {
                switch (item.getItemId()) {
                    case R.id.id1://firstPage
                        startActivity(new Intent(HomePage.this, First_Page.class));
                        finish();

                        break;
                    case R.id.navigation_home: //homePage
                        break;
                    case R.id.navigation_notifications://NearBy
                        startActivity(new Intent(HomePage.this, hot_event_activity.class));
                        finish();

                        break;
                    case R.id.navigation_dashboard: //account
                        startActivity(new Intent(HomePage.this, product.class));
                        finish();

                        break;
                    case R.id.id2:
                        startActivity(new Intent(HomePage.this, NewFavoriteAvtivity.class));
                        finish();

                        break;
                }
                }
                else
                {
                    Toast.makeText(HomePage.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


    }


    public void findResturant(View view) {
      Category_ID=3;
      SubCategory(Category_ID);
    }
    public void SubCategory(final int categoryID) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender(HomePage.this);
                        result_sub_category = http.SEND_POST_Sub_category("http://188.165.159.59/testapp/dal/Login.asmx/getdata_sub_category", categoryID);
                        //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                        //      +username.getText().toString()+"&password="+password1.getText().toString());
                    }
                    else {
                        result_sub_category = "";
                        Toast.makeText(HomePage.this,"Check internet connection",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("String", result_sub_category);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("category_id",categoryID);

                    params_sub_category = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_sub_category.contains("cat_sub_id")) {

                    //   x.cancel();
                   // Toast.makeText(getBaseContext(), "Welcome To Sub Category", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_sub_category);
                        ArrayList<String> subName = new ArrayList<String>();
                        ArrayList<String> subUrlPic = new ArrayList<String>();
                        String [] Sub_cat_Name= new String[JA.length()];;
                        int []  sub_cat_id = new int[JA.length()];
                          String [] subUrl =new String [JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            Sub_cat_Name[i] = (String) JO.get("cat_sub_name");
                              sub_cat_id[i] = (int) JO.get("cat_sub_id");
                              subUrl[i]="http://188.165.159.59/testapp/"+(String) JO.get("url");
                              subName.add( Sub_cat_Name[i]);
                              subUrlPic.add(subUrl[i]);
                        }
                        Intent intent2 =new Intent(HomePage.this,ListViewActivity.class);
                        intent2.putExtra("subCatName",subName);
                        intent2.putExtra("subCatId",sub_cat_id);
                        intent2.putExtra("catId",categoryID);
                        intent2.putExtra("suvUrl",subUrlPic );
                        startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"Empty",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void findHotel(View view) {
        Category_ID=1;
        SubCategory(Category_ID);
    }

    public void findClothes(View view) {
        Category_ID=1003;
        SubCategory(Category_ID);
    }

    public void findCafees(View view) {
        Category_ID=2;
        SubCategory(Category_ID);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
