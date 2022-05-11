package com.example.ahmad.hakimi.tab;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.FavoriteActivity;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerAdapterNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/24/2017.
 */

public class Fav_news extends Fragment {
    private Context context;
    ListView fav_list;
    String result_fav;

    public Fav_news()
    {

    }
    public Fav_news(Context context)
    {

        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fav_news, container, false);

        fav_list=(ListView)rootView.findViewById(R.id.fav_news_list);
        getFavouriteNews();

        return rootView;
    }
    public void getFavouriteNews() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
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
                 //   Toast.makeText(context, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav);
                        ArrayList <String> newsName , newsText,agentNamelist,urlList;
                        newsText = new ArrayList<String>();
                        newsName = new ArrayList<String>();
                        agentNamelist = new ArrayList<String>();
                        urlList = new ArrayList<String>();
                        String [] news= new String[JA.length()];
                       int []  fav_id= new int [JA.length()];
                        int [] news_id= new int [JA.length()];
                        int [] agent_id= new int [JA.length()];
                        String[] newsName1= new String[JA.length()];
                        String[] agnetName= new String[JA.length()];
                        String[] url= new String[JA.length()];

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);
                            news[i] = (String) JO.get("text_news");
                            fav_id[i]= (int)   JO.get("fav_id");
                            agent_id[i]= (int)   JO.get("agent_id");
                            news_id[i]= (int)   JO.get("news_id");
                            newsName1[i]=(String)JO.get("news_name");
                            url[i]="http://188.165.159.59/testapp/"+(String) JO.get("url_picture");
                            agnetName[i]=(String)JO.get("agent_name");

                            newsName.add(newsName1[i]);
                            newsText.add(news[i]);
                           agentNamelist.add(agnetName[i]);
                            urlList.add(url[i]);
                          //  Toast.makeText(context,news[i],Toast.LENGTH_SHORT).show();

                        }
                        MyCustomerAdapterNews adapter = new MyCustomerAdapterNews(newsName,newsText,agentNamelist,urlList,agent_id,news_id,fav_id,false,context);
                        fav_list.setAdapter(adapter) ;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                 //   Toast.makeText(context,"No News",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
