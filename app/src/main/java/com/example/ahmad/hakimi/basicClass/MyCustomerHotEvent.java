package com.example.ahmad.hakimi.basicClass;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.ahmad.hakimi.Agent_profile;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.fullnewsactivity;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/19/2017.
 */

public class MyCustomerHotEvent extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();// name
    private ArrayList<String> list1 = new ArrayList<String>();// text
    private ArrayList<String> list2 = new ArrayList<String>();//agent pic
    private int [] Agent_Id,news_id;
    private Context context;
    String result_news,result_Agent_branch,params_Agent_branch ,result_Agent_offer,params_Agent_offer;
    Button add;




    public MyCustomerHotEvent(ArrayList<String> list,ArrayList<String> list1,ArrayList<String> list2, Context context) {
        this.list = list;
        this.list1=list1;
        this.list2=list2;

        this.context = context;

    }
    public MyCustomerHotEvent(ArrayList<String> list,ArrayList<String> list2, Context context) {
        this.list = list;

        this.list2=list2;

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
            view = inflater.inflate(R.layout.list_view_hot_offer, null);
            //view.setBackgroundResource(R.drawable.listshape);
        }
        TextView Name = (TextView)view.findViewById(R.id.name);
        Name.setText(list.get(position));
        TextView Text = (TextView)view.findViewById(R.id.text);
           Text.setText(list1.get(position));

        ImageView imageView = (ImageView) view.findViewById(R.id.hotImage);
        Picasso.with(context).load(list2.get(position)).into(imageView);

        final Button read = (Button)view.findViewById(R.id.ReadMoreID);
        read.setVisibility(View.INVISIBLE);






        return view;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
