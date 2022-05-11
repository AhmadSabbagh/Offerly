package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class pic_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_show);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        Intent intent=getIntent();
        String pic =intent.getStringExtra("pic");
        ImageView imageView =(ImageView)findViewById(R.id.imageView) ;
        Picasso.with(pic_show.this).load(pic).into(imageView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;
        int h=displayMetrics.heightPixels;
        getWindow().setLayout((int)(w*0.8),(int)(h*0.8));
    }
}
