package com.example.ahmad.hakimi.basicClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.agent_list;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/12/2017.
 */

public class MyCustomerAdapterSub extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();//news name
    private ArrayList<String> list1 = new ArrayList<String>();//pic
String result_Agent,params_Agent;
    private int [] Sub_Id,news_id;
    private Context context;
    private int catID;
    String result_follow;
    ImageView agentImage;
    int [] Agent_folow_id;



    public MyCustomerAdapterSub(ArrayList<String> list,int [] Sub_Id,int catId,ArrayList<String> list1, Context context) {
        this.list = list;
        this.list1 = list1;
        this.context = context;
        this.Sub_Id=Sub_Id;
        this.catID=catId;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sub_cat_list, null);
            //view.setBackgroundResource(R.drawable.listshape);
        }
        getFollow();
        final Button newsName = (Button) view.findViewById(R.id.namesubID);
        newsName.setText(list.get(position));
        agentImage = (ImageView)view.findViewById(R.id.productimgID);
        Picasso.with(context).load(list1.get(position)).into(agentImage);

        agentImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,pic_show.class);
                intent.putExtra("pic", list1.get(position));
                context.startActivity(intent);
            }
        });



        newsName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //deleteBtn.setVisibility(View.INVISIBLE);
                if(isNetworkAvailable()) {
                    getAgent(position);
                }
                notifyDataSetChanged();
            }
        });



        return view;
    }
    public void getAgent(final int pos) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_Agent = http.SEND_POST_Agent("http://188.165.159.59/testapp/dal/Login.asmx/getdata_agent",catID,Sub_Id[pos]);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Agent);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("category_id",catID);
                    jsonObject.put("subcategory_id",Sub_Id[pos]);

                    params_Agent = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent.contains("agent_id")) {

                    //   x.cancel();
                   // Toast.makeText(context, "Welcome To Agent", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        JSONArray JA = new JSONArray(result_Agent);
                        String [] agent_name= new String[JA.length()];;
                        int []  agent_id = new int[JA.length()];
                        int []  fake = new int[JA.length()];
                         ArrayList<String> urlPic = new ArrayList<String>();//pic
                        ArrayList<Double> AgentLat = new ArrayList<Double>();//pic
                        ArrayList<Double> AgentLon = new ArrayList<Double>();//pic
                        ArrayList<String> list = new ArrayList<String>();//pic
                        double [] agentLat  = new double[JA.length()];
                        double [] agentLon  = new double[JA.length()];

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            agent_name[i] = (String) JO.get("name");
                            agent_id[i] = (int) JO.get("agent_id");
                            agentLat[i] = (double) JO.get("latitude");
                            agentLon[i] = (double) JO.get("longitude");
                            list.add((String) JO.get("name"));
                            urlPic.add("http://188.165.159.59/testapp/"+(String) JO.get("url"));
                            fake[i]=0;
                          //  Toast.makeText(context,"Agent Name"+agent_name[i]+"  AgentId :"+agent_id[i],Toast.LENGTH_SHORT).show();
                        }

                        Intent intent2 =new Intent(context,agent_list.class);
                        intent2.putExtra("agentName",list);
                        intent2.putExtra("agentId",agent_id);
                        intent2.putExtra("agentPic",urlPic);
                        intent2.putExtra("AGENTLAT",agentLat);
                        intent2.putExtra("AGENTLON",agentLon);
                        if(Agent_folow_id!=null) {
                            intent2.putExtra("agentFollowId", Agent_folow_id);
                        }
                        else
                        {
                            intent2.putExtra("agentFollowId", fake);
                        }
                        context.startActivity(intent2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(context,"No Agent",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void getFollow() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
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
                        //String [] name= new String[JA.length()];
                        Agent_folow_id= new int [JA.length()];
                        // ArrayList<String>agentName=new ArrayList<String>();
                        //  double [] Hot_offer_image=new double[JA.length()];;
                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);
                            //name[i] = (String) JO.get("name");
                            Agent_folow_id[i]= (int) JO.get("agent_id");
                            // agentName.add(name[i]);
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(context,"Please Try Again",Toast.LENGTH_SHORT).show();

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
