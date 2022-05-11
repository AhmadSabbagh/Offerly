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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.ListViewActivity;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.fullnewsactivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/18/2017.
 */

public class MyCustomerFollowed extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();//offer pic
    int [] AgentId;
    Context context;
    String result_Un;






    public MyCustomerFollowed(ArrayList<String> list,int [] id ,Context context) {
        this.list = list;
          this.AgentId=id;
        this.context = context;


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
            view = inflater.inflate(R.layout.followed_list, null);
        }


        TextView pic = (TextView) view.findViewById(R.id.textFollowId);
       pic.setText(list.get(position));
        final Button unfollow =(Button)view.findViewById(R.id.unFollowId);

        unfollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    unfollowAgent(AgentId[position]);
                    unfollow.setVisibility(View.INVISIBLE);
                }
                notifyDataSetChanged();
            }
        });



        return view;
    }
    public void unfollowAgent(final int id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_Un = http.SEND_POST_Agent_unfollow("http://188.165.159.59/testapp/dal/Login.asmx/remove_flow",id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_Un);


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
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_Un.contains("succ")) {

                    //   x.cancel();
                   // Toast.makeText(context, "UnFollowed", Toast.LENGTH_SHORT).show();

                    // JSONObject jb = null;


                }
                else
                {
                   // Toast.makeText(context,"Error try again",Toast.LENGTH_SHORT).show();

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
