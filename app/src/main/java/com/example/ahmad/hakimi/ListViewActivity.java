package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomAdapter;
import com.example.ahmad.hakimi.basicClass.MyCustomerAdapterSub;
import com.example.ahmad.hakimi.basicClass.MyCustomerFollowed;
import com.example.ahmad.hakimi.basicClass.MyCustomerOffer;
import com.example.ahmad.hakimi.basicClass.MyCustomerOfferPicture;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {


  String [] names ,offer_name ;
    String [] SubCatName;
    ArrayList<String> AgentName,SubCatNameList ,offerName, offerDesc,offerPic , urlPic,AgentFollowedNAme ,subUrlPic,AgentPic;
    String [] BranchName ,subPic;
    String [] BranchPhone;
  int [] SubCatId ,offer_id ,fav_offer_id;
    int [] Agent_id,offerID,AgentFollwedID,Agent_id_follow;
    double[] BranchLat;
    double[] BranchLon, discount ;
  int CatId;
  Location myLocation;
  String result_Agent , params_Agent ,result_Agent_branch ,params_Agent_branch;
    ArrayList<String> list;
    double [] AgentLat,AgentLon;
    ArrayList<Double> AgentLatList,AgentLonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        AgentLatList = new ArrayList<Double>();
        AgentLonList=new ArrayList<Double>();
         list = new ArrayList<String>();
        SubCatNameList=new ArrayList<String>();
        subUrlPic=new ArrayList<String>();
        AgentPic=new ArrayList<String>();
        Intent intent2 = getIntent();
        names = intent2.getStringArrayExtra("hot_offer"); //to display hot offer on listview
////////////////////////////////////////////////////////////////////////////////
        AgentFollowedNAme =  intent2.getStringArrayListExtra("AgentFollowedName");
        AgentFollwedID = intent2.getIntArrayExtra("agentFollowedID");
 ////////   / / / / ////////////////////////////////////////////////////////////////////////////////////////////////////
        SubCatNameList = intent2.getStringArrayListExtra("subCatName"); //display sub category of resturant for example American rest

        SubCatId = intent2.getIntArrayExtra("subCatId"); // never be displayed

        CatId = intent2.getIntExtra("catId",0); // to determine categoryId (resturat - hot_offer...)
        subUrlPic = intent2.getStringArrayListExtra("suvUrl");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        urlPic =intent2.getStringArrayListExtra("urlPic");
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        offerName=intent2.getStringArrayListExtra("offerNameList");
        offerDesc=intent2.getStringArrayListExtra("offerDesc");
        offerPic=intent2.getStringArrayListExtra("offerUrl");
        offerID=intent2.getIntArrayExtra("offer_id");
        fav_offer_id=intent2.getIntArrayExtra("Fav_offer_id");

////////////////////////////////////////////////////////////////////////////////////////////
    /*    AgentName = intent2.getStringArrayListExtra("agentName");//display the agent like mac-kfv...ect
        AgentPic= intent2.getStringArrayListExtra("agentPic");
        Agent_id = intent2.getIntArrayExtra("agentId"); // never be displayed
        AgentLat=intent2.getDoubleArrayExtra("AGENTLAT");
        AgentLon=intent2.getDoubleArrayExtra("AGENTLON");
        Agent_id_follow=intent2.getIntArrayExtra("agentFollowId");*/

////////////////////////////////////////////////////////////////////////////////////////////////
         BranchName = intent2.getStringArrayExtra("branchName"); //display Branches name

        BranchPhone = intent2.getStringArrayExtra("branchPhone");//for make a call

        BranchLat = intent2.getDoubleArrayExtra("branchLat");

        BranchLon = intent2.getDoubleArrayExtra("branchLon");
        ////////////////////////////////////////////////////////////////////////////////////////
        offer_id = intent2.getIntArrayExtra("offer_id");//for make a call

        offer_name = intent2.getStringArrayExtra("offer_name");

        discount = intent2.getDoubleArrayExtra("discount");

////////////////////////////////////////////////////////////////////////////////////////////////
        ListView listView = (ListView) findViewById(R.id.LV1);

        if(names!=null) {
            ListAdapter list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
            listView.setAdapter(list);
        }
        else if (SubCatId!=null) //hereSubCatId
        {
           /* SubCatNameList.clear();
          subUrlPic.clear();
          AgentName=null;*/

          /*  for ( int i=0 ; i< SubCatNameList.size();i++)
            {
                SubCatNameList.remove(i);
            }*/
       /*     for ( int i=0 ; i< SubCatName.length;i++)
            {
                SubCatNameList.add(SubCatName[i]);
            }
           /* for ( int i=0 ; i< subPic.length;i++)
            {
                subUrlPic.remove(subPic[i]);
            }
            for ( int i=0 ; i< subPic.length;i++)
            {
                subUrlPic.add(subPic[i]);
            }*/
            MyCustomerAdapterSub adapter = new MyCustomerAdapterSub(SubCatNameList ,SubCatId,CatId,subUrlPic,this);

            listView.setAdapter(adapter) ;

        }
      /*  else if (AgentName!=null)
        {
            SubCatName=null;

            MyCustomAdapter adapter = new MyCustomAdapter(AgentName,AgentPic,Agent_id,AgentLat,AgentLon,Agent_id_follow,this);

            listView.setAdapter(adapter) ;

        }*/
        else if (AgentFollowedNAme!=null)
        {
            /*ListAdapter list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AgentName);
             listView.setAdapter(list);*/
            MyCustomerFollowed adapter = new MyCustomerFollowed(AgentFollowedNAme,AgentFollwedID,this);

            listView.setAdapter(adapter) ;

        }
        else if (BranchName!=null)
        {
            ListAdapter list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BranchName);

            listView.setAdapter(list);

        }
        else if (offerName!=null)
        {

          MyCustomerOffer adapter = new MyCustomerOffer(offerName,offerDesc,offerPic,offer_id,fav_offer_id,true,this);
                        listView.setAdapter(adapter) ;

        }
        else if (urlPic!=null)
        {

            MyCustomerOfferPicture adapter = new MyCustomerOfferPicture(urlPic,this);
            listView.setAdapter(adapter) ;

        }
      //  ListView lv = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
              //  Toast.makeText(getBaseContext(),value,Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getBaseContext(),""+position,Toast.LENGTH_SHORT).show();
                if(SubCatName!=null) {
                    //getAgent(position);
                }
               else  if(SubCatName!=null) {

                }
                else  if(BranchName!=null) {
                    Intent intent3 =new Intent(ListViewActivity.this,Maps.class);
                    intent3.putExtra("branchName",BranchName);
                    intent3.putExtra("branchPhone",BranchPhone);
                    intent3.putExtra("branchLat",BranchLat);
                    intent3.putExtra("branchLon",BranchLon);
                    intent3.putExtra("pos",position);
                    startActivity(intent3);
                }


            }
        });

    }


}
