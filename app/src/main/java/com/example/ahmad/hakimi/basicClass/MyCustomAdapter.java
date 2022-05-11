package com.example.ahmad.hakimi.basicClass;

/**
 * Created by ahmad on 12/7/2017.
 */

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.Agent_profile;
import com.example.ahmad.hakimi.ListViewActivity;
import com.example.ahmad.hakimi.Map;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/7/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> list1 = new ArrayList<String>();//pic
    private double[] AgentLat ;
    private double [] AgentLon ;
    private int [] Agent_Id;
    private Context context;
    String result_Follow,result_Agent_branch,params_Agent_branch ,result_Agent_offer,params_Agent_offer,result_fav_offer;
    ListView offerList;
    private int [] Agent_folow_id ,fav_offer_id;




    public MyCustomAdapter(ArrayList<String> list,ArrayList<String> list1,int [] Agent_Id,double[] lat,double[] lon ,int [] Agent_folow_id,Context context) {
        this.list = list;
        this.list1 = list1;
        this.AgentLat=lat;
        this.AgentLon=lon;
        this.context = context;
        this.Agent_Id=Agent_Id;
       this.Agent_folow_id=Agent_folow_id;

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
            view = inflater.inflate(R.layout.follow_list_form, null);


        }

        getFavouriteOffer();
        Button listItemText = (Button) view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        ImageView agentImg = (ImageView)view.findViewById(R.id.productimgID);
        Picasso.with(context).load(list1.get(position)).into(agentImg);

        agentImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,pic_show.class);
                intent.putExtra("pic", list1.get(position));
                context.startActivity(intent);

            }
        });

         final Button folowBu = (Button)view.findViewById(R.id.folowBu);
        final Button branch = (Button)view.findViewById(R.id.branchesID2);
        final Button offer = (Button)view.findViewById(R.id.offerBu);
        final Button Location = (Button)view.findViewById(R.id.LocationID);
        offerList = (ListView)view.findViewById(R.id.LV1);
        Boolean x= true;
        for (int i=0;i<Agent_folow_id.length;i++)
        {
            if(Agent_Id[position]==Agent_folow_id[i])
            {
                folowBu.setText("Followed");
                x=false;
               // folowBu.setEnabled(false);
              //  folowBu.setVisibility(View.INVISIBLE);
            }

        }
       if(x==true)
       {
           folowBu.setText("Follow");
       }

        folowBu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if(folowBu.getText().toString().equals("Follow")) {
                   if(isNetworkAvailable()) {
                       Follow(Agent_Id[position], folowBu);
                       folowBu.setVisibility(View.INVISIBLE);
                   }
               }

                notifyDataSetChanged();
            }
        });
        branch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if(isNetworkAvailable()) {
                   getAgent_branches(position);
               }
                notifyDataSetChanged();
            }
        });

        offer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                get_Agent_Offer(position);

                notifyDataSetChanged();
            }
        });
        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(context,Agent_profile.class);
                intent.putExtra("agent_id",Agent_Id[position]);
                context.startActivity(intent);

                notifyDataSetChanged();
            }
        });
        Location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Map.class);
                intent.putExtra("agent_lat", AgentLat[position]);
                intent.putExtra("agent_lon", AgentLon[position]);
                intent.putExtra("agent_name_single", list.get(position));
                context.startActivity(intent);




                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void Follow(final int agentId, final Button folowBu) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_Follow = http.SEND_POST_Follow("http://188.165.159.59/testapp/dal/Login.asmx/add_flow",agentId);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Follow);


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

                if(result_Follow.contains("succ")) {
                    Toast.makeText(context,"Followed",Toast.LENGTH_SHORT).show();
                        }

                else
                {
                    Toast.makeText(context,"Not Followed",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }


    public void getAgent_branches(final int pos) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_Agent_branch = http.SEND_POST_Agent_branch("http://188.165.159.59/testapp/dal/Login.asmx/getdata_branches_agent",Agent_Id[pos]);
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

                    jsonObject.put("agent_id",Agent_Id[pos]);

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
                  //  Toast.makeText(context, "Welcome To Agent Branch", Toast.LENGTH_SHORT).show();
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
                         //   Toast.makeText(context,branch_name[i]+"  "+phone[i]+"  "+branch_lat[i]+ "  "+branch_lon[i],Toast.LENGTH_SHORT).show();
                        }
                        Intent intent2 =new Intent(context,ListViewActivity.class);
                        intent2.putExtra("branchName",branch_name);
                        intent2.putExtra("branchPhone",phone);
                        intent2.putExtra("branchLat",branch_lat);
                        intent2.putExtra("branchLon",branch_lon);
                        context.startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                  //  Toast.makeText(context,"No Branches found",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,Map.class);
                    intent.putExtra("agent_lat", AgentLat[pos]);
                    intent.putExtra("agent_lon", AgentLon[pos]);
                    intent.putExtra("agent_name_single", list.get(pos));
                    context.startActivity(intent);


                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void get_Agent_Offer(final int pos) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_Agent_offer = http.SEND_POST_Agent_offers("http://188.165.159.59/testapp/dal/Login.asmx/getdata_offers_agent",Agent_Id[pos]);
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

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("agent_id",Agent_Id[pos]);

                    params_Agent_offer = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Agent_offer.contains("offer_name")) {

                    //   x.cancel();
                  //  Toast.makeText(context, "Welcome To Agent Offer", Toast.LENGTH_SHORT).show();
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
                        Intent intent2=new Intent(context,ListViewActivity.class);
                        intent2.putExtra("offerNameList",offerName);//good
                        intent2.putExtra("offerDesc",offerDescription);//good
                        intent2.putExtra("offerUrl",pic);//good
                        intent2.putExtra("offer_id",offer_id);//good
                        intent2.putExtra("Fav_offer_id",fav_offer_id);//good

                        context.startActivity(intent2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // Toast.makeText(context,"Try Again",Toast.LENGTH_SHORT).show();

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
                   // Toast.makeText(context, "Welcome Favourite", Toast.LENGTH_SHORT).show();
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
                  //  Toast.makeText(context,"No Fav",Toast.LENGTH_SHORT).show();

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