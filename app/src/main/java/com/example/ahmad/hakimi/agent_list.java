package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ahmad.hakimi.basicClass.MyCustomAdapter;

import java.util.ArrayList;

public class agent_list extends AppCompatActivity {
ListView listView ;


    ArrayList<String> AgentName,AgentPic;
    int [] Agent_id,Agent_id_follow;
    double [] AgentLat,AgentLon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        setContentView(R.layout.activity_agent_list);
        listView = (ListView)findViewById(R.id.new_list);
        Intent intent2=getIntent();
        AgentName = intent2.getStringArrayListExtra("agentName");//display the agent like mac-kfv...ect
        AgentPic= intent2.getStringArrayListExtra("agentPic");
        Agent_id = intent2.getIntArrayExtra("agentId"); // never be displayed
        AgentLat=intent2.getDoubleArrayExtra("AGENTLAT");
        AgentLon=intent2.getDoubleArrayExtra("AGENTLON");
        Agent_id_follow=intent2.getIntArrayExtra("agentFollowId");
          if (AgentName!=null)
        {
            MyCustomAdapter adapter = new MyCustomAdapter(AgentName,AgentPic,Agent_id,AgentLat,AgentLon,Agent_id_follow,this);

            listView.setAdapter(adapter) ;

        }
    }


}
