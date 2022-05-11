package com.example.ahmad.hakimi.tab;

/**
 * Created by ahmad on 12/3/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.First_Page;
import com.example.ahmad.hakimi.ListViewActivity;
import com.example.ahmad.hakimi.Maps;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomAdapter;
import com.example.ahmad.hakimi.basicClass.MyCustomerAdapterNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class news_tab extends Fragment{
ListView newsLIST;
String result_news,result_fav;
String [] Hot_news,newsName1,newsText1 ,agentName;
int [] Hot_news_id,agent_id;
int []  fav_id;
private Context context;
ArrayList<String> newsName,newsText,agentNamelist;
public news_tab()
{

}
public news_tab (Context context)
{

    this.context=context;
    newsName=new ArrayList<String>();
    newsText=new ArrayList<String>();
    agentNamelist=new ArrayList<String>();
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news, container, false);
        rootView.setBackground(Drawable.createFromPath("@drawable/back"));
        newsLIST=(ListView)rootView.findViewById(R.id.newsLV);
        getFavouriteNews();
        news();


        return rootView;
    }

    public void news() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender((Activity) context);
                        result_news = http.SEND_POST_get_news("http://188.165.159.59/testapp/dal/Login.asmx/getdata_news");
                    }
                    else {
                        result_news = "";
                    }
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
                        JSONArray JA = new JSONArray(result_news);
                        Hot_news= new String[JA.length()];
                        Hot_news_id= new int [JA.length()];
                        agent_id= new int [JA.length()];
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

                           // Hot_news[i] = (String) JO.get("text_news");
                           Hot_news_id[i]= (int)   JO.get("news_id");
                            agent_id[i]= (int)   JO.get("agent_id");
                           newsName1[i]=(String) JO.get("news_name");
                           newsText1[i]=(String) JO.get("text_news");
                           agentName[i]=(String) JO.get("agent_name");
                           url[i]="http://188.165.159.59/testapp/"+(String) JO.get("url_picture");

                           newsName.add(newsName1[i]);
                           newsText.add(newsText1[i]);
                           agentNamelist.add(agentName[i]);
                           urlList.add(url[i]);
                        }
                        MyCustomerAdapterNews adapter = new MyCustomerAdapterNews(newsName,newsText,agentNamelist,urlList,agent_id,Hot_news_id,fav_id,true,context);
                       newsLIST.setAdapter(adapter) ;



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(getActivity(),"No News",Toast.LENGTH_SHORT).show();

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
                    if(isNetworkAvailable()) {
                        HttpRequestSender http = new HttpRequestSender((Activity) context);
                        result_fav = http.SEND_POST_get_news_fav("http://188.165.159.59/testapp/dal/Login.asmx/getdata_newsfav");
                    }
                    else
                    {
                        result_fav="";
                        Toast.makeText(context,"Check the internet Connection",Toast.LENGTH_SHORT).show();
                    }
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
                   // Toast.makeText(context, "Welcome Favourite", Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(context,"No News",Toast.LENGTH_SHORT).show();

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
}
