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
import com.example.ahmad.hakimi.basicClass.MyCustomerOffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/24/2017.
 */

public class Fav_offer  extends Fragment {
private Context context;
ListView fav_list_offer;
String result_fav_offer;

    public Fav_offer()
    {

    }
    public Fav_offer(Context context)
    {

        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fav_offer, container, false);

        fav_list_offer=(ListView)rootView.findViewById(R.id.fav_offer_list);
         getFavouriteOffer();
        return rootView;
    }
    public void getFavouriteOffer() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity)context);
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
                 //   Toast.makeText(context, "Welcome Favourite", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_fav_offer);
                       ArrayList <String> offerName= new ArrayList<String>();
                        ArrayList <String> offerDesc= new ArrayList<String>();
                        ArrayList <String> offerPic= new ArrayList<String>();

                        int [] fav_id= new int [JA.length()];
                        int [] offer_id=new int [JA.length()];;

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            offerName.add((String) JO.get("offer_name"));
                            offerDesc.add((String) JO.get("descr"));
                            offerPic.add("http://188.165.159.59/testapp/"+(String) JO.get("url"));
                            fav_id[i]= (int)   JO.get("fav_id");
                            offer_id[i]=(int) JO.get("offer_id");

                        }
                        MyCustomerOffer adapter = new MyCustomerOffer(offerName,offerDesc,offerPic,offer_id,fav_id,false,context);
                        fav_list_offer.setAdapter(adapter) ;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(context,"No Fav",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
