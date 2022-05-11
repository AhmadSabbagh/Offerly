package com.example.ahmad.hakimi.basicClass;

import android.content.Context;
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

import com.example.ahmad.hakimi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/18/2017.
 */

public class MyCustomerOfferPicture extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();//offer pic
    Context context;






    public MyCustomerOfferPicture(ArrayList<String> list, Context context) {
        this.list = list;

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
            view = inflater.inflate(R.layout.offer_pic, null);
        }


        ImageView pic = (ImageView) view.findViewById(R.id.offerImageID);
        Picasso.with(context).load(list.get(position)).into(pic);






        return view;
    }

}
