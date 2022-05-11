package com.example.ahmad.hakimi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerAdapterNews;
import com.example.ahmad.hakimi.basicClass.MyCustomerOffer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Agent_profile extends AppCompatActivity { // do not forget pic methodd
    int agent_id;
    String agent_name ,result_Agent_branch ,params_Agent_branch,result_Agent_offer,result_Agent_info,result_fav_offer;
    ImageView agentImage;
    Button branch,branchLoc;
    ListView offersList;
    String [] x ={"a","b"};
    String [] y ={"a","b"};
    String [] offer_name;
    String [] picture;
///////////////////////////////////////////////
ListView newsLIST;
    String result_news,result_fav;
    String [] Hot_news,newsName1,newsText1 ,agentName;
    int [] Hot_news_id,agent_id1;
    ArrayList<String> newsName,newsText,agentNamelist;
    TextView descText;
    int [] fav_id,fav_offer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        Intent intent = getIntent();
        agent_id=intent.getIntExtra("agent_id",0);
        agent_name=intent.getStringExtra("agent_name");
         agentImage = (ImageView)findViewById(R.id.agentIMg);
         branch = (Button)findViewById(R.id.LocationID);
        branchLoc = (Button)findViewById(R.id.branchesLocID);
        newsLIST = (ListView)findViewById(R.id.newsListID);
        descText=(TextView)findViewById(R.id.DescriptionID);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        //offersList = (ListView)findViewById(R.id.offersList);
        if(isNetworkAvailable()) {
            getFavouriteOffer();
            getAgnetinfo(agent_id);
            getFavouriteNews();
            news();
        }
        agentImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agent_profile.this,pic_show.class);
                intent.putExtra("pic", picture[0]);
                startActivity(intent);

            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void getAgent_branches(final int agent_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
                    result_Agent_branch = http.SEND_POST_Agent_branch("http://188.165.159.59/testapp/dal/Login.asmx/getdata_branches_agent",agent_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Agent_branch);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("agent_id",agent_id);

                    params_Agent_branch = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent_branch.contains("branch_name")) {

                    //   x.cancel();
                  //  Toast.makeText(getBaseContext(), "Welcome To Agent Branch", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_Agent_branch);
                        String [] branch_name= new String[JA.length()];;
                        String []  phone = new String [JA.length()];
                        double [] branch_lat= new double[JA.length()];
                        double [] branch_lon= new double[JA.length()];
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            branch_name[i] = (String) JO.get("branch_name");
                            phone[i] = (String) JO.get("phone");
                            branch_lat[i]= (double) JO.get("latitude");
                            branch_lon[i]= (double) JO.get("longitude");
                          //  Toast.makeText(Agent_profile.this,branch_name[i]+"  "+phone[i]+"  "+branch_lat[i]+ "  "+branch_lon[i],Toast.LENGTH_SHORT).show();
                        }
                        Intent intent2 =new Intent(Agent_profile.this,ListViewActivity.class);
                        intent2.putExtra("branchName",branch_name);
                        intent2.putExtra("branchPhone",phone);
                        intent2.putExtra("branchLat",branch_lat);
                        intent2.putExtra("branchLon",branch_lon);
                        startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(getBaseContext(),"No Bramches",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getBranches(View view) {
        if(isNetworkAvailable()) {
            getAgent_branches(agent_id);
        }
    }


    public void news() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
                    result_news = http.SEND_POST_get_news_agent("http://188.165.159.59/testapp/dal/Login.asmx/getdata_news_agent",agent_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_news);


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

                if(result_news.contains("text_news")) {

                    //   x.cancel();
                    //  Toast.makeText(getActivity(), "Welcome News", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        newsName=new ArrayList<String>();
                        newsText=new ArrayList<String>();
                        agentNamelist=new ArrayList<String>();
                        JSONArray JA = new JSONArray(result_news);
                        Hot_news= new String[JA.length()];
                        Hot_news_id= new int [JA.length()];
                        agent_id1= new int [JA.length()];
                        newsName1= new String[JA.length()];
                        newsText1= new String  [JA.length()];
                        agentName = new String[JA.length()];
                        String [] url=new String [JA.length()];

                        ArrayList<String> urlList =new ArrayList<String>();
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        newsText.clear();
                        for(int i=0;i<newsText.size();i++)
                        {
                            newsText.remove(i);
                        }
                        newsName.clear();
                        for(int i=0;i<newsName.size();i++)
                        {
                            newsName.remove(i);
                        }
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            Hot_news[i] = (String) JO.get("text_news");
                            Hot_news_id[i]= (int)   JO.get("news_id");
                            agent_id1[i]= (int)   JO.get("agent_id");
                            newsName1[i]=(String) JO.get("news_name");
                            newsText1[i]=(String) JO.get("text_news");
                            agentName[i]=(String) JO.get("agent_name");
                            url[i]="http://188.165.159.59/testapp/"+(String) JO.get("url_picture");

                            newsName.add(newsName1[i]);
                            newsText.add(newsText1[i]);
                            agentNamelist.add(agentName[i]);
                            urlList.add(url[i]);


                            //  Hot_offer_image[i] = (int) JO.get("image");Hot_offer_name
                            //  Toast.makeText(getActivity(),Hot_news[i],Toast.LENGTH_SHORT).show();
                        }
                        MyCustomerAdapterNews adapter = new MyCustomerAdapterNews(newsName,newsText,agentNamelist,urlList,agent_id1,Hot_news_id,fav_id,true,Agent_profile.this);
                        newsLIST.setAdapter(adapter) ;



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(Agent_profile.this,"No News",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getFavouriteNews() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
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
               //     Toast.makeText(Agent_profile.this, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav);
                        ArrayList <String> newsName , newsText,agentNamelist,urlList;

                        fav_id= new int [JA.length()];


                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);

                            fav_id[i]= (int)   JO.get("news_id");


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    //Toast.makeText(Agent_profile.this,"No Fav News",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getLocation(View view) {
        if(isNetworkAvailable()) {
            getAgent_loc(agent_id);
        }
    }

    public void getAgent_loc(final int agent_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
                    result_Agent_branch = http.SEND_POST_Agent_branch("http://188.165.159.59/testapp/dal/Login.asmx/getdata_branches_agent",agent_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Agent_branch);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("agent_id",agent_id);

                    params_Agent_branch = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent_branch.contains("branch_name")) {

                    //   x.cancel();
                   // Toast.makeText(getBaseContext(), "Welcome To Agent Branch", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_Agent_branch);
                        String [] branch_name= new String[JA.length()];;
                        String []  phone = new String [JA.length()];
                        double [] branch_lat= new double[JA.length()];
                        double [] branch_lon= new double[JA.length()];
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            branch_name[i] = (String) JO.get("branch_name");
                            phone[i] = (String) JO.get("phone");
                            branch_lat[i]= (double) JO.get("latitude");
                            branch_lon[i]= (double) JO.get("longitude");
                         //   Toast.makeText(Agent_profile.this,branch_name[i]+"  "+phone[i]+"  "+branch_lat[i]+ "  "+branch_lon[i],Toast.LENGTH_SHORT).show();
                        }
                        Intent intent2 =new Intent(Agent_profile.this,Maps.class);
                        intent2.putExtra("branchName",branch_name);
                        intent2.putExtra("branchPhone",phone);
                        intent2.putExtra("branchLat",branch_lat);
                        intent2.putExtra("branchLon",branch_lon);
                        startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(getBaseContext(),"No Agent",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void getOffers(View view) {
        if(isNetworkAvailable()) {
            get_Agent_Offer(agent_id);
        }

    }


    public void get_Agent_Offer(final int Agent_Id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
                    result_Agent_offer = http.SEND_POST_Agent_offers("http://188.165.159.59/testapp/dal/Login.asmx/getdata_offers_agent",Agent_Id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Agent_offer);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent_offer.contains("offer_name")) {

                    //   x.cancel();
                   // Toast.makeText(Agent_profile.this, "Welcome To Agent Offer", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        ArrayList<String> offerName,offerDescription,offerTime,startDate,EndDate,pic;
                        JSONArray JA = new JSONArray(result_Agent_offer);
                        String [] offer_name= new String[JA.length()];;//offername
                        int [] offer_id= new int [JA.length()]; //good
                        String [] offerDesc= new String[JA.length()];
                        String [] offerUrl= new String[JA.length()];

                        offerName=new ArrayList<String>();
                        offerDescription=new ArrayList<String>();
                        pic =new ArrayList<String>();

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);
                            offer_name[i] = (String) JO.get("offer_name");
                            offer_id[i]= (int) JO.get("offer_id");
                            offerDesc[i] = (String) JO.get("descr");
                            offerUrl[i] ="http://188.165.159.59/testapp/"+(String) JO.get("url");

                            offerName.add(offer_name[i]);
                            offerDescription.add(offerDesc[i]);
                            pic.add(offerUrl[i]);


                        }
                        /*MyCustomerOffer adapter = new MyCustomerOffer(offerName,offerTime,offerDiscout,offer_id,context);
                        offerList.setAdapter(adapter) ;*/
                        Intent intent2=new Intent(Agent_profile.this,ListViewActivity.class);
                        intent2.putExtra("offerNameList",offerName);//good
                        intent2.putExtra("offerDesc",offerDescription);//good
                        intent2.putExtra("offerUrl",pic);//good
                        intent2.putExtra("offer_id",offer_id);//good
                        intent2.putExtra("Fav_offer_id",fav_offer_id);//good

                        startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(Agent_profile.this,"No  offer found",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getAgnetinfo(final int agent_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
                    result_Agent_info = http.SEND_POST_Agent_offers("http://188.165.159.59/testapp/dal/Login.asmx/getdata_agentpic",agent_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Agent_info);


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

                if(result_Agent_info.contains("descr")) {


                    try {
                        JSONArray JA = new JSONArray(result_Agent_info);
                        String[] desc= new String[JA.length()];;
                         picture= new String[JA.length()];;

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);
                            desc[i] = (String) JO.get("descr");
                            picture[i] ="http://188.165.159.59/testapp/"+(String) JO.get("url");


                        }
                        Picasso.with(Agent_profile.this).load( picture[0]).into(agentImage);
                        descText.setText(desc[0]);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"Error while uploading",Toast.LENGTH_SHORT).show();

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
                    HttpRequestSender http = new HttpRequestSender(Agent_profile.this);
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
                  //  Toast.makeText(Agent_profile.this, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav_offer);


                        fav_offer_id= new int [JA.length()];


                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);


                            fav_offer_id[i]= (int)   JO.get("offer_id");


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(Agent_profile.this,"No Fav",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

}
