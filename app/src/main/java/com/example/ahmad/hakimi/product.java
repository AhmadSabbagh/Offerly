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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerHotEvent;
import com.example.ahmad.hakimi.basicClass.MyCustomerProduct;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class product extends AppCompatActivity {
ListView producList;
String result_product;
String [] product_name,product_info,url;
int [] product_id;
double [] price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        producList = (ListView)findViewById(R.id.productList);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNetworkAvailable()) {
                    switch (item.getItemId()) {

                        case R.id.id1://firstPage
                            startActivity(new Intent(product.this, First_Page.class));
                            finish();

                            break;
                        case R.id.navigation_home: //homePage
                            startActivity(new Intent(product.this, HomePage.class));
                            finish();

                            break;
                        case R.id.navigation_notifications://NearBy
                            startActivity(new Intent(product.this, hot_event_activity.class));
                            finish();
                            break;
                        case R.id.navigation_dashboard: //account


                            break;
                        case R.id.id2:
                            startActivity(new Intent(product.this, NewFavoriteAvtivity.class));
                            finish();

                            break;
                    }
                }
                else
                {
                    Toast.makeText(product.this,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show();
                }
                return false;

            }
        });

        producList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                //   Toast.makeText(hot_event_activity.this,value,Toast.LENGTH_SHORT).show();
                //   Toast.makeText(hot_event_activity.this,""+position,Toast.LENGTH_SHORT).show();
                if(product_name!=null) {
                    //     Toast.makeText(hot_event_activity.this,""+position,Toast.LENGTH_SHORT).show();
                    if(isNetworkAvailable()) {
                        Intent intent = new Intent(product.this,fullnewsactivity.class);
                        intent.putExtra("news_name", product_name[position]);
                        intent.putExtra("news_text", product_info[position]);
                         intent.putExtra("price", price[position]);
                        intent.putExtra("news_pic", url[position]);
                        intent.putExtra("state", "true");
                        intent.putExtra("product_id", product_id[position]);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(product.this,"Check internet connection",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        getProduct();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void getProduct() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(product.this);
                    result_product = http.SEND_POST_Hot_offer("http://188.165.159.59/testapp/dal/login.asmx/getdata_product");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_product);


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

                if(result_product.contains("name")) {

                    //   x.cancel();
                    //   Toast.makeText(getActivity(), "Welcome To Hot event", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_product);
                        product_name= new String[JA.length()];
                        product_info=new String [JA.length()];
                        price=new double [JA.length()];
                        product_id=new int [JA.length()];
                         url = new String[JA.length()];
                        ArrayList<String> name = new ArrayList<String>();
                        ArrayList<String>text = new ArrayList<String>();
                        ArrayList<String>urlPic = new ArrayList<String>();
                        ArrayList<Double>priceArray = new ArrayList<Double>();
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            product_name[i] = (String) JO.get("name");
                            price[i] = (Double) JO.get("price");
                            product_id[i] = (int) JO.get("product_id");
                            product_info[i] = (String) JO.get("text_information");
                            url[i]="http://188.165.159.59/testapp/"+(String)JO.get("url_picture");
                            name.add( product_name[i]);
                            text.add(product_info[i]);
                            priceArray.add(price[i]);
                            urlPic.add(url[i]);
                        }

                        MyCustomerProduct list = new MyCustomerProduct(name,priceArray,urlPic,product.this);
                        producList.setAdapter(list) ;



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
}
