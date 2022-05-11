package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.example.ahmad.hakimi.basicClass.MyCustomerProduct;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fullnewsactivity extends AppCompatActivity {
    private String list ;//news name
    private String list1 ;//news text
    private String list2 ;//agent name
    private String list3 ; // pic
    private String state ; // pic
    private int  product_id;
    Double price;
    String result_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
       list= intent.getStringExtra("news_name");
       list1= intent.getStringExtra("news_text");
        list2= intent.getStringExtra("agent_name");
        list3= intent.getStringExtra("news_pic");
        state= intent.getStringExtra("state");
        price = intent.getDoubleExtra("price",0);
        product_id = intent.getIntExtra("product_id",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullnewsactivity);
        TextView AgentName=(TextView) findViewById(R.id.FageNameID);
        TextView priceText=(TextView) findViewById(R.id.priceID);
        TextView NewsName =(TextView) findViewById(R.id.fnewsNameID);
        TextView NewsTexy=(TextView) findViewById(R.id.fnewsTextID);
        ImageView imageView=(ImageView)findViewById(R.id.news_pic);
        ImageView share=(ImageView)findViewById(R.id.ShareNEwsBUID);
        ImageButton orderImgBu =(ImageButton)findViewById(R.id.orderBu);
        AgentName.setText(list2);
        NewsName.setText(list);
        NewsTexy.setText(list1);
        priceText.setVisibility(View.INVISIBLE);
        orderImgBu.setVisibility(View.INVISIBLE);
        if(state.equals("true")) {
            priceText.setText(String.valueOf(price)+"JD");
            priceText.setVisibility(View.VISIBLE);
            orderImgBu.setVisibility(View.VISIBLE);
        }

        Picasso.with(fullnewsactivity.this).load(list3).into(imageView);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        /*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;
        int h=displayMetrics.heightPixels;
        getWindow().setLayout((int)(w*0.8),(int)(h*0.8));
        */
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body=list1;
                String sub = "yourSub";
                intent.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
    }


    public void order(View view) {
        Intent intent =new Intent(this,OrderActivity.class);
        intent.putExtra("product_id",product_id);
        startActivity(intent);
    }

}
