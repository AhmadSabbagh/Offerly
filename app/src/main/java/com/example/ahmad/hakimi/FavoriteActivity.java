package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteActivity extends AppCompatActivity {
ListView favoritList,offerList;
String result_fav,result_fav_offer;
String [] news,offerName;
int [] news_id,fav_id,agent_id;
double [] offerDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favoritList =(ListView)findViewById(R.id.favLV);
        offerList =(ListView)findViewById(R.id.offerListVid);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        // BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNetworkAvailable()) {
                switch (item.getItemId()) {
                    case R.id.id1://firstPage
                        startActivity(new Intent(FavoriteActivity.this, First_Page.class));
                        break;
                    case R.id.navigation_home: //homePage
                        startActivity(new Intent(FavoriteActivity.this, HomePage.class));
                        break;
                    case R.id.navigation_notifications://NearBy
                        startActivity(new Intent(FavoriteActivity.this, nearby.class));

                        break;
                    case R.id.navigation_dashboard: //account
                        startActivity(new Intent(FavoriteActivity.this, Account.class));
                        break;
                    case R.id.id2://favorite
                        break;
                }
                }
                else
                {
                    Toast.makeText(FavoriteActivity.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

if(isNetworkAvailable()) {
    getFavouriteNews();
    getFavouriteOffer();
}
        favoritList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
              //  Toast.makeText(getBaseContext(),value,Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getBaseContext(),""+position,Toast.LENGTH_SHORT).show();
                if(news!=null) {
                    Intent intent = new Intent(FavoriteActivity.this,Agent_profile.class);
                    intent.putExtra("agent_name", "name ");
                    intent.putExtra("agent_id", agent_id[position]);
                   startActivity(intent);
                }

            }
        });
    }

    public void getFavouriteNews() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(FavoriteActivity.this);
                    result_fav = http.SEND_POST_get_news_fav("http://188.165.159.59/testapp/dal/Login.asmx/getdata_newsfav");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_fav);


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

                if(result_fav.contains("text_news")) {

                    //   x.cancel();
                //    Toast.makeText(FavoriteActivity.this, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav);
                        news= new String[JA.length()];
                        news_id= new int [JA.length()];
                        agent_id= new int [JA.length()];
                        String[] newsName= new String[JA.length()];
                        String[] News= new String[JA.length()];

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);
//[{"agent_id":1,"news_name":"New Branch for Mac",
// "text_news":"New branch for Mac will be in Gardens",
// "news_id":3,"fav_id":124,"url_picture":""}]{"d":null}
                            news[i] = (String) JO.get("text_news");
                            news_id[i]= (int)   JO.get("fav_id");
                            agent_id[i]= (int)   JO.get("agent_id");
                            newsName[i]=(String)JO.get("news_name");



                            //  Hot_offer_image[i] = (int) JO.get("image");
                         //   Toast.makeText(FavoriteActivity.this,news[i],Toast.LENGTH_SHORT).show();
                        }
                        ListAdapter list1 = new ArrayAdapter<String>(FavoriteActivity.this, android.R.layout.simple_list_item_1, news);
                        favoritList.setAdapter(list1) ;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(FavoriteActivity.this,"No News",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getFavouriteOffer() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(FavoriteActivity.this);
                    result_fav_offer = http.SEND_POST_get_news_fav("http://188.165.159.59/testapp/dal/Login.asmx/getdata_offerfav");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_fav_offer);


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

                if(result_fav_offer.contains("offer_name")) {

                    //   x.cancel();
                   // Toast.makeText(FavoriteActivity.this, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav_offer);
                        offerName= new String[JA.length()];
                        fav_id= new int [JA.length()];
                        offerDiscount=new double[JA.length()];
                        String [] offers=new String[JA.length()];;

                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            offerName[i] = (String) JO.get("offer_name");
                           // offerDiscount[i] = (double) JO.get("discount");
                            fav_id[i]= (int)   JO.get("fav_id");


                        }
                        ListAdapter list1 = new ArrayAdapter<String>(FavoriteActivity.this, android.R.layout.simple_list_item_1, offerName);
                        offerList.setAdapter(list1) ;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(FavoriteActivity.this,"No Fav",Toast.LENGTH_SHORT).show();

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
