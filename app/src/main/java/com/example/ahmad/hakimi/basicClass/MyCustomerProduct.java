//package com.example.ahmad.hakimi.basicClass;
//
//public class MyCustomerProduct {

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

import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.agent_list;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/12/2017.
 */

public class MyCustomerProduct extends BaseAdapter implements ListAdapter {
    private ArrayList<String> name = new ArrayList<String>();//news name
    private ArrayList<String> pic = new ArrayList<String>();//pic
    private ArrayList<Double> price = new ArrayList<Double>();
    String result_Agent,params_Agent;

    private Context context;

    ImageView productImage;
    TextView nameText ,priceText;




    public MyCustomerProduct(ArrayList<String> name,ArrayList <Double> price,ArrayList<String> pic, Context context) {
        this.name = name;
        this.pic = pic;
        this.context = context;
        this.price=price;

    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int pos) {
        return name.get(pos);
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
            view = inflater.inflate(R.layout.product_list_view, null);
            //view.setBackgroundResource(R.drawable.listshape);
        }


        productImage = (ImageView)view.findViewById(R.id.productimgID);
        nameText = (TextView) view.findViewById(R.id.NameID);
        priceText = (TextView)view.findViewById(R.id.priceID);
        Picasso.with(context).load(pic.get(position)).into(productImage);
        nameText.setText(name.get(position));
        priceText.setText(String.valueOf(price.get(position))+"JD");

        productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,pic_show.class);
                intent.putExtra("pic", pic.get(position));
                context.startActivity(intent);
            }
        });






        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

